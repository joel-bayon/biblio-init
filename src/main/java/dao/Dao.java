package dao;

import java.util.Collection;
import java.util.List;

public interface Dao<K, E> {
	public void save(E entity);
	public void update(E entity);
	public E load(K primaryKey);
	public List<E> loadAll();
	public void remove(E entity);
	public void removeById(K id);
	public void removeAll(Collection<E> entities) ;		
}

