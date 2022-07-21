package br.com.jetro.negocio.membresia;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.jetro.entitycontroler.membresia.LiderancaEC;
import br.com.jetro.modelo.membresia.Lideranca;
import br.com.jetro.util.Transactional;

public class LiderancaAS implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private LiderancaEC liderancaEC;
	
	@Transactional
	public void salvar(Lideranca lideranca){
		liderancaEC.guardar(lideranca);
	}

}
