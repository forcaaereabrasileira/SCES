package com.suchorski.sces.daos;

import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.suchorski.sces.generics.GenericDAO;
import com.suchorski.sces.models.Cliente;
import com.suchorski.sces.models.Controle;
import com.suchorski.sces.models.Controle_;

public class ControleDAO extends GenericDAO<Controle, Long> {
	
	public ControleDAO() {
		super(Controle.class);
	}
	
	public List<Controle> listAbertos() {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Controle> criteriaQuery = criteriaBuilder.createQuery(Controle.class);
		Root<Controle> root = criteriaQuery.from(Controle.class);
		criteriaQuery.select(root).where(criteriaBuilder.isNull(root.get(Controle_.dataSaida))).orderBy(criteriaBuilder.desc(root.get(Controle_.dataEntrada)));
		TypedQuery<Controle> typedQuery = getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}
	
	public List<Controle> listFechados() {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Controle> criteriaQuery = criteriaBuilder.createQuery(Controle.class);
		Root<Controle> root = criteriaQuery.from(Controle.class);
		criteriaQuery.select(root).where(criteriaBuilder.isNotNull(root.get(Controle_.dataSaida))).orderBy(criteriaBuilder.desc(root.get(Controle_.dataSaida)));
		TypedQuery<Controle> typedQuery = getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

	public void devolver(Controle controle, Cliente administrador, Cliente usuario) {
		controle.setAdministradorSaida(administrador);
		controle.setUsuarioSaida(usuario);
		controle.setDataSaida(new Date());
		save(controle);
	}

}
