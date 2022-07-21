package br.com.jetro.beans;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.jetro.constantes.ConstantesMensagem;
import br.com.jetro.entitycontroler.UsuarioEC;
import br.com.jetro.modelo.Usuario;
import br.com.jetro.negocio.NegocioException;

@Named
@SessionScoped
public class LoginBean extends PageCode{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3109186378539319160L;

	@Inject
	private Usuario usuario;
	
	@Inject
	private UsuarioEC usuarioEC;
	
	private String nome;
	
	private String senha;
	
	public String login(){
		
		try {
			
			usuario.setNome(this.nome.trim());
			usuario.setSenha(this.senha.trim());
			usuario = usuarioEC.verificarLogin(usuario);
			return "/inicio?faces-redirect=true";
		} catch (NegocioException e) {
			adicionarMensagem(e.getMessage(), FacesMessage.SEVERITY_ERROR);
		} catch (Exception e) {
			adicionarMensagem(ConstantesMensagem.MN002, FacesMessage.SEVERITY_ERROR);
		}
		usuario.setNome(null);
		usuario.setSenha(null);
		
		return null;
	}
	
	public String logout(){
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		
		return "/login?faces-redirect=true";
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	

}
