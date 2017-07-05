package dao.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import util.SessionThreadLocal;
import dao.Dao;

@SuppressWarnings("unchecked")
@Transactional(propagation=Propagation.REQUIRED)
public abstract class DaoHibernateTemplate <K, E> implements Dao<K, E> {
		protected Class<E> entityClass;
		@Autowired
		HibernateTemplate template;
	 
		public DaoHibernateTemplate() {
			ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
			this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[1];
		}
		
		@Override
		public void save(E entity) { template.save(entity); }
		
		@Override
		public void remove(E entity) { template.delete(entity); }
		
		@Override
		public void removeById(K id) {
			template.delete(template.load(entityClass, (Serializable)id));
		}
		
		@Override
		public E load(K id) { 
				return  (E) template.get(entityClass, (Serializable)id); 
		}
	
		
		@Override
		public void update(E entity) { template.merge(entity); }
		
		@Override
		public List<E> loadAll() {
			return  (List<E>) template.find("from " + entityClass.getName());
					   
		}

		@Override
		public void removeAll(Collection<E> entities) {
			
			template.deleteAll(entities);
			
		}

		
}
