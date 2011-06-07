package at.ac.tuwien.swag.model.dao;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import at.ac.tuwien.swag.model.domain.AbstractEntity;

import com.google.inject.persist.Transactional;

public abstract class AbstractDAO<E extends AbstractEntity> {
    public AbstractDAO(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public void insert(E e) {
        em.persist(e);
    }

    @Transactional
    public E findById(long id) {
        return em.find(getEntityClass(), id);
    }

    @Transactional
    public E update(E e) {
        return em.merge(e);
    }

    @Transactional
    public void delete(E e) {
        em.remove(e);
    }

    @Transactional
    public List<E> findByQuery(String query, Map<String, String> values) {
        Query emQuery = em.createQuery(query);

        for (Entry<String, String> entry : values.entrySet()) {
            emQuery.setParameter(entry.getKey(), entry.getValue());
        }

        return emQuery.getResultList();
    }

    protected EntityManager getEntityManager() {
        return em;
    }

    protected abstract Class<E> getEntityClass();

    private EntityManager em;
}
