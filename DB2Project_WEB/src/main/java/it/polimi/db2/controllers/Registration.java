package it.polimi.db2.controllers;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.persistence.PersistenceException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.db2.exceptions.*;
import it.polimi.db2.services.ConsumerService;

/**
 * Servlet implementation class Registration
 */
@WebServlet("/Registration")
public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;
    
    @EJB(name = "it.polimi.db2.services/ConsumerService")
    private ConsumerService cs;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Registration() {
        super();
    }

    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username, password, email;
		String message = null;
		ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        
        try {
            email = StringEscapeUtils.escapeJava(request.getParameter("email"));
            username = StringEscapeUtils.escapeJava(request.getParameter("username"));
            password = StringEscapeUtils.escapeJava(request.getParameter("password"));

            Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
            Matcher m = p.matcher(email);
            if (!m.matches()) {
            	message = "Error in format of the email";
            	throw new Exception();
            }
            //System.out.println("EMAIL " + email);
            if (email == null || username == null || password == null || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                message = "Missing or empty credential value";
            	throw new Exception();
            }
        } catch (Exception e) {
        	ctx.setVariable("errorMsg", message);
            String path = "/index.html";
            templateEngine.process(path, ctx, response.getWriter());
            return;
        }
        
        try {
            cs.registerConsumer(username, password,email);
        } catch (DuplicateException | PersistenceException e) {
        	ctx.setVariable("registration", "KO");
            String path = "/index.html";
            templateEngine.process(path, ctx, response.getWriter());
            return;
        }
        
        ctx.setVariable("registration", "OK");
        String path = "/index.html";
        templateEngine.process(path, ctx, response.getWriter());
        return;
    }

}
