package swag;

import javax.persistence.*;

public abstract class Helper {
	public static void dropDatabase(EntityManager em) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		em.createNativeQuery("DROP TABLE message CASCADE").executeUpdate();

		em.createNativeQuery("DROP TABLE users CASCADE").executeUpdate();

		em.createNativeQuery("DROP TABLE map CASCADE").executeUpdate();

		em.createNativeQuery("DROP TABLE mapUser CASCADE").executeUpdate();

		em.createNativeQuery("DROP TABLE storedRessource CASCADE")
				.executeUpdate();

		em.createNativeQuery("DROP TABLE square CASCADE").executeUpdate();

		em.createNativeQuery("DROP TABLE ressourceBuilding CASCADE")
				.executeUpdate();

		em.createNativeQuery("DROP TABLE baseBuilding CASCADE").executeUpdate();

		em.createNativeQuery("DROP TABLE boost CASCADE").executeUpdate();

		em.createNativeQuery("DROP TABLE troop CASCADE").executeUpdate();

		em.createNativeQuery("DROP TABLE soldier CASCADE").executeUpdate();

		tx.commit();
	}
}
