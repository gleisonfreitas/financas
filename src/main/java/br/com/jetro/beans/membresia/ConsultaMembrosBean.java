package br.com.jetro.beans.membresia;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.jetro.assistentes.MembroFiltro;
import br.com.jetro.beans.PageCode;
import br.com.jetro.constantes.ConstantesMensagem;
import br.com.jetro.entitycontroler.membresia.MembroEC;
import br.com.jetro.modelo.membresia.Membro;
import br.com.jetro.negocio.membresia.MembroAS;

@Named
@ViewScoped
public class ConsultaMembrosBean extends PageCode implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private MembroEC membroEC;
	
	@Inject
	private MembroAS membroAS;
	
	private MembroFiltro filtro;
	
	private List<Membro> membros;
	
	private Long idExclusao;
	
	public void preparar(){
		this.filtro = new MembroFiltro();
	}
	
	public void consultar(){
		this.membros = membroEC.consultarPorFiltro(filtro);
		
	}
	
	public void excluir(){
		membroAS.remover(idExclusao);
		preparar();
		consultar();
		adicionarMensagem(ConstantesMensagem.MN025, FacesMessage.SEVERITY_INFO);
	}

	public MembroFiltro getFiltro() {
		return filtro;
	}

	public void setFiltro(MembroFiltro filtro) {
		this.filtro = filtro;
	}

	public List<Membro> getMembros() {
		return membros;
	}

	public void setMembros(List<Membro> membros) {
		this.membros = membros;
	}

	public Long getIdExclusao() {
		return idExclusao;
	}

	public void setIdExclusao(Long idExclusao) {
		this.idExclusao = idExclusao;
	}
	

}
