package br.com.jetro.assistentes;

import java.io.Serializable;

public class ResumoLancamentoMensal implements Serializable, Comparable<ResumoLancamentoMensal>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7987290403175594438L;
	
	
	private String nomeCategoria;
	
	private String nomeSubCategoria;
	
	private Double valor;
	
	@Override
	public int compareTo(ResumoLancamentoMensal o) {
		return this.nomeCategoria.compareTo(o.getNomeCategoria());
	}

	public String getNomeCategoria() {
		return nomeCategoria;
	}

	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}

	public String getNomeSubCategoria() {
		return nomeSubCategoria;
	}

	public void setNomeSubCategoria(String nomeSubCategoria) {
		this.nomeSubCategoria = nomeSubCategoria;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

}
