package br.com.jetro.entitycontroler;

import java.io.Serializable;

import javax.persistence.NoResultException;

import br.com.jetro.constantes.ConstantesMensagem;
import br.com.jetro.modelo.Usuario;
import br.com.jetro.negocio.NegocioException;

public class UsuarioEC extends EntityControlerGenerics<Usuario> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4682370452363813769L;
	
	
	public Usuario verificarLogin(Usuario usuario) throws NegocioException{
		
		try {
			return entityManager.createNamedQuery("login", Usuario.class)
					.setParameter("nome", usuario.getNome())
					.setParameter("senha", usuario.getSenha())
					.getSingleResult();
			
		} catch (NoResultException e) {
			throw new NegocioException(ConstantesMensagem.MN007);
		}catch (Exception e) {
			throw new NegocioException(ConstantesMensagem.MN008);
		}
	}
}
