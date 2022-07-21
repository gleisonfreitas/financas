package br.com.jetro.assistentes;

import java.util.ArrayList;
import java.util.List;

import br.com.jetro.util.Util;

public class ResumoCategoria {
	
	private String descricao;
	private List<ResumoLancamento> listaSubCategoria;
	
	public ResumoCategoria() {
		this.listaSubCategoria = new ArrayList<ResumoLancamento>();
	}
	
	public String getTotal(){
		
		Double total = 0.0;
		
		for (ResumoLancamento resumoLancamento : listaSubCategoria) {
			total += resumoLancamento.getValor().doubleValue();
		}
		
		return Util.retornarNumeroBr(total);
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<ResumoLancamento> getListaSubCategoria() {
		return listaSubCategoria;
	}
	public void setListaSubCategoria(List<ResumoLancamento> listaSubCategoria) {
		this.listaSubCategoria = listaSubCategoria;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ResumoCategoria)) {
			return false;
		}
		ResumoCategoria other = (ResumoCategoria) obj;
		if (descricao == null) {
			if (other.descricao != null) {
				return false;
			}
		} else if (!descricao.equals(other.descricao)) {
			return false;
		}
		return true;
	}
}
