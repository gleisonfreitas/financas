package br.com.jetro.assistentes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RelatorioGeral implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3592044457772415765L;
	
	private Integer ano;
	
	private Integer periodo;
	
	private List<ResumoLancamentoAnual> resumoLancamentoAnual;
	
	private List<ResumoLancamentoAnual> resumoLancamentoMes;
	
	private List<ResumoLancamentoAnual> resumoLancamentoMesReceitas;
	
	private List<ResumoLancamentoAnual> graficoOferta;
	
	private List<ResumoLancamentoAnual> graficoDizimo;
	
	private List<ResumoLancamentoAnual> graficoEnergia;
	
	private List<ResumoLancamentoAnual> graficoGrupoDespesas;
	
	private List<ResumoLancamentoAnual> resumoLancamentoMesDespesas;
	
	private List<ResumoLancamentoAnual> resumoLancamentoPorSubCategoria;
	
	public RelatorioGeral(Integer ano, Integer periodo) {
		this.ano = ano;
		this.periodo = periodo;
		this.resumoLancamentoAnual = new ArrayList<ResumoLancamentoAnual>();
		this.resumoLancamentoMes = new ArrayList<ResumoLancamentoAnual>();
		this.resumoLancamentoMesReceitas = new ArrayList<ResumoLancamentoAnual>();
		this.graficoOferta = new ArrayList<ResumoLancamentoAnual>();
		this.graficoDizimo = new ArrayList<ResumoLancamentoAnual>();
		this.graficoEnergia = new ArrayList<ResumoLancamentoAnual>();
		this.graficoGrupoDespesas = new ArrayList<ResumoLancamentoAnual>();
		this.resumoLancamentoMesDespesas = new ArrayList<ResumoLancamentoAnual>();
		this.resumoLancamentoPorSubCategoria = new ArrayList<ResumoLancamentoAnual>();
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}
	
	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}

	public List<ResumoLancamentoAnual> getResumoLancamentoAnual() {
		return resumoLancamentoAnual;
	}

	public void setResumoLancamentoAnual(List<ResumoLancamentoAnual> resumoLancamentoAnual) {
		this.resumoLancamentoAnual = resumoLancamentoAnual;
	}

	public List<ResumoLancamentoAnual> getResumoLancamentoMes() {
		return resumoLancamentoMes;
	}

	public void setResumoLancamentoMes(List<ResumoLancamentoAnual> resumoLancamentoMes) {
		this.resumoLancamentoMes = resumoLancamentoMes;
	}

	public List<ResumoLancamentoAnual> getResumoLancamentoMesReceitas() {
		return resumoLancamentoMesReceitas;
	}

	public void setResumoLancamentoMesReceitas(List<ResumoLancamentoAnual> resumoLancamentoMesReceitas) {
		this.resumoLancamentoMesReceitas = resumoLancamentoMesReceitas;
	}
	
	public List<ResumoLancamentoAnual> getGraficoOferta() {
		return graficoOferta;
	}

	public void setGraficoOferta(List<ResumoLancamentoAnual> graficoOferta) {
		this.graficoOferta = graficoOferta;
	}

	public List<ResumoLancamentoAnual> getGraficoDizimo() {
		return graficoDizimo;
	}

	public void setGraficoDizimo(List<ResumoLancamentoAnual> graficoDizimo) {
		this.graficoDizimo = graficoDizimo;
	}

	public List<ResumoLancamentoAnual> getGraficoEnergia() {
		return graficoEnergia;
	}

	public void setGraficoEnergia(List<ResumoLancamentoAnual> graficoEnergia) {
		this.graficoEnergia = graficoEnergia;
	}

	public List<ResumoLancamentoAnual> getGraficoGrupoDespesas() {
		return graficoGrupoDespesas;
	}

	public void setGraficoGrupoDespesas(List<ResumoLancamentoAnual> graficoGrupoDespesas) {
		this.graficoGrupoDespesas = graficoGrupoDespesas;
	}

	public List<ResumoLancamentoAnual> getResumoLancamentoMesDespesas() {
		return resumoLancamentoMesDespesas;
	}

	public void setResumoLancamentoMesDespesas(List<ResumoLancamentoAnual> resumoLancamentoMesDespesas) {
		this.resumoLancamentoMesDespesas = resumoLancamentoMesDespesas;
	}

	public List<ResumoLancamentoAnual> getResumoLancamentoPorSubCategoria() {
		return resumoLancamentoPorSubCategoria;
	}

	public void setResumoLancamentoPorSubCategoria(List<ResumoLancamentoAnual> resumoLancamentoPorSubCategoria) {
		this.resumoLancamentoPorSubCategoria = resumoLancamentoPorSubCategoria;
	}
	
}
