package br.com.jetro.beans.membresia;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.jetro.beans.PageCode;
import br.com.jetro.constantes.ConstantesMensagem;
import br.com.jetro.entitycontroler.membresia.LiderancaEC;
import br.com.jetro.modelo.membresia.Lideranca;
import br.com.jetro.negocio.membresia.LiderancaAS;

@Named
@ViewScoped
public class CadastroLiderancasBean extends PageCode implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private LiderancaEC liderancaEC;
	
	@Inject
	private LiderancaAS liderancaAS; 
	
	private Lideranca lideranca;
	
	private List<Lideranca> liderancas;
	
	
	public void prepararCadastro(){
		this.lideranca = new Lideranca();
		this.liderancas = liderancaEC.listar();
	}
	
	public void salvarLideranca(){
		
		try {
			liderancaAS.salvar(this.lideranca);
			adicionarMensagem(ConstantesMensagem.MN001, FacesMessage.SEVERITY_INFO);
			prepararCadastro();
		} catch (Exception e) {
			adicionarMensagem(ConstantesMensagem.MN002, FacesMessage.SEVERITY_ERROR);
		}
	}

	public Lideranca getLideranca() {
		return lideranca;
	}

	public void setLideranca(Lideranca lideranca) {
		this.lideranca = lideranca;
	}

	public List<Lideranca> getLiderancas() {
		return liderancas;
	}

	public void setLiderancas(List<Lideranca> liderancas) {
		this.liderancas = liderancas;
	}
}
