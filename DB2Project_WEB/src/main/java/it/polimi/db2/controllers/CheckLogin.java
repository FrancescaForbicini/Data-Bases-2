package it.polimi.db2.controllers;

import java.io.IOException;

import javax.ejb.EJB;
import javax.persistence.NonUniqueResultException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.db2.exceptions.*;
import it.polimi.db2.services.*;
import it.polimi.db2.entities.*;

import org.apache.commons.lang.StringEscapeUtils;

@WebServlet("/CheckLogin")
public class CheckLogin extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;
    
    @EJB(name = "it.polimi.db2.services/ConsumerService")
    private ConsumerService cs;
    
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }

    protected void doGet (HttpServletRequest request, HttpServletResponse response)
            throws  IOException {
    	String username, password, email;
    	String path;
    	request.getSession().setAttribute("consumer",null);
        try {
            email = StringEscapeUtils.escapeJava(request.getParameter("email"));
            username = StringEscapeUtils.escapeJava(request.getParameter("username"));
            password = StringEscapeUtils.escapeJava(request.getParameter("password"));
            if (email == null || username == null || password == null || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                throw new Exception("Missing or empty credential value");
            }

        } catch (Exception e) {
        	ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
            ctx.setVariable("errorMsg", e.getMessage());
            path = "/index.html";
            templateEngine.process(path, ctx, response.getWriter());
            return;
        }
        Consumer consumer;
        try {
            consumer = cs.checkCredentials(username, password,email);
        } catch (NonUniqueResultException | CredentialsException e) {
        	ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
            ctx.setVariable("errorMsg", "Could not check credentials");
            path = "/index.html";
            templateEngine.process(path, ctx, response.getWriter());
            return;
        }

        if (consumer == null) {
            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
            ctx.setVariable("errorMsg", "Incorrect credentials (username, password or email)");
            path = "/index.html";
            templateEngine.process(path, ctx, response.getWriter());
            return;
        } else {
        	if (request.getSession().getAttribute("subscription") == null) {
                path = getServletContext().getContextPath() + "/Home";
        	}
        	else {
        		path = getServletContext().getContextPath() + "/Confirmation";
        	}
            request.getSession().setAttribute("consumer", consumer);
            response.sendRedirect(path);
            return;            
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws  IOException {
    	doGet(request,response);

    }

    public void destroy() {}
}