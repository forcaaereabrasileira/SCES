package com.suchorski.sces.controllers;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import com.suchorski.sces.utils.HibernateService;

import lombok.extern.jbosslog.JBossLog;


@Named("app")
@ApplicationScoped
@JBossLog
public class AplicacaoController {
	
	public static final String CONTEXT = "sces";
	public static final String NAME = "SCES";
	public static final String TITLE = "Sistema de Controle de Entrada e Saída";
	public static final String VERSION = "BETA";
	
	@PostConstruct
	public void init() {
		log.debug("Initializing Application class...");
		HibernateService.startup();
	}
	
	@PreDestroy
	public void destroy() {
		HibernateService.shutdown();
		log.debug("Destroying Application class...");
	}
	
	public String getContext() {
		return CONTEXT;
	}
	
	public String getName() {
		return NAME;
	}
	
	public String getTitle() {
		return TITLE;
	}
	
	public String getVersion() {
		return VERSION;
	}
	
}
