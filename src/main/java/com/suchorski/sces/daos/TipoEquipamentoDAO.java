package com.suchorski.sces.daos;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.suchorski.sces.generics.GenericDAO;
import com.suchorski.sces.models.TipoEquipamento;
import com.suchorski.sces.models.TipoEquipamento_;

public class TipoEquipamentoDAO extends GenericDAO<TipoEquipamento, Integer> {
	
	public TipoEquipamentoDAO() {
		super(TipoEquipamento.class);
	}
	
	public List<TipoEquipamento> list() {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<TipoEquipamento> criteriaQuery = criteriaBuilder.createQuery(TipoEquipamento.class);
		Root<TipoEquipamento> root = criteriaQuery.from(TipoEquipamento.class);
		criteriaQuery.select(root).orderBy(criteriaBuilder.asc(root.get(TipoEquipamento_.descricao)));
		TypedQuery<TipoEquipamento> typedQuery = getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

}
