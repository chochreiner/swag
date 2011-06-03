package swag.dao;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

import swag.domain.*;

public class MessageDAO extends AbstractDAO<Message> {
	public MessageDAO(EntityManager em) {
		super(em);
	}

	@SuppressWarnings("unchecked")
	public List<Message> findAll() {
		return getEntityManager().createQuery("SELECT obj FROM Message obj")
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Message> findByTimestamp(Date timestamp) {
		return getEntityManager()
				.createQuery(
						"SELECT obj FROM Message obj WHERE obj.timestamp = :val")
				.setParameter("val", timestamp).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Message> findBySubject(String subject) {
		return getEntityManager()
				.createQuery(
						"SELECT obj FROM Message obj WHERE obj.subject = :val")
				.setParameter("val", subject).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Message> findByText(String text) {
		return getEntityManager()
				.createQuery(
						"SELECT obj FROM Message obj WHERE obj.text = :val")
				.setParameter("val", text).getResultList();
	}

	protected Class<Message> getEntityClass() {
		return Message.class;
	}
}
