package biblio.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
	static SessionFactory factory = null;
	static ServiceRegistry serviceRegistry;
	static {
		// A SessionFactory is set up once for an application!
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.configure("/hibernate/hibernate.cfg.xml") // configures settings from hibernate.cfg.xml
				.build();
		factory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
	}

	public static SessionFactory getSessionFactory() {
		return factory;
	}
}
