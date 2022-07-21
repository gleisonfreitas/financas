package br.com.jetro.entitycontroler.financas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.jetro.assistentes.LancamentoFiltro;
import br.com.jetro.assistentes.ResumoCategoria;
import br.com.jetro.assistentes.ResumoLancamento;
import br.com.jetro.assistentes.ResumoLancamentoMes;
import br.com.jetro.entitycontroler.EntityControlerGenerics;
import br.com.jetro.enums.TipoLancamento;
import br.com.jetro.modelo.financas.Lancamento;
import br.com.jetro.modelo.financas.MesRef;

public class LancamentoEC extends EntityControlerGenerics<Lancamento> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8895031518759387838L;
	
	public List<String> descricoesQueContem(String descricao){
		
		TypedQuery<String> query = entityManager.createQuery(
				"select distinct descricao from Lancamento "
				+ "where upper(descricao) like upper(:descricao)",
				String.class);
		
		query.setParameter("descricao", descricao);
		
		return query.getResultList();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Lancamento> listarPorFiltro(LancamentoFiltro filtro){
		StringBuilder sb = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		sb.append("SELECT l from Lancamento l ");
		boolean primeiroWhere = true;
		if(filtro.getDescricao() != null && !filtro.getDescricao().trim().isEmpty()){
			if(primeiroWhere){
				sb.append("WHERE upper(l.descricao) like upper(:descricao) ");
				primeiroWhere = false;
			}
			params.put("descricao", "%"+filtro.getDescricao()+"%");
		}
		if(filtro.getCategoria() != null){
			if(primeiroWhere){
				sb.append("WHERE l.subCategoria.categoria.id = :codigoCategoria ");
				primeiroWhere = false;
			}else{
				sb.append("AND l.subCategoria.categoria.id = :codigoCategoria ");
			}
			params.put("codigoCategoria", filtro.getCategoria().getId());
		}
		if(filtro.getSubCategoria() != null){
			if(primeiroWhere){
				sb.append("WHERE l.subCategoria.id = :codigoSubCategoria ");
				primeiroWhere = false;
			}else{
				sb.append("AND l.subCategoria.id = :codigoSubCategoria ");
			}
			params.put("codigoSubCategoria", filtro.getSubCategoria().getId());
		}
		if(filtro.getDataInicio() != null && filtro.getDataFim() != null){
			if(primeiroWhere){
				sb.append("WHERE l.data between :dataInicio AND :dataFim ");
				primeiroWhere = false;
			}else{
				sb.append("AND l.data between :dataInicio AND :dataFim ");
			}
			params.put("dataInicio", filtro.getDataInicio());
			params.put("dataFim", filtro.getDataFim());
		}else if(filtro.getDataInicio() != null){
			if(primeiroWhere){
				sb.append("WHERE l.data >= :dataInicio ");
				primeiroWhere = false;
			}else{
				sb.append("AND l.data >= :dataInicio ");
			}
			params.put("dataInicio", filtro.getDataInicio());
		}else if(filtro.getDataFim() != null){
			if(primeiroWhere){
				sb.append("WHERE l.data <= :dataFim ");
				primeiroWhere = false;
			}else{
				sb.append("AND l.data <= :dataFim ");
			}
			params.put("dataFim", filtro.getDataFim());
		}
		if(filtro.getValorMin() != null && filtro.getValorMax() != null){
			if(primeiroWhere){
				sb.append("WHERE l.valor between :valorMin AND :valorMax ");
				primeiroWhere = false;
			}else{
				sb.append("AND l.valor between :valorMin AND :valorMax ");
			}
			params.put("valorMin", filtro.getValorMin());
			params.put("valorMax", filtro.getValorMax());
		}else if(filtro.getValorMin() != null){
			if(primeiroWhere){
				sb.append("WHERE l.valor >= :valorMin ");
				primeiroWhere = false;
			}else{
				sb.append("AND l.valor >= :valorMin ");
			}
			params.put("valorMin", filtro.getValorMin());
		}else if(filtro.getValorMax() != null){
			if(primeiroWhere){
				sb.append("WHERE l.valor <= :valorMax ");
				primeiroWhere = false;
			}else{
				sb.append("AND l.valor <= :valorMax ");
			}
			params.put("valorMax", filtro.getValorMax());
		}
		Query query = entityManager.createQuery(sb.toString());
		for (Entry<String, Object> entry : params.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		
		return query.getResultList();
	}
	
	public Double retornarSaldoMes(MesRef mes){
		
		Double retorno = 0.0;
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT SUM(CASE (c.tipo) WHEN 'CREDITO' THEN valor ELSE valor * -1 END) ")
			.append("FROM lancamento l ")
			.append("INNER JOIN subcategoria s ON l.subcategoria = s.id ")
			.append("INNER JOIN categoria c ON s.categoria_id = c.id ")
			.append("WHERE mesRef = :idMesRef");
		
		BigDecimal saldo = (BigDecimal) entityManager.createNativeQuery(sb.toString())
				.setParameter("idMesRef", mes.getId()).getSingleResult();
		
		if(saldo != null){
			retorno = saldo.doubleValue();
		}
		
		return retorno;
		
	}

	@SuppressWarnings("unchecked")
	public List<ResumoCategoria> retornarReceitas(
			MesRef mesRef) {
		
		List<Lancamento> lista = entityManager.createNamedQuery("lancamentoPorTipoEMesRef")
			.setParameter("tipo", TipoLancamento.CREDITO)
			.setParameter("codigoMesRef", mesRef.getId()).getResultList();
		
		return preencherResumo(lista);
		
	}
	
	@SuppressWarnings("unchecked")
	public List<ResumoCategoria> retornarDespesas(
			MesRef mesRef) {
		
		List<Lancamento> lista = entityManager.createNamedQuery("lancamentoPorTipoEMesRef")
				.setParameter("tipo", TipoLancamento.DEBITO)
				.setParameter("codigoMesRef", mesRef.getId()).getResultList();
			
			return preencherResumo(lista);
	}

	private List<ResumoCategoria> preencherResumo(List<Lancamento> lista) {
		
		List<ResumoCategoria> retorno = new ArrayList<ResumoCategoria>();
		
		for (Lancamento lancamento : lista) {
			
			ResumoCategoria resumoCategoria = new ResumoCategoria();
			resumoCategoria.setDescricao(lancamento.getSubCategoria().getCategoria().getDescricao());
			
			ResumoLancamento subCategoria = extrairSubCategoria(lancamento);
			
			int indexOf = retorno.indexOf(resumoCategoria);
			
			if(indexOf == -1){
				resumoCategoria.getListaSubCategoria().add(subCategoria);
				retorno.add(resumoCategoria);
			}else{
				resumoCategoria = retorno.get(indexOf);
				
				int indexOfSubCategoria = resumoCategoria.getListaSubCategoria().indexOf(subCategoria);
				
				if(indexOfSubCategoria == -1){
					resumoCategoria.getListaSubCategoria().add(subCategoria);
				}else{
					ResumoLancamento resumoSubCategoria = resumoCategoria.getListaSubCategoria().get(indexOfSubCategoria);
					resumoSubCategoria.setValor(resumoSubCategoria.getValor()+subCategoria.getValor());
				}
			}
			
		}
		return retorno;
	}

	private ResumoLancamento extrairSubCategoria(Lancamento lancamento) {
		ResumoLancamento resumo = new ResumoLancamento();
		resumo.setDescricao(lancamento.getSubCategoria().getDescricao());
		resumo.setValor(lancamento.getValor().doubleValue());
		
		return resumo;
	}

	public Double retornarTotalReceitas(MesRef mesRefAtual) {
		
		Double retorno = 0.0;
		
		Double total = (Double) entityManager.createNamedQuery("totalPorTipoEMesRef")
				.setParameter("tipo", TipoLancamento.CREDITO)
				.setParameter("codigoMesRef", mesRefAtual.getId()).getSingleResult();
		
		if(total != null){
			retorno = total;
		}
		
		return retorno;
	}
	
	public Double retornarTotalDespesas(MesRef mesRefAtual) {
		
		Double retorno = 0.0;
		
		Double total = (Double) entityManager.createNamedQuery("totalPorTipoEMesRef")
				.setParameter("tipo", TipoLancamento.DEBITO)
				.setParameter("codigoMesRef", mesRefAtual.getId()).getSingleResult();
		
		if(total != null){
			retorno = total.doubleValue();
		}
		
		return retorno;
	}

	@SuppressWarnings("unchecked")
	public List<ResumoLancamento> listaReceitasAoDia(MesRef mesRef){
		
		List<Object[]> list = entityManager.createNamedQuery("lancamentoAoDiaPorTipoEMesRef")
				.setParameter("tipo", TipoLancamento.CREDITO)
				.setParameter("codigoMesRef", mesRef.getId()).getResultList();
		
		List<ResumoLancamento> retorno = montarResumoDia(list);
		
		return retorno;
	}
	
	@SuppressWarnings("unchecked")
	public List<ResumoLancamento> listaDespesasAoDia(MesRef mesRef){
		
		List<Object[]> list = entityManager.createNamedQuery("lancamentoAoDiaPorTipoEMesRef")
				.setParameter("tipo", TipoLancamento.DEBITO)
				.setParameter("codigoMesRef", mesRef.getId()).getResultList();
		
		List<ResumoLancamento> retorno = montarResumoDia(list);
		
		return retorno;
	}

	private List<ResumoLancamento> montarResumoDia(List<Object[]> list) {
		List<ResumoLancamento> retorno = new ArrayList<ResumoLancamento>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		for (Object[] object : list) {
			ResumoLancamento resumo = new ResumoLancamento();
			resumo.setDescricao(sdf.format(object[0]));
			resumo.setValor((Double) object[1]);
			
			retorno.add(resumo);
		}
		return retorno;
	}

	@SuppressWarnings("unchecked")
	public List<ResumoLancamento> retornarValorPorDespesas(MesRef mes) {
		
		List<Object[]> lista = entityManager.createNamedQuery("totalLancamentoPorSubCategoria")
			.setParameter("tipo", TipoLancamento.DEBITO)
			.setParameter("codigoMesRef", mes.getId()).getResultList();
		
		List<ResumoLancamento> retorno = new ArrayList<ResumoLancamento>();
		
		for (Object[] object : lista) {
			ResumoLancamento resumo = new ResumoLancamento();
			resumo.setDescricao((String) object[0]);
			resumo.setValor((Double) object[1]);
			
			retorno.add(resumo);
		}
		Collections.sort(retorno);
		
		return retorno;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<ResumoLancamentoMes> retornarResumoMesUltimosDozesMeses(MesRef mes){
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("SELECT m.dataref, ")
			.append("SUM(CASE (c.tipo) WHEN 'CREDITO' THEN l.valor ELSE 0 END) AS CREDITO, ")
			.append("SUM(CASE (c.tipo) WHEN 'DEBITO' THEN l.valor ELSE 0 END) AS DEBITO, ")
			.append("SUM(CASE (c.tipo) WHEN 'CREDITO' THEN l.valor ELSE 0 END) - ")
			.append("SUM(CASE (c.tipo) WHEN 'DEBITO' THEN l.valor ELSE 0 END) AS SALDO ")
			.append("FROM lancamento l ")
			.append("INNER JOIN mesref m ON l.mesref = m.id ")
			.append("INNER JOIN subcategoria s ON l.subcategoria = s.id ")
			.append("INNER JOIN categoria c ON s.categoria_id = c.id ")
			.append("WHERE  data BETWEEN :dataInicio AND :dataFim ")
			.append("GROUP BY m.dataref");
		
		
		Calendar dataFim = Calendar.getInstance();
		dataFim.setTime(mes.getData());
		dataFim.set(Calendar.DAY_OF_MONTH, dataFim.getActualMaximum(Calendar.DAY_OF_MONTH));
		
		Calendar dataInicio = Calendar.getInstance();
		dataInicio.setTime(mes.getData());
		dataInicio.set(Calendar.DAY_OF_MONTH, 1);		
		dataInicio.set(Calendar.MONTH, Calendar.JANUARY);
		
		List<Object[]> resultList = entityManager.createNativeQuery(builder.toString())
			.setParameter("dataInicio", dataInicio.getTime())
			.setParameter("dataFim", dataFim.getTime()).getResultList();
		
		List<ResumoLancamentoMes> retorno = new ArrayList<ResumoLancamentoMes>();
		
		for (Object[] result : resultList) {
			ResumoLancamentoMes resumo = new ResumoLancamentoMes();
			resumo.setData((Date) result[0]);
			resumo.setCredito(((BigDecimal) result[1]).doubleValue());
			resumo.setDebito(((BigDecimal) result[2]).doubleValue());
			resumo.setSaldo(((BigDecimal) result[3]).doubleValue());
			
			retorno.add(resumo);
		}
		
		return retorno;
	}
	
	@SuppressWarnings("unchecked")
	public List<Lancamento> listarReceitasPorMesRef(MesRef mesRef){
		
		
		List<Lancamento> lista = entityManager.createNamedQuery("lancamentoPorTipoEMesRef")
				.setParameter("tipo", TipoLancamento.CREDITO)
				.setParameter("codigoMesRef", mesRef.getId()).getResultList();
		

		return lista;
	}

	
	@SuppressWarnings("unchecked")
	public List<Lancamento> listarDespesasPorMesRef(MesRef mesRef){
		
		List<Lancamento> lista = entityManager.createNamedQuery("lancamentoPorTipoEMesRef")
				.setParameter("tipo", TipoLancamento.DEBITO)
				.setParameter("codigoMesRef", mesRef.getId()).getResultList();
		
		
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	public List<Lancamento> listarPorMesRef(MesRef mesRef){
		
		List<Lancamento> lista = entityManager.createNamedQuery("lancamentoPorMesRef")
				.setParameter("codigoMesRef", mesRef.getId()).getResultList();
		
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	public List<Lancamento> listarPorAno(Integer ano, Integer periodo){
		
		Calendar calendar = Calendar.getInstance();
		
		if(periodo != 1){
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.set(Calendar.MONTH, Calendar.JANUARY);
			calendar.set(Calendar.YEAR, ano);
		}else{
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.set(Calendar.MONTH, Calendar.JULY);
			calendar.set(Calendar.YEAR, ano);
		}
		
		Date inicio = calendar.getTime();
		
		if(periodo == 0){
			calendar.set(Calendar.DAY_OF_MONTH, 30);
			calendar.set(Calendar.MONTH, Calendar.JUNE);
		}else {
			calendar.set(Calendar.DAY_OF_MONTH, 31);
			calendar.set(Calendar.MONTH, Calendar.DECEMBER);
		}
		
		Date fim = calendar.getTime();
		
		return entityManager.createNamedQuery("lancamentoPorAno")
			.setParameter("inicio", inicio)
			.setParameter("fim", fim)
			.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Lancamento> listarPorAnoSubCategoria(Integer ano, Integer periodo, Long codigoSubCategoria){
		
		Calendar calendar = Calendar.getInstance();
		
		if(periodo != 1){
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.set(Calendar.MONTH, Calendar.JANUARY);
			calendar.set(Calendar.YEAR, ano);
		}else{
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.set(Calendar.MONTH, Calendar.JULY);
			calendar.set(Calendar.YEAR, ano);
		}
		
		Date inicio = calendar.getTime();
		
		if(periodo == 0){
			calendar.set(Calendar.DAY_OF_MONTH, 30);
			calendar.set(Calendar.MONTH, Calendar.JUNE);
		}else {
			calendar.set(Calendar.DAY_OF_MONTH, 31);
			calendar.set(Calendar.MONTH, Calendar.DECEMBER);
		}
		
		Date fim = calendar.getTime();
		
		return entityManager.createNamedQuery("lancamentoPorAnoSubCategoria")
			.setParameter("inicio", inicio)
			.setParameter("fim", fim)
			.setParameter("subCategoria", codigoSubCategoria)
			.getResultList();
	}
}
