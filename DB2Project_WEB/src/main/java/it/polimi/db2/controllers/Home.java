package it.polimi.db2.controllers;

import java.io.IOException;
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
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.db2.entities.Consumer;
import it.polimi.db2.entities.Order;
import it.polimi.db2.entities.ServicePackage;
import it.polimi.db2.services.OrderService;
import it.polimi.db2.services.ServicePackageService;

/**
 * Servlet implementation class Home
 */
@WebServlet("/Home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	
	@EJB(name="it.polimi.db2.services/ServicePackageService")
	private ServicePackageService sps;
	@EJB(name="it.polimi.db2.services/OrderService")
	private OrderService os;
       
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
		try {
			List<ServicePackage> packs= sps.findAllServicePackages();
			ServletContext servletContext = getServletContext();
			final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
			ctx.setVariable("service_packages", packs);
			if (request.getSession().getAttribute("consumer") != null) {
				Consumer consumer = (Consumer) request.getSession().getAttribute("consumer");
				List<Order> rejected = os.findRejectedOrdersByConsumer(consumer);
				if (!rejected.isEmpty()) {
					ctx.setVariable("rejected_orders", rejected);
				}
			}
			if (request.getSession().getAttribute("payment") != null) {
				ctx.setVariable("payment",request.getSession().getAttribute("payment"));
				request.getSession().removeAttribute("subscription");
				request.getSession().removeAttribute("service_package");
				request.getSession().removeAttribute("payment");
			}
			
			String path="/WEB-INF/home.html";
			templateEngine.process(path, ctx, response.getWriter());
			return;
		} catch (PersistenceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving service_packages or rejected orders");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
