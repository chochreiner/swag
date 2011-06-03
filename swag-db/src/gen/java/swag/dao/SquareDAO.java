package swag.dao;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

import swag.domain.*;

public class SquareDAO extends AbstractDAO<Square> {
	public SquareDAO(EntityManager em) {
		super(em);
	}

	@SuppressWarnings("unchecked")
	public List<Square> findAll() {
		return getEntityManager().createQuery("SELECT obj FROM Square obj")
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Square> findByCoordX(int coordX) {
		return getEntityManager()
				.createQuery(
						"SELECT obj FROM Square obj WHERE obj.coordX = :val")
				.setParameter("val", coordX).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Square> findByCoordY(int coordY) {
		return getEntityManager()
				.createQuery(
						"SELECT obj FROM Square obj WHERE obj.coordY = :val")
				.setParameter("val", coordY).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Square> findByIsHomeBase(boolean isHomeBase) {
		return getEntityManager()
				.createQuery(
						"SELECT obj FROM Square obj WHERE obj.isHomeBase = :val")
				.setParameter("val", isHomeBase).getResultList();
	}

	protected Class<Square> getEntityClass() {
		return Square.class;
	}
}
