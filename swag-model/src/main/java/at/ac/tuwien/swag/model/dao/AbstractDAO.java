package at.ac.tuwien.swag.model.dao;

import javax.persistence.EntityManager;

import com.google.inject.persist.Transactional;

import at.ac.tuwien.swag.model.domain.AbstractEntity;

public abstract class AbstractDAO<E extends AbstractEntity> {
	
	public AbstractDAO( EntityManager em ) {
		this.em = em;
	}
	
	@Transactional
	public void insert( E e ) {
		em.persist( e );
	}
	
	@Transactional
	public abstract Iterable<E> getAll();
	
	@Transactional
	public E findById( long id ) {
		return em.find( getEntityClass(), id );
	}
	
	@Transactional
	public E update( E e ) {
		return em.merge( e );
	}
	
	@Transactional
	public void delete( E e ) {
		em.remove( e );
	}
	
	@Transactional
	public abstract void deleteAll();
	
	protected EntityManager getEntityManager() {
		return em;
	}
	
	protected abstract Class<E> getEntityClass();
	
	private EntityManager em;
}
