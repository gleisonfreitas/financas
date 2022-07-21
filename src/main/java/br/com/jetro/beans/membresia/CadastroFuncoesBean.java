package br.com.jetro.beans.membresia;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.jetro.beans.PageCode;
import br.com.jetro.constantes.ConstantesMensagem;
import br.com.jetro.entitycontroler.membresia.FuncaoEC;
import br.com.jetro.modelo.membresia.Funcao;
import br.com.jetro.negocio.membresia.FuncaoAS;

@Named
@ViewScoped
public class CadastroFuncoesBean extends PageCode implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private FuncaoEC funcaoEC;
	
	@Inject
	private FuncaoAS funcaoAS; 
	
	private Funcao funcao;
	
	private List<Funcao> funcoes;
	
	
	public void prepararCadastro(){
		this.funcao = new Funcao();
		this.funcoes = funcaoEC.listar();
	}
	
	public void salvarFuncao(){
		
		try {
			funcaoAS.salvar(this.funcao);
			adicionarMensagem(ConstantesMensagem.MN001, FacesMessage.SEVERITY_INFO);
			prepararCadastro();
		} catch (Exception e) {
			adicionarMensagem(ConstantesMensagem.MN002, FacesMessage.SEVERITY_ERROR);
		}
	}

	public Funcao getFuncao() {
		return funcao;
	}

	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}

	public List<Funcao> getFuncoes() {
		return funcoes;
	}

	public void setFuncoes(List<Funcao> funcoes) {
		this.funcoes = funcoes;
	}
}
