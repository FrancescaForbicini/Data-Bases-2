package it.polimi.db2.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.db2.entities.OptionalProduct;
import it.polimi.db2.entities.Order;
import it.polimi.db2.entities.ServicePackage;
import it.polimi.db2.entities.ValidityPeriod;
import it.polimi.db2.services.OrderService;
import it.polimi.db2.utils.Subscription;

/**
 * Servlet implementation class Confirmation
 */
@WebServlet("/Confirmation")
public class Confirmation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	
    /*@EJB(name = "it.polimi.db2.services/ValidityPeriodService")
    private ValidityPeriodService vps;
    @EJB(name = "it.polimi.db2.services/OptionalProductService")
    private OptionalProductService ops;*/
    @EJB(name = "it.polimi.db2.services/OrderService")
    private OrderService os;
    
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Confirmation() {
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
		Subscription subscription = new Subscription(); 
		
		if (request.getSession().getAttribute("subscription") == null) {
			int id_vp = 0;
			Date date = new Date();
			String[] id_op = null;
			ServicePackage sp = null;
			ValidityPeriod vp = null;
			ArrayList<OptionalProduct> op = new ArrayList<>();
			Order order = null;
			
			if (request.getParameter("orderid") == null) {
				sp = (ServicePackage) request.getSession().getAttribute("service_package");
				try {
					id_vp = Integer.parseInt(request.getParameter("validity_periods"));
				}catch (NumberFormatException e) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Id of service package and of validity period must be an integer!");
					return;
				}
				
				id_op = request.getParameterValues("optprods");
				try {
					date = DateTime.parse(request.getParameter("date")).toDate();
				}
				catch(Exception e) {
					String path;
			        path = getServletContext().getContextPath() + "/Buy";
			        request.getSession().setAttribute("errorMsg", "Malformed data!");
			        request.getSession().setAttribute("spid", sp.getId());
					response.sendRedirect(path);
					return;
				}
				try {
					if (Calendar.getInstance().getTime().after(date) && !Calendar.getInstance().getTime().equals(date)) {
						throw new Exception();
					}
				}
				catch(Exception e ) {
					String path;
			        path = getServletContext().getContextPath() + "/Buy";
			        request.getSession().setAttribute("errorMsg", "You have to select a date after today!");
			        request.getSession().setAttribute("spid", sp.getId());
					response.sendRedirect(path);
					return;
				}	
				
				for (ValidityPeriod validityPeriod: sp.getValidityPeriods())
					if (id_vp == validityPeriod.getId()) {
						vp = validityPeriod;
						break;
					}
				if (id_op != null) {
					for (String optionalProduct: id_op) {
						for (OptionalProduct optional: sp.getOptionalProducts())
							if (Integer.parseInt(optionalProduct) == optional.getId())
								op.add(optional);
					}
				}
			}
			else {
				int orderid;
				try {
					orderid = Integer.parseInt(request.getParameter("orderid"));
				}catch (NumberFormatException e) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Id of order must be an integer!");
					return;
				}
				order = os.findOrder(orderid);
				sp = order.getServicePackage();
				vp = order.getValidityPeriod();
				//op.addAll(order.getOptionalProducts());
				request.getSession().setAttribute("retryorder", orderid);
			}
			
			if (vp == null || (id_op != null && id_op.length != op.size())) {
				String path;
		        path = getServletContext().getContextPath() + "/Buy";
		        request.getSession().setAttribute("errorMsg", "Missing validity period or wrong optional products selected");
		        request.getSession().setAttribute("spid", sp.getId());
				response.sendRedirect(path);
				return;
			}
			
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.MONTH,vp.getMonths());
			
			
			subscription.setServicePackage(sp);
			subscription.setValidityPeriod(vp);
			subscription.setOptionalProducts(op);
			subscription.setActivation(date);			
			subscription.setDeactivation(c.getTime());
			request.getSession().setAttribute("subscription",subscription);
		}
		
		String path;
		ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		path="/WEB-INF/confirmationpage.html";
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
