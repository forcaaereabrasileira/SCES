package com.suchorski.sces.oauth.servlets;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.suchorski.sces.controllers.SessaoController;

@WebServlet("/callback")
public class CallbackServlet extends HttpServlet {
	
	private static final long serialVersionUID = -4498524540309201192L;

	@Inject private SessaoController sessao;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String code = request.getParameter("code");
		if (code != null && !code.isEmpty()) {
			sessao.setToken(code);
			response.sendRedirect("login.xhtml");
		}
	}

}
