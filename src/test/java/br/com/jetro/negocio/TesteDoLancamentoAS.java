package br.com.jetro.negocio;

import java.io.Serializable;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import br.com.jetro.modelo.financas.Lancamento;
import br.com.jetro.negocio.financas.LancamentoAS;

public class TesteDoLancamentoAS implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7764141510905658156L;
	
	private LancamentoAS lancamentoAS;
	
	@Before
	public void inicializa(){
		this.lancamentoAS = new LancamentoAS();
	}
	
	@Test(expected=NegocioException.class)
	public void naoDeveSalvarLancamentoComValorMenorQueZero() throws NegocioException{

		//Cenário
		Lancamento lancamento = new Lancamento();
		lancamento.setData(new Date());
		lancamento.setDescricao("Teste salvar lancamento");
		lancamento.setValor(-100.0);

		//Acao
		lancamentoAS.salvar(lancamento);

	}

	@Test(expected=NegocioException.class)
	public void naoDeveSalvarLancamentoComDataAnteriorAtual() throws NegocioException{

		//Cenário
		Lancamento lancamento = new Lancamento();
		lancamento.setData(new Date(2016,12,31));
		lancamento.setDescricao("Teste salvar lancamento");
		lancamento.setValor(100.0);

		//Validacao
		lancamentoAS.salvar(lancamento);

	}

}
