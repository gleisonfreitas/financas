package br.com.jetro.beans;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.jetro.constantes.ConstantesMensagem;
import br.com.jetro.modelo.ConfiguracaoGeral;
import br.com.jetro.negocio.ConfiguracaoGeralAS;


@Named
@SessionScoped
public class ConfiguracoesBean extends PageCode implements Serializable{
	
	
	private ConfiguracaoGeral configuracaoGeral;
	
	@Inject
	private ConfiguracaoGeralAS configuracaoGeralAS;
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -2149704771595732778L;
	
	public void prepararConfiguracoes(){
		this.configuracaoGeral = configuracaoGeralAS.bucarPorId(1L);
	}
	
	public void salvar(){
		try{
			configuracaoGeralAS.salvar(configuracaoGeral);
			adicionarMensagem(ConstantesMensagem.MN001, FacesMessage.SEVERITY_INFO);
		} catch (Exception e) {
			adicionarMensagem(ConstantesMensagem.MN002, FacesMessage.SEVERITY_ERROR);
		}
	}

	public ConfiguracaoGeral getConfiguracaoGeral() {
		return configuracaoGeral;
	}

	public void setConfiguracaoGeral(ConfiguracaoGeral configuracaoGeral) {
		this.configuracaoGeral = configuracaoGeral;
	}
	
	

}
