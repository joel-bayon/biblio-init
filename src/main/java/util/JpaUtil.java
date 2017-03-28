package util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaUtil {
	static EntityManagerFactory factory ;
	
	static {
		factory = Persistence.createEntityManagerFactory("biblio");
	}

	public static EntityManagerFactory getEntityManagerFactory() {
		return factory;
	}
}
