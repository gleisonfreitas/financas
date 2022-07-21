package br.com.jetro.beans;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

public class PageCode implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -249374377817746198L;

	
	public FacesContext getContext(){
		return FacesContext.getCurrentInstance();
	}
	
	public ServletContext getServletContext(){
		
		return (ServletContext) getContext().getExternalContext().getContext();
	}
	
	public String recuperarMensagemBuble(String idMensagem){
	
		return ResourceBundle.getBundle("br.com.jetro.resources.messages").getString(idMensagem);
	}
	
	public void adicionarMensagem(String idMensagem, Severity severity){
		String textoMessage = recuperarMensagemBuble(idMensagem);
		FacesMessage message = new FacesMessage(textoMessage);
		message.setSeverity(severity);
		getContext().addMessage(null, message);
	}
	
	public void adicionarMensagemObrigatorio(String campo){
		FacesMessage message = new FacesMessage("O campo "+campo+" é obrigatório.");
		message.setSeverity(FacesMessage.SEVERITY_WARN);
		getContext().addMessage(null, message);
	}
	
}
