package br.com.jetro.assistentes;

import java.io.Serializable;

public class ResumoLancamentoAnual implements Serializable, Comparable<ResumoLancamentoAnual>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3276206660066528324L;
	
	private Integer numeroMes;
	
	private String mes;
	
	private String descricao;
	
	private Double valor;
	
	public ResumoLancamentoAnual() {
		
		valor = 0.0;
	}
	
	@Override
	public int compareTo(ResumoLancamentoAnual o) {
		return this.numeroMes.compareTo(o.numeroMes);
	}
	
	public void addValor(Double valor){
		this.valor += valor;
	}
	
	public Integer getNumeroMes() {
		return numeroMes;
	}
	
	public void setNumeroMes(Integer numeroMes) {
		this.numeroMes = numeroMes;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
}
