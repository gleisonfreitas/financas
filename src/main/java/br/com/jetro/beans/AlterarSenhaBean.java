package br.com.jetro.beans;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.jetro.constantes.ConstantesMensagem;
import br.com.jetro.modelo.Usuario;
import br.com.jetro.negocio.AlteraSenhaAS;
import br.com.jetro.negocio.NegocioException;

@Named
@ViewScoped
public class AlterarSenhaBean extends PageCode implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1104480687429994157L;
	
	@Inject
	private Usuario usuario;
	
	@Inject
	private AlteraSenhaAS alteraSenhaAS;
	
	private String senhaAtual;
	
	private String senhaNova;
	
	private String senhaNovaConfirmacao;
	
	public void preparar(){
		
		this.senhaAtual = null;
		this.senhaNova = null;
		this.senhaNovaConfirmacao = null;
		
	}
	
	public void alterarSenha(){
		
		try {
			alteraSenhaAS.alterarSenha(usuario, senhaAtual, senhaNova, senhaNovaConfirmacao);
			adicionarMensagem(ConstantesMensagem.MN017, FacesMessage.SEVERITY_INFO);
		} catch (NegocioException e) {
			adicionarMensagem(e.getMessage(), FacesMessage.SEVERITY_WARN);
		} catch (Exception e) {
			adicionarMensagem(ConstantesMensagem.MN002, FacesMessage.SEVERITY_ERROR);
		}
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}

	public String getSenhaNova() {
		return senhaNova;
	}

	public void setSenhaNova(String senhaNova) {
		this.senhaNova = senhaNova;
	}

	public String getSenhaNovaConfirmacao() {
		return senhaNovaConfirmacao;
	}

	public void setSenhaNovaConfirmacao(String senhaNovaConfirmacao) {
		this.senhaNovaConfirmacao = senhaNovaConfirmacao;
	}
	
	

}
