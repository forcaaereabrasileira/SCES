package com.suchorski.sces.generics;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.suchorski.sces.utils.HibernateService;


public abstract class GenericDAO<T, I extends Serializable> implements AutoCloseable {
	
	private Class<T> persistedClass;
	private EntityManager entityManager;
	
	private GenericDAO() {
		entityManager = HibernateService.createEntityManager();
	}
	
	public GenericDAO(Class<T> persistedClass) {
		this();
		this.persistedClass = persistedClass;
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@Override
	public void close() {
		if (entityManager != null && entityManager.isOpen()) {			
			entityManager.close();
		}
	}
	
	public void save(T entity) {
		entityManager.getTransaction().begin();
		entityManager.persist(entity);
		entityManager.getTransaction().commit();
	}
	
	public T getById(I id) {
		return entityManager.find(persistedClass, id);
	}
	
	public List<T> list() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(persistedClass);
		Root<T> root = criteriaQuery.from(persistedClass);
		criteriaQuery.select(root);
		TypedQuery<T> typedQuery = entityManager.createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}
	
	public T update(T entity) {
		entityManager.getTransaction().begin();
		T t = entityManager.merge(entity);
		entityManager.getTransaction().commit();
		return t;
	}
	
	public void delete(T entity) {
		entityManager.getTransaction().begin();
		entityManager.remove(entityManager.merge(entity));
		entityManager.getTransaction().commit();
	}

	public void deleteById(I id) {
		entityManager.getTransaction().begin();
		entityManager.remove(getById(id));
		entityManager.getTransaction().commit();
	}

}
