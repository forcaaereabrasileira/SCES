package com.suchorski.sces.daos;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import com.suchorski.sces.ad.LoginUnicoUsuario;
import com.suchorski.sces.generics.GenericDAO;
import com.suchorski.sces.models.Cliente;
import com.suchorski.sces.models.Cliente_;

public class ClienteDAO extends GenericDAO<Cliente, Integer> {
	
	public ClienteDAO() {
		super(Cliente.class);
	}
	
	public Cliente saveAndGet(LoginUnicoUsuario loginUnicoUsuario) {
		try {
			Cliente cliente = findByCpf(loginUnicoUsuario.getCpf());
			if (!cliente.getNomeExibicao().equals(loginUnicoUsuario.getDisplayName())) {
				cliente.setNomeExibicao(loginUnicoUsuario.getDisplayName());
				save(cliente);
			}
			return cliente;
		} catch (NoResultException e) {
			save(new Cliente(loginUnicoUsuario.getCpf(), loginUnicoUsuario.getDisplayName()));
			return findByCpf(loginUnicoUsuario.getCpf());
		}
	}
	
	public Cliente findByCpf(String cpf) throws NoResultException {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class);
		Root<Cliente> root = criteriaQuery.from(Cliente.class);
		ParameterExpression<String> parameterExpression = criteriaBuilder.parameter(String.class);
		criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(Cliente_.cpf), parameterExpression));
		TypedQuery<Cliente> typedQuery = getEntityManager().createQuery(criteriaQuery);
		typedQuery.setParameter(parameterExpression, cpf);
		return typedQuery.getSingleResult();
	}
	
}
