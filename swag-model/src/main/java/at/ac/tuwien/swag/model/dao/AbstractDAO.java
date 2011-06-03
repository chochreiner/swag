package at.ac.tuwien.swag.model.dao;

import javax.persistence.EntityManager;

import at.ac.tuwien.swag.model.domain.AbstractEntity;

public abstract class AbstractDAO<E extends AbstractEntity> {
	public AbstractDAO( EntityManager em ) {
		this.em = em;
	}
	
	public void insert( E e ) {
		em.persist( e );
	}
	public E findById( long id ) {
		return em.find( getEntityClass(), id );
	}
	public E update( E e ) {
		return em.merge( e );
	}
	public void delete( E e ) {
		em.remove( e );
	}
	
	protected EntityManager getEntityManager() {
		return em;
	}
	
	protected abstract Class<E> getEntityClass();
	
	private EntityManager em;
}
