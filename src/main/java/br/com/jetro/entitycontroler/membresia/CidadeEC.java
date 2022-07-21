package br.com.jetro.entitycontroler.membresia;

import java.io.Serializable;
import java.util.List;

import br.com.jetro.entitycontroler.EntityControlerGenerics;
import br.com.jetro.modelo.membresia.Cidade;

public class CidadeEC extends EntityControlerGenerics<Cidade> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public List<Cidade> listarPorEstado(Long idEstado) {
		
		return (List<Cidade>) entityManager.createQuery(
				"Select c from Cidade c where c.estado.id = :idEstado", Cidade.class).
				setParameter("idEstado", idEstado).getResultList();
		
	}

}
