package swag.dao;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

import swag.domain.*;

public class StoredRessourceDAO extends AbstractDAO<StoredRessource> {
	public StoredRessourceDAO(EntityManager em) {
		super(em);
	}

	@SuppressWarnings("unchecked")
	public List<StoredRessource> findAll() {
		return getEntityManager().createQuery(
				"SELECT obj FROM StoredRessource obj").getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<StoredRessource> findByAmount(int amount) {
		return getEntityManager()
				.createQuery(
						"SELECT obj FROM StoredRessource obj WHERE obj.amount = :val")
				.setParameter("val", amount).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<StoredRessource> findByRessourceType(
			SharedRessourceType ressourceType) {
		return getEntityManager()
				.createQuery(
						"SELECT obj FROM StoredRessource obj WHERE obj.ressourceType = :val")
				.setParameter("val", ressourceType).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<StoredRessource> findByLastUpdate(Date lastUpdate) {
		return getEntityManager()
				.createQuery(
						"SELECT obj FROM StoredRessource obj WHERE obj.lastUpdate = :val")
				.setParameter("val", lastUpdate).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<StoredRessource> findByCurrentRate(double currentRate) {
		return getEntityManager()
				.createQuery(
						"SELECT obj FROM StoredRessource obj WHERE obj.currentRate = :val")
				.setParameter("val", currentRate).getResultList();
	}

	protected Class<StoredRessource> getEntityClass() {
		return StoredRessource.class;
	}
}
