package br.com.jetro.negocio.financas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import br.com.jetro.assistentes.RelatorioGeral;
import br.com.jetro.assistentes.RelatorioResumoMensal;
import br.com.jetro.assistentes.ResumoLancamentoAnual;
import br.com.jetro.assistentes.ResumoLancamentoMensal;
import br.com.jetro.constantes.ConstantesMensagem;
import br.com.jetro.entitycontroler.ConfiguracaoGeralEC;
import br.com.jetro.enums.TipoLancamento;
import br.com.jetro.modelo.ConfiguracaoGeral;
import br.com.jetro.modelo.financas.Lancamento;
import br.com.jetro.modelo.financas.MesRef;
import br.com.jetro.modelo.financas.SubCategoria;
import br.com.jetro.negocio.NegocioException;
import br.com.jetro.util.Util;

public class RelatorioAS implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3591075668410294692L;
	
	private static final Long DIZIMO = 23L;

	@Inject
	private MesReferenciaAS mesRefAS;
	
	@Inject
	private LancamentoAS lancamentoAS;
	
	@Inject
	private ConfiguracaoGeralEC configuracaoGeralEC;

	public RelatorioResumoMensal consultarRelatorioResumoMensal(MesRef mesRef) throws NegocioException {
		
		RelatorioResumoMensal relatorio = new RelatorioResumoMensal();
		
		relatorio.setMesRef(mesRef);
		
		List<Lancamento> listaReceitas = lancamentoAS.listarReceitasPorMesRef(mesRef);
		List<ResumoLancamentoMensal> listaResumoReceitas = new ArrayList<ResumoLancamentoMensal>();
		montarResumoMensal(listaResumoReceitas, listaReceitas);
		Collections.sort(listaResumoReceitas);
		relatorio.setListaReceitas(listaResumoReceitas);
		
		List<Lancamento> listaDespesas = lancamentoAS.listarDespesasPorMesRef(mesRef);
		List<ResumoLancamentoMensal> listaResumoDespesas = new ArrayList<ResumoLancamentoMensal>();
		montarResumoMensal(listaResumoDespesas, listaDespesas);
		Collections.sort(listaResumoDespesas);
		relatorio.setListaDespesas(listaResumoDespesas);
		
		ConfiguracaoGeral configuracao = configuracaoGeralEC.bucarPorId(1L);
		
		relatorio.setConfiguracao(configuracao);
		relatorio.setValorNacional(calcularValorNacional(listaReceitas));
		relatorio.setDescontoPrevidenciario(relatorio.getValorNacional() * 0.2);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(mesRef.getData());
		calendar.add(Calendar.MONTH, -1);
		
		Date data = calendar.getTime();
		MesRef mesAnterior = mesRefAS.consultarMesRefPorData(data);
		
		relatorio.setSaldoAnterior(mesAnterior.getValorSaldo());
		
		relatorio.setTotalReceitas(calcularTotalReceitas(listaReceitas));
		relatorio.setTotalDespesas(calcularTotalDespesas(listaDespesas));
		
		return relatorio;
	}
	
	public List<Lancamento> consultarRelatorioDetalhadoMensal(MesRef mesRef) {
		
		return lancamentoAS.listarPorMesRef(mesRef);
		
	}
	
	private void montarResumoMensal(List<ResumoLancamentoMensal> listaRetorno,
			List<Lancamento> lista) {
		Map<SubCategoria, Double> mapSubCategoria = new HashMap<SubCategoria, Double>();
		
		for (Lancamento lancamento : lista) {
			if(mapSubCategoria.containsKey(lancamento.getSubCategoria())){
				Double valor = mapSubCategoria.get(lancamento.getSubCategoria());
				valor += lancamento.getValor();
				mapSubCategoria.put(lancamento.getSubCategoria(), valor);
			}else{
				mapSubCategoria.put(lancamento.getSubCategoria(), lancamento.getValor());
			}
		}
		
		for (Entry<SubCategoria, Double> entry : mapSubCategoria.entrySet()) {
			ResumoLancamentoMensal resumo = new ResumoLancamentoMensal();
			resumo.setNomeCategoria(entry.getKey().getCategoria().getDescricao());
			resumo.setNomeSubCategoria(entry.getKey().getDescricao());
			resumo.setValor(entry.getValue());
			
			listaRetorno.add(resumo);
		}
	}

	private Double calcularTotalDespesas(List<Lancamento> listaDespesas) {
		
		Double valor = 0.0;
		
		for (Lancamento lancamento : listaDespesas) {
			valor += lancamento.getValor();
		}
		
		return valor;
	}

	private Double calcularTotalReceitas(List<Lancamento> listaReceitas) {
		
		Double valor = 0.0;
		
		for (Lancamento lancamento : listaReceitas) {
			valor += lancamento.getValor();
		}
		
		return valor;
	}

	private Double calcularValorNacional(List<Lancamento> listaReceitas) {
		
		Double valor = 0.0;
		
		for (Lancamento lancamento : listaReceitas) {
			if(DIZIMO.equals(lancamento.getSubCategoria().getId())){
				valor += lancamento.getValor();
			}
		}
		
		return valor * 0.10;
	}
	
	public RelatorioGeral consultarRelatorioGeral(Integer ano, Integer periodo) throws NegocioException{
		
		if(ano == null){
			throw new NegocioException(ConstantesMensagem.MN021);
		}else if(ano < 1000){
			throw new NegocioException(ConstantesMensagem.MN022);
		}
		
		
		List<Lancamento> listaPorAno = lancamentoAS.listarPorAno(ano, periodo);
		
		if(listaPorAno == null || listaPorAno.isEmpty()){
			throw new NegocioException(ConstantesMensagem.MN020);
		}
		
		RelatorioGeral relatorioGeral = new RelatorioGeral(ano, periodo);
		ResumoLancamentoAnual resumoSaldoInicial = iniciarResumoAnual(ano, periodo);
		ResumoLancamentoAnual resumoReceitas = new ResumoLancamentoAnual();
		resumoReceitas.setDescricao("RECEITAS");
		ResumoLancamentoAnual resumoDespesas = new ResumoLancamentoAnual();
		resumoDespesas.setDescricao("DESPESAS");
		ResumoLancamentoAnual resumoSaldoFinal = new ResumoLancamentoAnual();
		resumoSaldoFinal.setDescricao("SALDO FINAL");
		resumoSaldoFinal.addValor(resumoSaldoInicial.getValor());
		List<ResumoLancamentoAnual> listaAno = new ArrayList<ResumoLancamentoAnual>();
		listaAno.add(resumoSaldoInicial);
		listaAno.add(resumoReceitas);
		listaAno.add(resumoDespesas);
		listaAno.add(resumoSaldoFinal);
		relatorioGeral.setResumoLancamentoAnual(listaAno);
		
		Map<String, ResumoLancamentoAnual> map = new HashMap<String, ResumoLancamentoAnual>();
		Map<String, ResumoLancamentoAnual> mapReceitas = new HashMap<String, ResumoLancamentoAnual>();
		Map<String, ResumoLancamentoAnual> mapDespesas = new HashMap<String, ResumoLancamentoAnual>();
		Map<String, ResumoLancamentoAnual> mapGrupoDespesas = new HashMap<String, ResumoLancamentoAnual>();
		
		for (Lancamento lancamento : listaPorAno) {
			preencherResumoAnual(relatorioGeral, lancamento);
			preencherResumoMes(relatorioGeral, lancamento, map);
			preencherResumoMesReceitasDespesas(relatorioGeral, lancamento, mapReceitas, mapDespesas, mapGrupoDespesas);
		}
		
		resumoSaldoFinal.addValor(resumoReceitas.getValor() - resumoDespesas.getValor());
		
		return relatorioGeral;
	}

	private void preencherResumoMesReceitasDespesas(RelatorioGeral relatorioGeral, Lancamento lancamento, 
			Map<String, ResumoLancamentoAnual> mapReceitas, Map<String, ResumoLancamentoAnual> mapDespesas, 
			Map<String, ResumoLancamentoAnual> mapGrupoDespesas) throws NegocioException {
		
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(lancamento.getData());
		String key = ""+calendar.get(Calendar.MONTH)+""+ lancamento.getSubCategoria().getId();
		
		String keyGrupoDespesas = ""+calendar.get(Calendar.MONTH)+""+lancamento.getSubCategoria().getCategoria().getId();
		
		if(TipoLancamento.CREDITO.equals(lancamento.getSubCategoria().getCategoria().getTipoLancamento())){
			
			ResumoLancamentoAnual resumo = mapReceitas.get(key);
			if(resumo != null){
				resumo.addValor(lancamento.getValor());
			}else{
				resumo = new ResumoLancamentoAnual();
				resumo.setDescricao(lancamento.getSubCategoria().getDescricao());
				resumo.setNumeroMes(calendar.get(Calendar.MONTH));
				resumo.setMes(Util.retornarNomeMes(lancamento.getData()));
				resumo.addValor(lancamento.getValor());
				
				mapReceitas.put(key, resumo);
				relatorioGeral.getResumoLancamentoMesReceitas().add(resumo);
			}
			if( "Ofertas".equals(resumo.getDescricao().trim())){
				relatorioGeral.getGraficoOferta().add(resumo);
			}else if( "Dízimos".equals(resumo.getDescricao().trim())) {
				relatorioGeral.getGraficoDizimo().add(resumo);
			}
		}else{
			
			ResumoLancamentoAnual resumo = mapDespesas.get(key);
			
			if(resumo != null){
				resumo.addValor(lancamento.getValor());
			}else{
				resumo = new ResumoLancamentoAnual();
				resumo.setDescricao(lancamento.getSubCategoria().getDescricao());
				resumo.setNumeroMes(calendar.get(Calendar.MONTH));
				resumo.setMes(Util.retornarNomeMes(lancamento.getData()));
				resumo.addValor(lancamento.getValor());
				
				mapDespesas.put(key, resumo);
				relatorioGeral.getResumoLancamentoMesDespesas().add(resumo);
			}
			
			if( "Energia".equals(resumo.getDescricao().trim())){
				relatorioGeral.getGraficoEnergia().add(resumo);
			}
			
			ResumoLancamentoAnual resumoGrupoDespesa = mapGrupoDespesas.get(keyGrupoDespesas);
			
			if(resumoGrupoDespesa != null){
				resumoGrupoDespesa.addValor(lancamento.getValor());
			}else{
				resumoGrupoDespesa = new ResumoLancamentoAnual();
				resumoGrupoDespesa.setDescricao(lancamento.getSubCategoria().getCategoria().getDescricao());
				resumoGrupoDespesa.setNumeroMes(calendar.get(Calendar.MONTH));
				resumoGrupoDespesa.setMes(Util.retornarNomeMes(lancamento.getData()));
				resumoGrupoDespesa.addValor(lancamento.getValor());
				
				mapGrupoDespesas.put(keyGrupoDespesas, resumoGrupoDespesa);
				relatorioGeral.getGraficoGrupoDespesas().add(resumoGrupoDespesa);
			}
			
		}
		
		Collections.sort(relatorioGeral.getResumoLancamentoMesReceitas());
		Collections.sort(relatorioGeral.getResumoLancamentoMesDespesas());
		Collections.sort(relatorioGeral.getGraficoOferta());
		Collections.sort(relatorioGeral.getGraficoDizimo());
		Collections.sort(relatorioGeral.getGraficoEnergia());
		Collections.sort(relatorioGeral.getGraficoGrupoDespesas());
	}

	private ResumoLancamentoAnual iniciarResumoAnual(Integer ano, Integer periodo) throws NegocioException {
		
		ResumoLancamentoAnual resumo = new ResumoLancamentoAnual();
		
		Calendar calendar = Calendar.getInstance();
		
		if(periodo != 1){
			//Ano anterior
			calendar.set(Calendar.YEAR, ano-1);
			//Mês dezembro
			calendar.set(Calendar.MONTH, Calendar.DECEMBER);
		} else {
			calendar.set(Calendar.YEAR, ano);
			calendar.set(Calendar.MONTH, Calendar.JUNE);
		}
		
		Date dataSaldoAnterior = calendar.getTime();
		saldoInicial(resumo, dataSaldoAnterior);
		
		return resumo;
	}

	private void preencherResumoMes(RelatorioGeral relatorioGeral, Lancamento lancamento, 
			Map<String, ResumoLancamentoAnual> map) throws NegocioException {
		
		Calendar calendar = Calendar.getInstance();
			
		calendar.setTime(lancamento.getData());
		int numMes = calendar.get(Calendar.MONTH);
		String keySaldoInicial = numMes+"0";
		String keyReceitas = numMes+"1";
		String keyDespesas = numMes+"2";
		String keySaldoFinal = numMes+"3";
		
		ResumoLancamentoAnual resumoSaldoInicial = map.get(keySaldoInicial);
		ResumoLancamentoAnual resumoSaldoFinal = map.get(keySaldoFinal);
		if(resumoSaldoInicial == null){
			resumoSaldoInicial = new ResumoLancamentoAnual();
			resumoSaldoFinal = new ResumoLancamentoAnual();
			calendar.add(Calendar.MONTH, -1);
			Date dataMesAnterior = calendar.getTime();
			saldoInicial(resumoSaldoInicial, dataMesAnterior);
			resumoSaldoInicial.setNumeroMes(numMes);
			resumoSaldoInicial.setMes(Util.retornarNomeMes(lancamento.getData()));
			
			resumoSaldoFinal.setNumeroMes(numMes);
			resumoSaldoFinal.setMes(Util.retornarNomeMes(lancamento.getData()));
			resumoSaldoFinal.setDescricao("Saldo final");
			resumoSaldoFinal.addValor(resumoSaldoInicial.getValor());
			
			map.put(keySaldoInicial, resumoSaldoInicial);
			map.put(keySaldoFinal, resumoSaldoFinal);
			relatorioGeral.getResumoLancamentoMes().add(resumoSaldoInicial);
			relatorioGeral.getResumoLancamentoMes().add(resumoSaldoFinal);
		}
		
		ResumoLancamentoAnual resumo = null;
		String descricao;
		String key;
		if(TipoLancamento.CREDITO.equals(lancamento.getSubCategoria().getCategoria().getTipoLancamento())){
			resumo = map.get(keyReceitas);
			descricao = "Receitas";
			key = keyReceitas;
			resumoSaldoFinal.addValor(lancamento.getValor());
		}else{
			resumo = map.get(keyDespesas);
			descricao = "Despesas";
			key = keyDespesas;
			resumoSaldoFinal.addValor(-lancamento.getValor());
		}
		if(resumo != null){
			resumo.addValor(lancamento.getValor());
		}else{
			resumo = new ResumoLancamentoAnual();
			resumo.setNumeroMes(numMes);
			resumo.setMes(Util.retornarNomeMes(lancamento.getData()));
			resumo.setDescricao(descricao);
			resumo.addValor(lancamento.getValor());
			
			map.put(key, resumo);
			relatorioGeral.getResumoLancamentoMes().add(resumo);
		}
			
		
		Collections.sort(relatorioGeral.getResumoLancamentoMes());
		
	}

	private void preencherResumoAnual(RelatorioGeral relatorioGeral, Lancamento lancamento) throws NegocioException {
		
		if(TipoLancamento.CREDITO.equals(lancamento.getSubCategoria().getCategoria().getTipoLancamento())){
			ResumoLancamentoAnual resumo = relatorioGeral.getResumoLancamentoAnual().get(1);
			resumo.addValor(lancamento.getValor());
		}else{
			ResumoLancamentoAnual resumo = relatorioGeral.getResumoLancamentoAnual().get(2);
			resumo.addValor(lancamento.getValor());
		}
		
	}

	private void saldoInicial(ResumoLancamentoAnual resumo, Date dataMesAnterior) throws NegocioException {
		try {
			resumo.setDescricao("Saldo inicial");
			MesRef mesRef = mesRefAS.consultarMesRefPorData(dataMesAnterior);
			resumo.setValor(mesRef.getValorSaldo());
		} catch (NegocioException e) {
			if(ConstantesMensagem.MN012.equals(e.getMessage())){
				resumo.setValor(0.0);
			}else{
				throw e;
			}
		}
	}

	public List<ResumoLancamentoAnual> consultarRelatorioAnualPorSubCategoria(Integer ano, Integer periodo, Long codigoSubCategoria) throws NegocioException {
		
		
		if(ano == null){
			throw new NegocioException(ConstantesMensagem.MN021);
		}else if(ano < 1000){
			throw new NegocioException(ConstantesMensagem.MN022);
		}
		
		if(codigoSubCategoria == null){
			throw new NegocioException(ConstantesMensagem.MN023);
		}
		List<Lancamento> listaPorAnoSubCategoria = lancamentoAS.listarPorAnoSubCategoria(ano, periodo, codigoSubCategoria);
		
		if(listaPorAnoSubCategoria == null || listaPorAnoSubCategoria.isEmpty()){
			throw new NegocioException(ConstantesMensagem.MN020);
		}
		
		List<ResumoLancamentoAnual> listaResumo = new ArrayList<ResumoLancamentoAnual>();
		
		Map<Integer, ResumoLancamentoAnual> map = new HashMap<Integer, ResumoLancamentoAnual>();
		
		
		for (Lancamento lancamento : listaPorAnoSubCategoria) {
			
			Calendar calendar = Calendar.getInstance();
			
			calendar.setTime(lancamento.getData());
			int key = calendar.get(Calendar.MONTH);
			
			ResumoLancamentoAnual resumo = map.get(key);
			if(resumo != null){
				resumo.addValor(lancamento.getValor());
			}else{
				resumo = new ResumoLancamentoAnual();
				resumo.setDescricao(lancamento.getSubCategoria().getDescricao());
				resumo.setValor(lancamento.getValor());
				resumo.setNumeroMes(key);
				resumo.setMes(Util.retornarNomeMes(lancamento.getData()));
				
				map.put(key, resumo);
				
				listaResumo.add(resumo);
			}
		}
		
		Collections.sort(listaResumo);
		
		return listaResumo;
	}

}
