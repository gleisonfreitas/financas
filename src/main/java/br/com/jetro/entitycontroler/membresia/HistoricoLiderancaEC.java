package br.com.jetro.entitycontroler.membresia;

import java.io.Serializable;
import java.util.List;

import br.com.jetro.entitycontroler.EntityControlerGenerics;
import br.com.jetro.modelo.membresia.HistoricoLideranca;

public class HistoricoLiderancaEC extends EntityControlerGenerics<HistoricoLideranca> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void excluirHistoricos(Long idDadosEclesiastico, List<Long> ids){
		
		entityManager.createNamedQuery("excluirHistorico")
		.setParameter("idDadosEclesiastico", idDadosEclesiastico)
		.setParameter("ids", ids)
		.executeUpdate();
	}
	
	public void excluirTodosHistoricos(Long idDadosEclesiastico){
		
		entityManager.createNamedQuery("excluirTodosHistorico")
		.setParameter("idDadosEclesiastico", idDadosEclesiastico)
		.executeUpdate();
	}

}
