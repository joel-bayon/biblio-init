package biblio.dao.jpa;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;

import biblio.util.PersistenceContextThreadLocal;
import biblio.dao.Dao;

@SuppressWarnings("unchecked")
public abstract class DaoJpa <K, E> implements Dao<K, E> {
		protected Class<E> entityClass;
	 
		static EntityManager entityManager = PersistenceContextThreadLocal.getEntityManager();
	 
		public DaoJpa() {
			
			ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
			this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[1];
		}
		
		@Override
		public void save(E entity) { 
			EntityManager entityManager = PersistenceContextThreadLocal.getEntityManager();
			entityManager.persist(entity); }
		@Override
		public void remove(E entity) { 
			EntityManager entityManager = PersistenceContextThreadLocal.getEntityManager();
			entityManager.remove(entityManager.merge(entity)); }
		
		@Override
		public void removeById(K id) {
			EntityManager entityManager = PersistenceContextThreadLocal.getEntityManager();
			entityManager.remove(entityManager.getReference(entityClass, (Serializable)id));
		}
		
		@Override
		public E load(K id) { 
			EntityManager entityManager = PersistenceContextThreadLocal.getEntityManager();
			return entityManager.find(entityClass, id); }
		@Override
		public void update(E entity) { 
			EntityManager entityManager = PersistenceContextThreadLocal.getEntityManager();
			entityManager.merge(entity); }
		
		@Override
		public List<E> loadAll() {
			EntityManager entityManager = PersistenceContextThreadLocal.getEntityManager();
			return entityManager.createQuery("from " + entityClass.getName())
					   .getResultList();
		}

		@Override
		public void removeAll(Collection<E> entities) {
			EntityManager entityManager = PersistenceContextThreadLocal.getEntityManager();
			for(E entity : entities) {
				entityManager.remove(entityManager.merge(entity));
			}
		}
}
