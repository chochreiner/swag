package swag.dao;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

import swag.domain.*;

public class MapDAO extends AbstractDAO<Map> {
	public MapDAO(EntityManager em) {
		super(em);
	}

	@SuppressWarnings("unchecked")
	public List<Map> findAll() {
		return getEntityManager().createQuery("SELECT obj FROM Map obj")
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Map> findByMaxNumUsers(int maxNumUsers) {
		return getEntityManager()
				.createQuery(
						"SELECT obj FROM Map obj WHERE obj.maxNumUsers = :val")
				.setParameter("val", maxNumUsers).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Map> findByXSize(int xSize) {
		return getEntityManager()
				.createQuery("SELECT obj FROM Map obj WHERE obj.xSize = :val")
				.setParameter("val", xSize).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Map> findByYSize(int ySize) {
		return getEntityManager()
				.createQuery("SELECT obj FROM Map obj WHERE obj.ySize = :val")
				.setParameter("val", ySize).getResultList();
	}

	@SuppressWarnings("unchecked")
	public Map findByName(String name) {
		return (Map) getEntityManager()
				.createQuery("SELECT obj FROM Map obj WHERE obj.name = :val")
				.setParameter("val", name).getSingleResult();
	}

	protected Class<Map> getEntityClass() {
		return Map.class;
	}
}
