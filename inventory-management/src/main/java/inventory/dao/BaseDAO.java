package inventory.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import inventory.model.Paging;

public interface BaseDAO<E> {
	public List<E> findAll(String queryStr, Map<String, Object> mapParams,Paging paging);
	public E findById(Class<E> e, Serializable id);
	public List<E> findByProperty(String property , Object value);
	public void save(E instance);
	public int insert(E instance);
	public void update(E instance);
}
