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

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.db2.entities.OptionalProduct;
import it.polimi.db2.entities.ServicePackage;
import it.polimi.db2.entities.ValidityPeriod;
import it.polimi.db2.services.ServicePackageService;

/**
 * Servlet implementation class Buy
 */
@WebServlet("/Buy")
public class Buy extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	
	@EJB(name="it.polimi.db2.services/ServicePackageService")
	private ServicePackageService sps;
	/*@EJB(name="it.polimi.db2.services/ValidityPeriodService")
	private ValidityPeriodService vps;
	@EJB(name="it.polimi.db2.services/OptionalProductService")
	private OptionalProductService ops;*/
	
	public void init() throws ServletException{
		ServletContext servletContext = getServletContext();
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		this.templateEngine = new TemplateEngine();
		this.templateEngine.setTemplateResolver(templateResolver);
		templateResolver.setSuffix(".html");
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Buy() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		
		int id;
		try {
			if (request.getParameter("spid")!=null) {
				id = Integer.parseInt(request.getParameter("spid").toString());
			}
			else {
				id = Integer.parseInt(request.getSession().getAttribute("spid").toString());
				request.getSession().removeAttribute("spid");
			}
		} catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Id of service package must be an integer!");
			return;
		}
		
		ServicePackage sp = sps.findServicePackage(id);
		if (sp==null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not find the service package requested");
			return;
		}
		request.getSession().setAttribute("service_package", sp);
		List <ValidityPeriod> vp = sp.getValidityPeriods();
		ctx.setVariable("validity_periods", vp);
		List <OptionalProduct> op = sp.getOptionalProducts();
		ctx.setVariable("optprods", op);
		if (request.getSession().getAttribute("errorMsg")!=null) {
			ctx.setVariable("errorMsg", request.getSession().getAttribute("errorMsg"));
			request.getSession().removeAttribute("errorMsg");
		}
		String path="/WEB-INF/buypage.html";
		templateEngine.process(path, ctx, response.getWriter());
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
