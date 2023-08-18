package com.suchorski.sces.ad;

import java.util.Hashtable;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

@Named("loginUnico")
@RequestScoped
public class LoginUnicoController {
	
	@Resource(lookup="java:global/ldap/fab")
	private InitialDirContext context;
	
	@PreDestroy
	public void destroy() {
		try {
			if (context != null) {
				context.close();
				context = null;
			}
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		destroy();
		super.finalize();
	}
	
	@SuppressWarnings("unchecked")
	public void login(String cpf, String senha) throws Exception {
		try {
			Hashtable<String, String> env = (Hashtable<String, String>) context.getEnvironment().clone();
			env.put(Context.SECURITY_PRINCIPAL, String.format("uid=%s,ou=contas,dc=fab,dc=intraer", cpf));
			env.put(Context.SECURITY_CREDENTIALS, senha);
			(new InitialDirContext(env)).close();
		} catch (AuthenticationException e) {
			throw new Exception("Usuário ou senha incorretos.");
		}
	}
	
	public LoginUnicoUsuario findByCpf(String cpf) throws Exception {
		SearchControls searchControls = new SearchControls();
		searchControls.setReturningAttributes(LoginUnicoUsuario.getReturningAttributes());
		searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		NamingEnumeration<SearchResult> naming = context.search("ou=contas", String.format("(&(uid=%s))", cpf), searchControls);
		if (naming.hasMoreElements()) {
			SearchResult result = (SearchResult) naming.next();
			Attributes attrs = result.getAttributes();
			return new LoginUnicoUsuario(attrs);
		}
		throw new Exception("Usuário não encontrado.");
	}
	
}