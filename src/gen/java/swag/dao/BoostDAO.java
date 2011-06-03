package swag.dao;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

import swag.domain.*;

public class BoostDAO extends AbstractDAO<Boost> {
	public BoostDAO(EntityManager em) {
		super(em);
	}

	@SuppressWarnings("unchecked")
	public List<Boost> findAll() {
		return getEntityManager().createQuery("SELECT obj FROM Boost obj")
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Boost> findByType(RessourceType type) {
		return getEntityManager()
				.createQuery("SELECT obj FROM Boost obj WHERE obj.type = :val")
				.setParameter("val", type).getResultList();
	}

	protected Class<Boost> getEntityClass() {
		return Boost.class;
	}
}
