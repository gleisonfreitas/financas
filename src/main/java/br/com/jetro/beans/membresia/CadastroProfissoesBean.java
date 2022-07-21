package br.com.jetro.beans.membresia;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.jetro.beans.PageCode;
import br.com.jetro.constantes.ConstantesMensagem;
import br.com.jetro.entitycontroler.membresia.ProfissaoEC;
import br.com.jetro.modelo.membresia.Profissao;
import br.com.jetro.negocio.membresia.ProfissaoAS;

@Named
@ViewScoped
public class CadastroProfissoesBean extends PageCode implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ProfissaoEC profissaoEC;
	
	@Inject
	private ProfissaoAS profissaoAS; 
	
	
	private Profissao profissao;
	
	private List<Profissao> profissoes;
	
	
	public void prepararCadastro(){
		this.profissao = new Profissao();
		this.profissoes = profissaoEC.listar();
	}
	
	public void salvarProfissao(){
		
		try {
			profissaoAS.salvar(this.profissao);
			adicionarMensagem(ConstantesMensagem.MN001, FacesMessage.SEVERITY_INFO);
			prepararCadastro();
		} catch (Exception e) {
			adicionarMensagem(ConstantesMensagem.MN002, FacesMessage.SEVERITY_ERROR);
		}
	}

	public Profissao getProfissao() {
		return profissao;
	}

	public void setProfissao(Profissao profissao) {
		this.profissao = profissao;
	}

	public List<Profissao> getProfissoes() {
		return profissoes;
	}

	public void setProfissoes(List<Profissao> profissoes) {
		this.profissoes = profissoes;
	}

}
