package br.com.jetro.negocio;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.jetro.entitycontroler.ConfiguracaoGeralEC;
import br.com.jetro.modelo.ConfiguracaoGeral;
import br.com.jetro.util.Transactional;

public class ConfiguracaoGeralAS implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6375374970158869881L;
	
	
	@Inject
	private ConfiguracaoGeralEC configuracaoGeralEC;
	
	@Transactional
	public void salvar(ConfiguracaoGeral configuracaoGeral){
		configuracaoGeralEC.guardar(configuracaoGeral);
	}

	public ConfiguracaoGeral bucarPorId(Long id) {
		return configuracaoGeralEC.bucarPorId(id);
	}
	
}
