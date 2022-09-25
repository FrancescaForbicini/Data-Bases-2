package it.polimi.db2.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.persistence.PersistenceException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.db2.entities.OptionalProduct;
import it.polimi.db2.entities.Service;
import it.polimi.db2.entities.ServicePackage;
import it.polimi.db2.entities.ValidityPeriod;
import it.polimi.db2.exceptions.NotFoundException;
import it.polimi.db2.services.OptionalProductService;
import it.polimi.db2.services.ServicePackageService;
import it.polimi.db2.services.ServiceService;
import it.polimi.db2.services.ValidityPeriodService;

/**
 * Servlet implementation class CreateServicePack
 */
@WebServlet("/CreateServicePack")
public class CreateServicePack extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	
	@EJB(name = "it.polimi.db2.services/OptionalProductService")
    private OptionalProductService ops;
	@EJB(name = "it.polimi.db2.services/ValidityPeriodService")
	private ValidityPeriodService vps;
	@EJB(name = "it.polimi.db2.services/ServiceService")
	private ServiceService ss;
	@EJB(name = "it.polimi.db2.services/ServicePackageService")
	private ServicePackageService sps;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateServicePack() {
        super();
    }

    public void init() {
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
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name, path;
		String[] vpIds, sIds, optProdIds;
		List<ValidityPeriod> valPeriods;
		List<OptionalProduct> optProds = new ArrayList<>();
		List<Service> services;
		
		name = request.getParameter("name");
		vpIds = request.getParameterValues("valpers");
		sIds = request.getParameterValues("services");
		optProdIds = request.getParameterValues("optprods");
		
		if (name==null || name.isEmpty() || vpIds==null || sIds==null || vpIds.length==0 || sIds.length==0) {
			request.getSession().setAttribute("errorMsg", "Missing parameters for creation of a service package!");
	        path = getServletContext().getContextPath() + "/Home";
	        response.sendRedirect(path);
	        return;
		}
		
		//insert into servicepackage, sp_vp, sp_op, sp_s
		try {
			valPeriods = vps.findAllById(vpIds);
			if (optProdIds != null)
				optProds = ops.findAllById(optProdIds);
			services = ss.findAllById(sIds);
		} catch (NotFoundException  | NumberFormatException e) {
			request.getSession().setAttribute("errorMsg", e.getMessage());
	        path = getServletContext().getContextPath() + "/Home";
	        response.sendRedirect(path);
	        return;
		}
		ServicePackage sp= new ServicePackage();
		sp.setName(name);
		sp.setValidityPeriods(valPeriods);
		sp.setOptionalProducts(optProds);
		sp.setServices(services);
		
		try {
			sps.insertServicePackage(sp);
		} catch (PersistenceException e) {
			request.getSession().setAttribute("errorMsg", "An error occurred while inserting the new service package in the db");
	        path = getServletContext().getContextPath() + "/Home";
	        response.sendRedirect(path);
	        return;
		}
		
		request.getSession().setAttribute("errorMsg", "Service package succesfully created!");
        path = getServletContext().getContextPath() + "/Home";
        response.sendRedirect(path);
        return;
	}

}
