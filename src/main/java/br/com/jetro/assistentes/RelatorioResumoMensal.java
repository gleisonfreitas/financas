package br.com.jetro.assistentes;

import java.util.List;

import br.com.jetro.modelo.ConfiguracaoGeral;
import br.com.jetro.modelo.financas.MesRef;

public class RelatorioResumoMensal {
	
	
	private MesRef mesRef;
	
	private List<ResumoLancamentoMensal> listaReceitas;
	
	private List<ResumoLancamentoMensal> listaDespesas;
	
	private Double valorNacional;
	
	private Double descontoPrevidenciario;
	
	private Double saldoAnterior;
	
	private Double totalReceitas;
	
	private Double totalDespesas;
	
	private ConfiguracaoGeral configuracao;

	public MesRef getMesRef() {
		return mesRef;
	}

	public void setMesRef(MesRef mesRef) {
		this.mesRef = mesRef;
	}

	public List<ResumoLancamentoMensal> getListaReceitas() {
		return listaReceitas;
	}

	public void setListaReceitas(List<ResumoLancamentoMensal> listaReceitas) {
		this.listaReceitas = listaReceitas;
	}

	public List<ResumoLancamentoMensal> getListaDespesas() {
		return listaDespesas;
	}

	public void setListaDespesas(List<ResumoLancamentoMensal> listaDespesas) {
		this.listaDespesas = listaDespesas;
	}

	public Double getValorNacional() {
		return valorNacional;
	}

	public void setValorNacional(Double valorNacional) {
		this.valorNacional = valorNacional;
	}

	public Double getDescontoPrevidenciario() {
		return descontoPrevidenciario;
	}

	public void setDescontoPrevidenciario(Double descontoPrevidenciario) {
		this.descontoPrevidenciario = descontoPrevidenciario;
	}

	public Double getSaldoAnterior() {
		return saldoAnterior;
	}

	public void setSaldoAnterior(Double saldoAnterior) {
		this.saldoAnterior = saldoAnterior;
	}

	public Double getTotalReceitas() {
		return totalReceitas;
	}

	public void setTotalReceitas(Double totalReceitas) {
		this.totalReceitas = totalReceitas;
	}

	public Double getTotalDespesas() {
		return totalDespesas;
	}

	public void setTotalDespesas(Double totalDespesas) {
		this.totalDespesas = totalDespesas;
	}

	public ConfiguracaoGeral getConfiguracao() {
		return configuracao;
	}

	public void setConfiguracao(ConfiguracaoGeral configuracao) {
		this.configuracao = configuracao;
	}
	

}
