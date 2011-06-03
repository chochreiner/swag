package swag.dao;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

import swag.domain.*;

public class TroopDAO extends AbstractDAO<Troop> {
	public TroopDAO(EntityManager em) {
		super(em);
	}

	@SuppressWarnings("unchecked")
	public List<Troop> findAll() {
		return getEntityManager().createQuery("SELECT obj FROM Troop obj")
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Troop> findByNumber(int number) {
		return getEntityManager()
				.createQuery(
						"SELECT obj FROM Troop obj WHERE obj.number = :val")
				.setParameter("val", number).getResultList();
	}

	protected Class<Troop> getEntityClass() {
		return Troop.class;
	}
}
