package com.suchorski.sces.controllers;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Messages;

import com.github.adminfaces.template.exception.AccessDeniedException;
import com.suchorski.sces.ad.LoginUnicoController;
import com.suchorski.sces.ad.LoginUnicoUsuario;
import com.suchorski.sces.daos.ClienteDAO;
import com.suchorski.sces.daos.ControleDAO;
import com.suchorski.sces.daos.TipoEquipamentoDAO;
import com.suchorski.sces.generics.AccessNeededController;
import com.suchorski.sces.models.Cliente;
import com.suchorski.sces.models.Controle;
import com.suchorski.sces.models.TipoEquipamento;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.jbosslog.JBossLog;

@Named("entrada")
@RequestScoped
@JBossLog
public class EntradaController implements AccessNeededController {
	
	@Inject private SessaoController sessao;
	@Inject private LoginUnicoController loginUnico;
	
	private TipoEquipamentoDAO tipoEquipamentoDAO;
	private ClienteDAO clienteDAO;
	private ControleDAO controleDAO;
	
	@Getter private List<TipoEquipamento> tipoEquipamentos;
	
	@Getter @Setter private String username;
	@Getter @Setter private String password;
	@Getter @Setter private String sau;
	@Getter @Setter private String dinf;
	@Getter @Setter private String contato;
	@Getter @Setter private TipoEquipamento tipoEquipamento;
	@Getter @Setter private String descricao;
	
	@PostConstruct
	public void init() {
		log.debug("Initializing JSF class");
		tipoEquipamentoDAO = new TipoEquipamentoDAO();
		tipoEquipamentos = tipoEquipamentoDAO.list();
		clienteDAO = new ClienteDAO();
		controleDAO = new ControleDAO();
	}
	
	@PreDestroy
	public void destroy() {
		tipoEquipamentoDAO.close();
		clienteDAO.close();
		controleDAO.close();
		log.debug("Destroyed JSF class");
	}
	
	public void registrarEntrada() {
		try {
			loginUnico.login(username, password);
			LoginUnicoUsuario luAdministrador = loginUnico.findByCpf(sessao.getUsuario().getCpf());
			LoginUnicoUsuario luUsuario = loginUnico.findByCpf(username);
			Cliente administrador = clienteDAO.saveAndGet(luAdministrador);
			Cliente usuario = clienteDAO.saveAndGet(luUsuario);
			controleDAO.save(new Controle(sau, dinf, contato, tipoEquipamento, descricao, administrador, usuario));
			Messages.create("Sucesso!").detail("Entrada registrada.").add();
		} catch (Exception e) {
			Messages.create("Erro!").warn().detail(e.getLocalizedMessage()).add();
		}
	}
	
	@Override
	public void grantAccess() throws AccessDeniedException {
		if (!sessao.getUsuario().hasPerfil("SCM SCA")) {
			throw new AccessDeniedException();
		}
	}
	
}
