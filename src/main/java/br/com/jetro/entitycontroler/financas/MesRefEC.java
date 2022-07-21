package br.com.jetro.entitycontroler.financas;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.NoResultException;

import br.com.jetro.entitycontroler.EntityControlerGenerics;
import br.com.jetro.modelo.financas.MesRef;

public class MesRefEC extends EntityControlerGenerics<MesRef> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7545778465169129491L;

	public MesRef buscarMesAtual() {
		
		MesRef mesRef;
		try {
			mesRef = entityManager.createNamedQuery("mesAtual", MesRef.class).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		
		return mesRef;
		
	}

	public MesRef consultarMesRefPorData(Date data){
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);
		
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		Date dataInicio = calendar.getTime();

		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date dataFim = calendar.getTime();
		
		try {
			return entityManager.createNamedQuery("consultaMesRefPorData", MesRef.class)
					.setParameter("dataInicio", dataInicio)
					.setParameter("dataFim", dataFim).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
}
