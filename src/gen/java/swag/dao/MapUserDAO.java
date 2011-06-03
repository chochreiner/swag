package swag.dao;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

import swag.domain.*;

public class MapUserDAO extends AbstractDAO<MapUser> {
	public MapUserDAO(EntityManager em) {
		super(em);
	}

	@SuppressWarnings("unchecked")
	public List<MapUser> findAll() {
		return getEntityManager().createQuery("SELECT obj FROM MapUser obj")
				.getResultList();
	}

	protected Class<MapUser> getEntityClass() {
		return MapUser.class;
	}
}
