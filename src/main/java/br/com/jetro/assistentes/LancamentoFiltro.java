package br.com.jetro.assistentes;

import java.util.Date;

import br.com.jetro.modelo.financas.Categoria;
import br.com.jetro.modelo.financas.SubCategoria;

public class LancamentoFiltro {
	
	private Date dataInicio;
	private Date dataFim;
	private String descricao;
	private Categoria categoria;
	private SubCategoria subCategoria;
	private Double valorMin;
	private Double valorMax;
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	public SubCategoria getSubCategoria() {
		return subCategoria;
	}
	public void setSubCategoria(SubCategoria subCategoria) {
		this.subCategoria = subCategoria;
	}
	public Double getValorMin() {
		return valorMin;
	}
	public void setValorMin(Double valorMin) {
		this.valorMin = valorMin;
	}
	public Double getValorMax() {
		return valorMax;
	}
	public void setValorMax(Double valorMax) {
		this.valorMax = valorMax;
	}
}
