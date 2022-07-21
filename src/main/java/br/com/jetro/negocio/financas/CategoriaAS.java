package br.com.jetro.negocio.financas;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.jetro.entitycontroler.financas.CategoriaEC;
import br.com.jetro.modelo.financas.Categoria;
import br.com.jetro.util.Transactional;

public class CategoriaAS implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1392633781350316414L;
	
	@Inject
	private CategoriaEC categoriaEC;

	@Transactional
	public void salvar(Categoria categoria){
		
		categoriaEC.guardar(categoria);
	}
	
	@Transactional
	public void excluir(Categoria categoria){
		categoriaEC.guardar(categoria);
	}
	
	public List<Categoria> listar(){
		
		return categoriaEC.listar();
	}

}
