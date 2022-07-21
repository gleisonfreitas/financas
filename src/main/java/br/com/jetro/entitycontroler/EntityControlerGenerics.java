package br.com.jetro.entitycontroler;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import br.com.jetro.negocio.NegocioException;

public class EntityControlerGenerics<T> {
	
	@Inject
	protected EntityManager entityManager;
	
	private Class<T> classe;
	
	@SuppressWarnings("unchecked")
	public EntityControlerGenerics() {
				
		classe = (Class<T>) (((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
	}
	
	public void fecharConexao(){
		entityManager.close();
	}
	
	public void inserir(T tipo) throws NegocioException{
		
		EntityTransaction trx = entityManager.getTransaction();
		try {
			trx.begin();
			entityManager.persist(tipo);
			trx.commit();
			
		} catch (Exception e) {
			trx.rollback();
			throw new NegocioException("Erro ao tentar salvar "+classe.getSimpleName(), e);
		} finally{
			entityManager.close();
		}
	}
	
	public List<T> listar(){
		
		TypedQuery<T> typedQuery = entityManager.createQuery("from "+classe.getSimpleName(), classe);
		return typedQuery.getResultList();
	}
	
	public T bucarPorId(Long id){
		
		return entityManager.find(classe, id);
	}
	
	public void remover(T tipo){
		entityManager.remove(tipo);
	}
	
	public T guardar(T tipo){
		tipo = entityManager.merge(tipo);
		entityManager.flush();
		return tipo;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	
}
