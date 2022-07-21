package br.com.jetro.beans.membresia;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.jetro.beans.PageCode;
import br.com.jetro.entitycontroler.membresia.EstadoEC;
import br.com.jetro.modelo.membresia.Estado;

@Named
@ViewScoped
public class EstadoBean extends PageCode implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private EstadoEC estadoEC;
	
	private List<Estado> estados;
	
	@PostConstruct
	public void inicializar(){
		
		this.estados = estadoEC.listar();
	}
	
	public void incluir(){
		
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

}
