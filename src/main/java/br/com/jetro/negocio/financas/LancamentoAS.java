package br.com.jetro.negocio.financas;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.com.jetro.assistentes.LancamentoFiltro;
import br.com.jetro.assistentes.ResumoCategoria;
import br.com.jetro.assistentes.ResumoLancamento;
import br.com.jetro.assistentes.ResumoLancamentoMes;
import br.com.jetro.constantes.ConstantesMensagem;
import br.com.jetro.entitycontroler.financas.LancamentoEC;
import br.com.jetro.entitycontroler.financas.MesRefEC;
import br.com.jetro.modelo.financas.Lancamento;
import br.com.jetro.modelo.financas.MesRef;
import br.com.jetro.negocio.NegocioException;
import br.com.jetro.util.Transactional;

public class LancamentoAS implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3298600252376058859L;
	
	@Inject
	private LancamentoEC lancamentoEC;
	
	@Inject
	private MesRefEC mesRefEC;

	@Transactional
	public void salvar(Lancamento lancamento) throws NegocioException{
		
		if(lancamento.getValor() == null || 
				lancamento.getValor().doubleValue() < 0){
			
			throw new NegocioException(ConstantesMensagem.MN003);
		}
		if(lancamento.getData().after(new Date())){
			throw new NegocioException(ConstantesMensagem.MN018);
		}
		MesRef mesAtual = mesRefEC.buscarMesAtual();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(mesAtual.getData());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		
		if(lancamento.getData().before(calendar.getTime())){
			throw new NegocioException(ConstantesMensagem.MN013);
		}
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		if(lancamento.getData().after(calendar.getTime())){
			throw new NegocioException(ConstantesMensagem.MN013);
		}
		
		lancamento.setMesRef(mesAtual);
		lancamentoEC.guardar(lancamento);
	}
	
	public List<Lancamento> listar(){
		return lancamentoEC.listar();
	}

	public List<Lancamento> listarPorFiltro(LancamentoFiltro filtro) {
		return lancamentoEC.listarPorFiltro(filtro);
	}

	public List<String> descricoesQueContem(String descricao) {
		return lancamentoEC.descricoesQueContem(descricao);
	}
	
	public Lancamento buscarPorId(Long id){
		return lancamentoEC.bucarPorId(id);
	}
	
	@Transactional
	public void excluir(Lancamento lancamento){
		lancamento = lancamentoEC.bucarPorId(lancamento.getId());
		lancamentoEC.remover(lancamento);
	}

	public List<ResumoCategoria> retornarReceitas(
			MesRef mesRef) {
		return lancamentoEC.retornarReceitas(mesRef);
	}
	
	public List<ResumoCategoria> retornarDespesas(
			MesRef mesRef) {
		return lancamentoEC.retornarDespesas(mesRef);
	}

	public Double retornarTotalReceitas(MesRef mesRefAtual) {
		return lancamentoEC.retornarTotalReceitas(mesRefAtual);
	}
	
	public Double retornarTotalDespesas(MesRef mesRefAtual) {
		return lancamentoEC.retornarTotalDespesas(mesRefAtual);
	}

	public List<ResumoLancamento> listaReceitasAoDia(MesRef mesRef) {
		
		List<ResumoLancamento> receitasAoDia = lancamentoEC.listaReceitasAoDia(mesRef);
		List<ResumoLancamento> retorno = montarListaPorDia(mesRef,
				receitasAoDia);
		
		return retorno;
	}
	
	
	public List<ResumoLancamento> listaDespesasAoDia(MesRef mesRef) {
		
		List<ResumoLancamento> receitasAoDia = lancamentoEC.listaDespesasAoDia(mesRef);
		List<ResumoLancamento> retorno = montarListaPorDia(mesRef,
				receitasAoDia);
		
		return retorno;
	}

	private List<ResumoLancamento> montarListaPorDia(MesRef mesRef,
			List<ResumoLancamento> receitasAoDia) {
		Collections.sort(receitasAoDia);
		
		List<ResumoLancamento> retorno = new ArrayList<ResumoLancamento>();
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(mesRef.getData());
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		
		Double acumulado = 0.0;
		
		int ultimoDia = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		for (int i = 1; i <= ultimoDia; i++) {

			calendar.set(Calendar.DAY_OF_MONTH, i);
			
			ResumoLancamento resumo = new ResumoLancamento();
			resumo.setDescricao(sdf.format(calendar.getTime()));
			
			if(receitasAoDia.contains(resumo)){
				int index = receitasAoDia.indexOf(resumo);
				acumulado += receitasAoDia.get(index).getValor();
			}
			
			resumo.setValor(acumulado);
			
			retorno.add(resumo);
		}
		Collections.sort(retorno);
		return retorno;
	}

	public List<ResumoLancamento> retornarValorPorDespesas(MesRef mes) {
		return lancamentoEC.retornarValorPorDespesas(mes);
	}

	public List<ResumoLancamentoMes> retornarResumoMesUltimosDozesMeses(
			MesRef mes) {
		return lancamentoEC.retornarResumoMesUltimosDozesMeses(mes);
	}

	public Double retornarSaldoMes(MesRef mes) {
		return lancamentoEC.retornarSaldoMes(mes);
	}

	public List<Lancamento> listarReceitasPorMesRef(MesRef mesRef) {
		
		
		return lancamentoEC.listarReceitasPorMesRef(mesRef);
		
	}

	public List<Lancamento> listarDespesasPorMesRef(MesRef mesRef) {
		
		return lancamentoEC.listarDespesasPorMesRef(mesRef);
		
	}

	public List<Lancamento> listarPorMesRef(MesRef mesRef) {
		return lancamentoEC.listarPorMesRef(mesRef);
	}

	public LancamentoEC getLancamentoEC() {
		return lancamentoEC;
	}

	public void setLancamentoEC(LancamentoEC lancamentoEC) {
		this.lancamentoEC = lancamentoEC;
	}

	public MesRefEC getMesRefEC() {
		return mesRefEC;
	}

	public void setMesRefEC(MesRefEC mesRefEC) {
		this.mesRefEC = mesRefEC;
	}

	public List<Lancamento> listarPorAno(Integer ano, Integer periodo) {
		return lancamentoEC.listarPorAno(ano, periodo);
	}

	public List<Lancamento> listarPorAnoSubCategoria(Integer ano, Integer periodo, Long codigoSubCategoria) {
		return lancamentoEC.listarPorAnoSubCategoria(ano, periodo, codigoSubCategoria);
	}
}
