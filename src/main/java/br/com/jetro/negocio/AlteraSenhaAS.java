package br.com.jetro.negocio;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.jetro.constantes.ConstantesMensagem;
import br.com.jetro.entitycontroler.UsuarioEC;
import br.com.jetro.modelo.Usuario;
import br.com.jetro.util.Transactional;

public class AlteraSenhaAS implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7160961568264045716L;
	
	@Inject
	private UsuarioEC usuarioEC;
	
	@Transactional
	public void alterarSenha(Usuario usuario, String... senhas) throws NegocioException{
		
		if(!usuario.getSenha().equals(senhas[0])){
			throw new NegocioException(ConstantesMensagem.MN015);
		}
		
		if(senhas[1].trim().isEmpty() || senhas[2].trim().isEmpty() || !senhas[1].equals(senhas[2])){
			throw new NegocioException(ConstantesMensagem.MN016);
		}
		
		usuario.setSenha(senhas[1]);

		usuarioEC.guardar(usuario);
	}

}
