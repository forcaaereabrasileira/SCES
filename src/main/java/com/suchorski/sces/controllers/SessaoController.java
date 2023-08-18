package com.suchorski.sces.controllers;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Specializes;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;

import com.github.adminfaces.template.exception.AccessDeniedException;
import com.github.adminfaces.template.session.AdminSession;
import com.suchorski.sces.oauth.ResourceUserData;
import com.suchorski.sces.oauth.ScatiAPI;

import lombok.Getter;
import lombok.extern.jbosslog.JBossLog;

@Named("sessao")
@SessionScoped
@Specializes
@JBossLog
public class SessaoController extends AdminSession implements Serializable {
	
	private static final long serialVersionUID = -3757770876122283151L;

	@Inject private AplicacaoController app;
	
	@Getter private ResourceUserData usuario;
	@Getter private ScatiAPI api;
	
	@PostConstruct
	public void init() {
		log.info("Initializing session...");
		api = new ScatiAPI().setPublicKey(ScatiAPI.CLIENT_PUBLIC).setPrivateKey(ScatiAPI.CLIENT_SECRET).setRedirectUri(String.format("http://%s/%s/callback", ScatiAPI.URI_CLIENT, app.getContext())).setScope(ScatiAPI.SCOPE);
		usuario = api.getResourceUserData();
	}
	
	@PreDestroy
	public void destroy() {
		log.info("Finalized session");
	}
	
	public String getDisplayName() {
		return String.format("%s %s [%s]", usuario.getPatente(), usuario.getNomeGuerra(), usuario.getOm());
	}
	
	public void setToken(String token) {
		api.setToken(token);
		atualizaUsuario();
	}
	
	public void atualizaUsuario() {
		usuario = api.getResourceUserData();
	}
	
	public void redirect() throws IOException {
		if (api.getToken() == null || api.getToken().isEmpty()) {
			Faces.redirect(api.generateLogonUri());
		} else {
			atualizaUsuario();
			if (usuario == null || !usuario.isValid()) {
				api.setToken(null);
				Faces.invalidateSession();
				Faces.redirect(api.generateLogonUri());
			} else {
				Faces.redirect("finalizados.xhtml?faces-redirect=true");
			}
		}
	}
	
	public void logoff() throws IOException {
		String redirect = api.logoffUri();
		api.setToken(null);
		Faces.invalidateSession();
		Faces.redirect(redirect);
	}
	
	public void hasAccess() throws AccessDeniedException {
		if (isLoggedIn() && !usuario.hasPerfil(ScatiAPI.SCOPE)) {
			throw new AccessDeniedException();
		}
	}
	
	@Override
	public boolean isLoggedIn() {
		atualizaUsuario();
		return usuario != null && usuario.isValid();
	}

}
