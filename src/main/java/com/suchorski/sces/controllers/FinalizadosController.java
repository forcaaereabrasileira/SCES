package com.suchorski.sces.controllers;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.adminfaces.template.exception.AccessDeniedException;
import com.suchorski.sces.daos.ControleDAO;
import com.suchorski.sces.generics.AccessNeededController;
import com.suchorski.sces.models.Controle;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.jbosslog.JBossLog;

@Named("finalizados")
@ViewScoped
@JBossLog
public class FinalizadosController implements AccessNeededController, Serializable {
	
	private static final long serialVersionUID = -3624329130645708109L;

	@Inject private SessaoController sessao;
	
	private ControleDAO controleDAO;
	
	@Getter @Setter private Controle controle;
	@Getter private List<Controle> fechadas;
	
	@PostConstruct
	public void init() {
		log.debug("Initializing JSF class");
		controleDAO = new ControleDAO();
		fechadas = controleDAO.listFechados();
	}
	
	@PreDestroy
	public void destroy() {
		controleDAO.close();
		log.debug("Destroyed JSF class");
	}
	
	@Override
	public void grantAccess() throws AccessDeniedException {
		if (!sessao.getUsuario().hasPerfil("SCV SCM SCA")) {
			throw new AccessDeniedException();
		}
	}
	
}
