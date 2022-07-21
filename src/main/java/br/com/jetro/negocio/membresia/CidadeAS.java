package br.com.jetro.negocio.membresia;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.jetro.entitycontroler.membresia.CidadeEC;
import br.com.jetro.modelo.membresia.Cidade;
import br.com.jetro.util.Transactional;

public class CidadeAS implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private CidadeEC cidadeEC;
	
	@Transactional
	public Cidade salvar(Cidade cidade){
		return cidadeEC.guardar(cidade);
	}

}
