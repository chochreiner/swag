package swag.dao;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

import swag.domain.*;

public class BaseBuildingDAO extends AbstractDAO<BaseBuilding> {
	public BaseBuildingDAO(EntityManager em) {
		super(em);
	}

	@SuppressWarnings("unchecked")
	public List<BaseBuilding> findAll() {
		return getEntityManager().createQuery(
				"SELECT obj FROM BaseBuilding obj").getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<BaseBuilding> findByLevel(int level) {
		return getEntityManager()
				.createQuery(
						"SELECT obj FROM BaseBuilding obj WHERE obj.level = :val")
				.setParameter("val", level).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<BaseBuilding> findByType(BaseBuildingType type) {
		return getEntityManager()
				.createQuery(
						"SELECT obj FROM BaseBuilding obj WHERE obj.type = :val")
				.setParameter("val", type).getResultList();
	}

	protected Class<BaseBuilding> getEntityClass() {
		return BaseBuilding.class;
	}
}
