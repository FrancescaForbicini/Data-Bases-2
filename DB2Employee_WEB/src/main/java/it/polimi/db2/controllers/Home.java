package it.polimi.db2.controllers;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.db2.entities.OptionalProduct;
import it.polimi.db2.entities.Service;
import it.polimi.db2.entities.ValidityPeriod;
import it.polimi.db2.services.OptionalProductService;
import it.polimi.db2.services.ServiceService;
import it.polimi.db2.services.ValidityPeriodService;

/**
 * Servlet implementation class Home
 */
@WebServlet("/Home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	
	@EJB(name = "it.polimi.db2.services/OptionalProductService")
    private OptionalProductService ops;
	@EJB(name = "it.polimi.db2.services/ValidityPeriodService")
	private ValidityPeriodService vps;
	@EJB(name = "it.polimi.db2.services/ServiceService")
	private ServiceService ss;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Home() {
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
		//retrieve optional products from db to possibly associate them with a new service package
		List<OptionalProduct> optProds = ops.findAllOptionalProduct();
		List<ValidityPeriod> validityPeriods = vps.findAllValidityPeriod();
		List<Service> services = ss.findAllServices();
		
		//process home.html
		ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        ctx.setVariable("optProds", optProds);
        ctx.setVariable("validityPeriods", validityPeriods);
        ctx.setVariable("services", services);
        HttpSession session = request.getSession();
        if (session.getAttribute("errorMsg")!=null) {
        	ctx.setVariable("errorMsg", session.getAttribute("errorMsg"));
        	session.removeAttribute("errorMsg");
        }
        String path = "/WEB-INF/home.html";
        templateEngine.process(path, ctx, response.getWriter());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
