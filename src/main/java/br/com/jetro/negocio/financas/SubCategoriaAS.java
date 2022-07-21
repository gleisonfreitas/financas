package br.com.jetro.negocio.financas;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.jetro.entitycontroler.financas.SubCategoriaEC;
import br.com.jetro.modelo.financas.SubCategoria;
import br.com.jetro.util.Transactional;

public class SubCategoriaAS implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5935215564820558342L;

	@Inject
	private SubCategoriaEC subCategoriaEC;
	
	@Transactional
	public void salvar(SubCategoria subCategoria){
		subCategoriaEC.guardar(subCategoria);
	}

	@Transactional
	public void excluir(SubCategoria subCategoria){
		subCategoria = subCategoriaEC.bucarPorId(subCategoria.getId());
		subCategoriaEC.remover(subCategoria);
	}
	
	public List<SubCategoria> listar(){
		return subCategoriaEC.listar();
	}
	
	public List<SubCategoria> listarPorCategoria(Long idCategoria){
		return subCategoriaEC.listarPorCategoria(idCategoria);
	}
}
