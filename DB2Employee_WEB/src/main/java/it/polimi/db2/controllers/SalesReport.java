package it.polimi.db2.controllers;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.openjpa.persistence.PersistenceException;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.db2.entities.Audit;
import it.polimi.db2.entities.Consumer;
import it.polimi.db2.entities.Order;
import it.polimi.db2.entities.purchases.OptionalProductPurchases;
import it.polimi.db2.entities.purchases.ServicePackagePurchases;
import it.polimi.db2.entities.purchases.ServicePackageValidityPeriodPurchases;
import it.polimi.db2.services.AuditService;
import it.polimi.db2.services.ConsumerService;
import it.polimi.db2.services.OptionalProductPurchasesService;
import it.polimi.db2.services.OrderService;
import it.polimi.db2.services.ServicePackagePurchasesService;
import it.polimi.db2.services.ServicePackageValidityPeriodPurchasesService;

/**
 * Servlet implementation class SalesReport
 */
@WebServlet("/SalesReport")
public class SalesReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	
	@EJB(name = "it.polimi.db2.services/ConsumerService")
	private ConsumerService cs;
	@EJB(name = "it.polimi.db2.services/AuditService")
	private AuditService as;
	@EJB(name = "it.polimi.db2.services/OrderService")
	private OrderService os;
	@EJB(name = "it.polimi.db2.services/ServicePackagePurchasesService")
	private ServicePackagePurchasesService spps;
	@EJB(name = "it.polimi.db2.services/ServicePackageValidityPeriodPurchasesService")
	private ServicePackageValidityPeriodPurchasesService spvpps;
	@EJB(name = "it.polimi.db2.services/OptionalProductPurchasesService")
	private OptionalProductPurchasesService opps;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SalesReport() {
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
		ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		List<Consumer> insolventCustomers;
		List<Audit> audits;
		List<Order> suspendedOrders;
		List<ServicePackagePurchases> packPurchases;
		List<ServicePackageValidityPeriodPurchases> packvpPurchases;
		OptionalProductPurchases op;
		
		
		try {
			insolventCustomers= cs.getInsolvent();
			audits= as.getAlerts();
			suspendedOrders= os.findRejectedOrders();
			packPurchases= spps.getAll();
			packvpPurchases= spvpps.getAll();
			op= opps.getMax();
		}catch (PersistenceException e) {
	        ctx.setVariable("errormsg", e.getMessage());
	        String path = "/WEB-INF/salesreportpage.html";
	        templateEngine.process(path, ctx, response.getWriter());
	        return;
		}
		catch(EJBException e) {
	        ctx.setVariable("errormsg", e.getMessage());
	        String path = "/WEB-INF/salesreportpage.html";
	        templateEngine.process(path, ctx, response.getWriter());
	        return;
		}
		
		ctx.setVariable("insolventCustomers", insolventCustomers);
        ctx.setVariable("audits", audits);
        ctx.setVariable("suspendedOrders", suspendedOrders);
        ctx.setVariable("packPurchases", packPurchases);
        ctx.setVariable("packvpPurchases", packvpPurchases);
        ctx.setVariable("op", op);
        String path = "/WEB-INF/salesreportpage.html";
        templateEngine.process(path, ctx, response.getWriter());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
