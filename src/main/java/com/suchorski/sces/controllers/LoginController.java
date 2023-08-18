package com.suchorski.sces.controllers;

import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Messages;

@Named("login")
@RequestScoped
public class LoginController {
	
	@Inject private SessaoController sessao;
	
	public void logonRedirect() throws IOException {
		sessao.redirect();
	}
	
	public void logoff() throws IOException {
		Messages.create("Sucesso!").flash().warn().detail("Usuário deslogado.").add();
		sessao.logoff();
	}

}
