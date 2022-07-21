package br.com.jetro.beans.membresia;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.jetro.beans.PageCode;
import br.com.jetro.constantes.ConstantesMensagem;
import br.com.jetro.entitycontroler.membresia.CidadeEC;
import br.com.jetro.entitycontroler.membresia.EstadoEC;
import br.com.jetro.modelo.membresia.Cidade;
import br.com.jetro.modelo.membresia.Estado;
import br.com.jetro.negocio.membresia.CidadeAS;

@Named
@ViewScoped
public class CadastroCidadesBean extends PageCode implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EstadoEC estadoEC;
	
	@Inject
	private CidadeAS cidadeAS;
	
	@Inject
	private CidadeEC cidadeEC;
	
	private Cidade cidade;
	
	private List<Estado> estados;
	
	private List<Cidade> cidades;
	
	@PostConstruct
	public void prepararCadastro(){
		this.cidade = new Cidade();
		this.estados = estadoEC.listar();
	}
	
	public void atualizarListaCidades(AjaxBehaviorEvent event){
		if(this.cidade.getEstado() != null && 
				this.cidade.getEstado().getId() != null){
			cidades = cidadeEC.listarPorEstado(this.cidade.getEstado().getId());
			Collections.sort(cidades);
		}else{
			cidades.clear();
		}
	}
	
	public void salvarCidades(){
		
		try {
			cidadeAS.salvar(this.cidade);
			adicionarMensagem(ConstantesMensagem.MN001, FacesMessage.SEVERITY_INFO);
			prepararCadastro();
		} catch (Exception e) {
			adicionarMensagem(ConstantesMensagem.MN002, FacesMessage.SEVERITY_ERROR);
		}
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

}
