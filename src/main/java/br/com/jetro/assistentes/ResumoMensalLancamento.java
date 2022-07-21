package br.com.jetro.assistentes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResumoMensalLancamento implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3209916397684097511L;
	
	
	private String nomeCategoria;
	
	private Double valorTotalCategoria;
	
	private List<ResumoLancamento> listaResumo;
	
	public ResumoMensalLancamento() {
		
		this.listaResumo = new ArrayList<ResumoLancamento>();
	}

	public String getNomeCategoria() {
		return nomeCategoria;
	}

	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}

	public Double getValorTotalCategoria() {
		return valorTotalCategoria;
	}

	public void setValorTotalCategoria(Double valorTotalCategoria) {
		this.valorTotalCategoria = valorTotalCategoria;
	}

	public List<ResumoLancamento> getListaResumo() {
		return listaResumo;
	}

	public void setListaResumo(List<ResumoLancamento> listaResumo) {
		this.listaResumo = listaResumo;
	}
	
	
	

}
