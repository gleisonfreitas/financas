package br.com.jetro.negocio.membresia;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.jetro.entitycontroler.membresia.ProfissaoEC;
import br.com.jetro.modelo.membresia.Profissao;
import br.com.jetro.util.Transactional;

public class ProfissaoAS implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ProfissaoEC profissaoEC;
	
	@Transactional
	public Profissao salvar(Profissao profissao){
		return profissaoEC.guardar(profissao);
	}

}
