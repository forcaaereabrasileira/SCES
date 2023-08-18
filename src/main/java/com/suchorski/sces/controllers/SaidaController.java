package com.suchorski.sces.controllers;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.omnifaces.util.Messages;

import com.github.adminfaces.template.exception.AccessDeniedException;
import com.suchorski.sces.ad.LoginUnicoController;
import com.suchorski.sces.ad.LoginUnicoUsuario;
import com.suchorski.sces.daos.ClienteDAO;
import com.suchorski.sces.daos.ControleDAO;
import com.suchorski.sces.generics.AccessNeededController;
import com.suchorski.sces.models.Cliente;
import com.suchorski.sces.models.Controle;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.jbosslog.JBossLog;

@Named("saida")
@ViewScoped
@JBossLog
public class SaidaController implements AccessNeededController, Serializable {
	
	private static final long serialVersionUID = -1500568245602120861L;

	private static final String REPLY_TO = "no-reply.gapls@fab.mil.br";
	private static final String SIGNATURE = "ASTIC-1 - Assessoria de TI";
	
	@Resource(lookup="java:jboss/mail/ati")
	private Session session;

	@Inject private SessaoController sessao;
	@Inject private LoginUnicoController loginUnico;
	
	private ClienteDAO clienteDAO;
	private ControleDAO controleDAO;
	
	@Getter private List<Controle> abertas;
	
	@Getter @Setter private Controle controle;
	@Getter @Setter private String cpf;
	@Getter @Setter private String senha;
	
	@PostConstruct
	public void init() {
		log.debug("Initializing JSF class");
		clienteDAO = new ClienteDAO();
		controleDAO = new ControleDAO();
		abertas = controleDAO.listAbertos();
	}
	
	@PreDestroy
	public void destroy() {
		clienteDAO.close();
		controleDAO.close();
		log.debug("Destroyed JSF class");
	}
	
	public void excluir() {
		controleDAO.delete(controle);
		abertas = controleDAO.listAbertos();
		Messages.create("Sucesso!").detail("Controle excluído.").add();
	}
	
	public void editar() {
		controleDAO.save(controle);
		Messages.create("Sucesso!").detail("Controle editado.").add();
	}
	
	public void devolver() {
		try {
			loginUnico.login(cpf, senha);
			LoginUnicoUsuario luAdministrador = loginUnico.findByCpf(sessao.getUsuario().getCpf());
			LoginUnicoUsuario luUsuario = loginUnico.findByCpf(cpf);
			Cliente administrador = clienteDAO.saveAndGet(luAdministrador);
			Cliente usuario = clienteDAO.saveAndGet(luUsuario);
			controleDAO.devolver(controle, administrador, usuario);
			abertas = controleDAO.listAbertos();
			if (!controle.getUsuarioEntrada().getCpf().equalsIgnoreCase(cpf)) {
				LoginUnicoUsuario luEntregador = loginUnico.findByCpf(controle.getUsuarioEntrada().getCpf());
				sendMail(luEntregador.getZimbra(), "Equipamento retirado!", "Seu equipamento " + controle.getDinf() + " com o SAU " + controle.getSau() + " foi retirado por: " + luUsuario.getPatente() + " " + luUsuario.getNomeCompleto());
			}
			cpf = senha = "";
			Messages.create("Sucesso!").detail("Equipamento devolvido.").add();
		} catch (Exception e) {
			Messages.create("Erro!").error().detail(e.getMessage()).add();
		}
	}
	
	private void sendMail(String email, String subject, String message) throws AddressException, MessagingException, UnsupportedEncodingException {
		Message m = new MimeMessage(session);
		m.setReplyTo(InternetAddress.parse(REPLY_TO));
		m.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
		m.setSubject(subject);
		m.setText(message + "\r\n\r\n" + SIGNATURE);
		Transport.send(m);
	}
	
	@Override
	public void grantAccess() throws AccessDeniedException {
		if (!sessao.getUsuario().hasPerfil("SCM SCA")) {
			throw new AccessDeniedException();
		}
	}
	
}
