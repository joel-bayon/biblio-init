package util;

import org.hibernate.Session;

public class SessionThreadLocal {
	public enum TransactionState {BEGIN, COMMIT, ROLLBACK};
	
static private ThreadLocal<Session> sessionThreadLocal = new ThreadLocal<Session>();

	static public Session getSession() {
		Session session = sessionThreadLocal.get();
		if(session == null) {
			session = HibernateUtil.getSessionFactory().openSession();
				sessionThreadLocal.set(session);
		} 
		return session;
	}
	
	static public void closeSession() {
		if(sessionThreadLocal.get() != null) {
			sessionThreadLocal.get().close();
			sessionThreadLocal.set(null);
		}
	}
	
	static public void setTransaction(TransactionState state) {
		Session session = getSession();
		switch (state) {
			case BEGIN: session.getTransaction().begin();break;
			case COMMIT: session.getTransaction().commit();  break;
			case ROLLBACK: session.getTransaction().rollback(); break;
		}
				
	}
	
}
