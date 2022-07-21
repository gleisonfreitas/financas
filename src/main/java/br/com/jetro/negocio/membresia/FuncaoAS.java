package br.com.jetro.negocio.membresia;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.jetro.entitycontroler.membresia.FuncaoEC;
import br.com.jetro.modelo.membresia.Funcao;
import br.com.jetro.util.Transactional;

public class FuncaoAS implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private FuncaoEC funcaoEC;
	
	@Transactional
	public void salvar(Funcao funcao){
		funcaoEC.guardar(funcao);
	}

}
