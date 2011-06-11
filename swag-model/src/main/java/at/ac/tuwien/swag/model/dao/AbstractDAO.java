package at.ac.tuwien.swag.model.dao;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import at.ac.tuwien.swag.model.domain.AbstractEntity;

import com.google.inject.Inject;

public abstract class AbstractDAO<E extends AbstractEntity> {

	public AbstractDAO() {
		/***/
	}

	public AbstractDAO( EntityManager em ) {
		this.em = em;
	}

	public void delete( final E toDelete ) {
		this.em.remove( this.em.merge( toDelete ) );
	}

	public void insert( final E toPersist ) {
		if (toPersist.getId() == null) {
			this.em.persist( toPersist );
		} else {
			update( toPersist );
		}
	}

	public void update( final E toUpdate ) {
		this.em.persist( this.em.merge( toUpdate ) );
	}

	public void refresh( final E toRefresh ) {
		this.em.refresh( this.em.merge( toRefresh ) );
	}

	public E findById( long id ) {
		return em.find( getEntityClass(), id );
	}

	@SuppressWarnings("unchecked")
	public List<E> findByQuery( String query, Map<String, ?> values ) {
		Query emQuery = em.createQuery( query );

		for (Entry<String, ?> entry : values.entrySet()) {
			emQuery.setParameter( entry.getKey(), entry.getValue() );
		}

		return emQuery.getResultList();
	}

	public abstract Iterable<E> getAll();

	public abstract void deleteAll();

	// ***** TRANSACTIONS

	public EntityTransaction getTransaction() {
		return getEntityManager().getTransaction();
	}

	public void beginTransaction() {
		getTransaction().begin();
	}

	public void commitTransaction() {
		getTransaction().commit();
	}

	public void rollbackTransaction() {
		getTransaction().rollback();
	}

	// ***** PRIVATE PARTS

	protected EntityManager getEntityManager() {
		return em;
	}

	protected abstract Class<E> getEntityClass();

	@Inject
	private EntityManager em;
}
