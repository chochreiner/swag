package swag.dao;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

import swag.domain.*;

public class RessourceBuildingDAO extends AbstractDAO<RessourceBuilding> {
	public RessourceBuildingDAO(EntityManager em) {
		super(em);
	}

	@SuppressWarnings("unchecked")
	public List<RessourceBuilding> findAll() {
		return getEntityManager().createQuery(
				"SELECT obj FROM RessourceBuilding obj").getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<RessourceBuilding> findByLevel(int level) {
		return getEntityManager()
				.createQuery(
						"SELECT obj FROM RessourceBuilding obj WHERE obj.level = :val")
				.setParameter("val", level).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<RessourceBuilding> findByType(RessourceBuildingType type) {
		return getEntityManager()
				.createQuery(
						"SELECT obj FROM RessourceBuilding obj WHERE obj.type = :val")
				.setParameter("val", type).getResultList();
	}

	protected Class<RessourceBuilding> getEntityClass() {
		return RessourceBuilding.class;
	}
}
