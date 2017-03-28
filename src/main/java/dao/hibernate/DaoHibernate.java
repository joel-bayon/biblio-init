package dao.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import org.hibernate.Session;

import util.SessionThreadLocal;
import dao.Dao;

@SuppressWarnings("unchecked")
public abstract class DaoHibernate <K, E> implements Dao<K, E> {
		protected Class<E> entityClass;
	 
		protected Session session = SessionThreadLocal.getSession();
	 
		public DaoHibernate() {
			ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
			this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[1];
		}
		
		@Override
		public void save(E entity) { session.save(entity); }
		
		@Override
		public void remove(E entity) { session.delete(entity); }
		
		@Override
		public void removeById(K id) {
			session.delete(session.load(entityClass, (Serializable)id));
		}
		
		@Override
		public E load(K id) { 
				return  (E) session.get(entityClass, (Serializable)id); 
		}
	
		
		@Override
		public void update(E entity) { session.merge(entity); }
		
		@Override
		public List<E> loadAll() {
			return (List<E>) session.createQuery("from " + entityClass.getName())
					   .getResultList(); 
		}

		@Override
		public void removeAll(Collection<E> entities) {
			for(E entity : entities) {
				session.delete(entity);
			}
		}

		
}
