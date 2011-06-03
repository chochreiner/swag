package swag.dao;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

import swag.domain.*;

public class UserDAO extends AbstractDAO<User> {
	public UserDAO(EntityManager em) {
		super(em);
	}

	@SuppressWarnings("unchecked")
	public List<User> findAll() {
		return getEntityManager().createQuery("SELECT obj FROM User obj")
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<User> findByName(String name) {
		return getEntityManager()
				.createQuery("SELECT obj FROM User obj WHERE obj.name = :val")
				.setParameter("val", name).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<User> findByPassword(String password) {
		return getEntityManager()
				.createQuery(
						"SELECT obj FROM User obj WHERE obj.password = :val")
				.setParameter("val", password).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<User> findByAddress(String address) {
		return getEntityManager()
				.createQuery(
						"SELECT obj FROM User obj WHERE obj.address = :val")
				.setParameter("val", address).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<User> findByEmail(String email) {
		return getEntityManager()
				.createQuery("SELECT obj FROM User obj WHERE obj.email = :val")
				.setParameter("val", email).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<User> findByFullname(String fullname) {
		return getEntityManager()
				.createQuery(
						"SELECT obj FROM User obj WHERE obj.fullname = :val")
				.setParameter("val", fullname).getResultList();
	}

	protected Class<User> getEntityClass() {
		return User.class;
	}
}
