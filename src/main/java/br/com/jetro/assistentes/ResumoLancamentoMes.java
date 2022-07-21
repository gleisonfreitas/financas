package br.com.jetro.assistentes;

import java.io.Serializable;
import java.util.Date;

public class ResumoLancamentoMes implements Serializable, Comparable<ResumoLancamentoMes>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7715054708268894062L;
	
	
	private Date data;
	
	private Double credito;
	
	private Double debito;
	
	private Double saldo;
	
	@Override
	public int compareTo(ResumoLancamentoMes o) {
		return this.getData().compareTo(o.getData());
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Double getCredito() {
		return credito;
	}

	public void setCredito(Double credito) {
		this.credito = credito;
	}

	public Double getDebito() {
		return debito;
	}

	public void setDebito(Double debito) {
		this.debito = debito;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

}
