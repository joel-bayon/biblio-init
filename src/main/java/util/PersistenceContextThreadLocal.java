package util;

import javax.persistence.EntityManager;

public class PersistenceContextThreadLocal {
	public enum TransactionState {BEGIN, COMMIT, ROLLBACK};
	
static private ThreadLocal<EntityManager> entityManagerThreadLocal = new ThreadLocal<EntityManager>();

	static public EntityManager getEntityManager() {
		EntityManager em = entityManagerThreadLocal.get();
		if(em == null) {
				em = JpaUtil.getEntityManagerFactory().createEntityManager();
				entityManagerThreadLocal.set(em);
		} 
		return em;
	}
	
	static public void closeEntityManager() {
		if(entityManagerThreadLocal.get() != null) {
			entityManagerThreadLocal.get().close();
			entityManagerThreadLocal.set(null);
		}
	}
	
	static public void setTransaction(TransactionState state) {
		EntityManager em = getEntityManager();
		switch (state) {
			case BEGIN: em.getTransaction().begin();break;
			case COMMIT: em.getTransaction().commit();  break;
			case ROLLBACK: em.getTransaction().rollback(); break;
		}
				
	}
	
}
