package br.com.jetro.entitycontroler.financas;

import java.io.Serializable;
import java.util.List;

import br.com.jetro.entitycontroler.EntityControlerGenerics;
import br.com.jetro.modelo.financas.SubCategoria;

public class SubCategoriaEC extends EntityControlerGenerics<SubCategoria> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6764888720358382566L;

	public List<SubCategoria> listarPorCategoria(Long idCategoria) {
		
		return (List<SubCategoria>) entityManager.createQuery(
				"Select sub from SubCategoria sub where sub.categoria.id = :idCategoria", SubCategoria.class).
				setParameter("idCategoria", idCategoria).getResultList();
		
	}

}
