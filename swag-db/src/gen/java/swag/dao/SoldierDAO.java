package swag.dao;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

import swag.domain.*;

public class SoldierDAO extends AbstractDAO<Soldier> {
	public SoldierDAO(EntityManager em) {
		super(em);
	}

	@SuppressWarnings("unchecked")
	public List<Soldier> findAll() {
		return getEntityManager().createQuery("SELECT obj FROM Soldier obj")
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Soldier> findByType(SoldierType type) {
		return getEntityManager()
				.createQuery(
						"SELECT obj FROM Soldier obj WHERE obj.type = :val")
				.setParameter("val", type).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Soldier> findByAttackStrength(double attackStrength) {
		return getEntityManager()
				.createQuery(
						"SELECT obj FROM Soldier obj WHERE obj.attackStrength = :val")
				.setParameter("val", attackStrength).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Soldier> findByAmount(int amount) {
		return getEntityManager()
				.createQuery(
						"SELECT obj FROM Soldier obj WHERE obj.amount = :val")
				.setParameter("val", amount).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Soldier> findBySpeed(double speed) {
		return getEntityManager()
				.createQuery(
						"SELECT obj FROM Soldier obj WHERE obj.speed = :val")
				.setParameter("val", speed).getResultList();
	}

	protected Class<Soldier> getEntityClass() {
		return Soldier.class;
	}
}
