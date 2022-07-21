package br.com.jetro.negocio;

import org.junit.Before;
import org.junit.Test;

import br.com.jetro.modelo.Usuario;

public class AlteraSenhaASTest {
	
	private Usuario usuario;
	private AlteraSenhaAS alteraSenhaAS;
	
	@Before
	public void inicializa(){
		this.usuario = new Usuario();
		this.usuario.setNome("Gleison");
		this.usuario.setSenha("123");
		
		this.alteraSenhaAS = new AlteraSenhaAS();
	}
	
	@Test(expected=NegocioException.class)
	public void naoPermitirAlterarSenhaComSenhaAtualIncorreta() throws NegocioException{
		
		
		String senhaAtual = "321";
		
		alteraSenhaAS.alterarSenha(usuario, senhaAtual);
		
	}
	
	@Test(expected=NegocioException.class)
	public void naoPermitirAlrerarSenhaComSenhaNovaQueNaoCoincidem() throws NegocioException{
		
		String senhaAtual = "123";
		
		String senhaNova = "1234";
		
		String senhaNovaConfirmacao = "321";
		
		alteraSenhaAS.alterarSenha(usuario, senhaAtual, senhaNova, senhaNovaConfirmacao);
		
		
	}

}
