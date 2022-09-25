package it.polimi.db2.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

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

import it.polimi.db2.entities.Consumer;
import it.polimi.db2.entities.OptionalProduct;
import it.polimi.db2.entities.Order;
import it.polimi.db2.entities.OrderOptionalProducts;
import it.polimi.db2.entities.ServiceActivationSchedule;
import it.polimi.db2.services.AuditService;
import it.polimi.db2.services.ConsumerService;
import it.polimi.db2.services.OrderService;
import it.polimi.db2.utils.Subscription;

/**
 * Servlet implementation class Payment
 */
@WebServlet("/Payment")
public class Payment extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;
    
    @EJB(name = "it.polimi.db2.services/OrderService")
    private OrderService os;
    @EJB(name="it.polimi.db2.services/ConsumerService")
    private ConsumerService cs;
    @EJB(name="it.polimi.db2.services/AuditService")
    private AuditService as;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Payment() {
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
		int payment;
		Random random = new Random();
		if(request.getParameter("payment")!=null) {
			try {
				payment=Integer.parseInt(request.getParameter("payment"));
				if (payment!=0 && payment!=1) {
					payment=random.nextInt(2);
				}
			}catch (NumberFormatException e) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Payment must be an integer!");
                return;
			}
		}
		else {
			payment = random.nextInt(2);
		}

        Consumer consumer = (Consumer) request.getSession().getAttribute("consumer");
        Subscription subscription = (Subscription) request.getSession().getAttribute("subscription");
        Order order;
        OrderOptionalProducts orderopt;
        List<OrderOptionalProducts> list= new ArrayList<>();
        if (request.getSession().getAttribute("retryorder") != null) {
			int retryorderid = (int) request.getSession().getAttribute("retryorder"); 
			order= os.updateOrder(retryorderid, payment);
			
			request.getSession().removeAttribute("retryorder");
        }
        else {
        	order = new Order();
        	order.setConsumer(consumer);
        	order.setStartDate(subscription.getActivation());
        	//order.setEndDate(subscription.getDeactivation());
        	order.setTimestamp(Calendar.getInstance().getTime());
        	order.setServicePackage(subscription.getServicePackage());
        	order.setValidityPeriod(subscription.getValidityPeriod());
        	order.setTotal(subscription.getTotalPrice());
        	order.setStatus(payment);
        	for (OptionalProduct op: subscription.getOptionalProducts()){
        		orderopt= new OrderOptionalProducts();
        		orderopt.setOptionalProduct(op);
        		orderopt.setOrder(order);
        		orderopt.setStatus(payment);
        		list.add(orderopt);
        	}
        	order.setOrderOptProds(list);
        	
        	ServiceActivationSchedule schedule= null;
        	if(payment==1) {
        		schedule= new ServiceActivationSchedule();
	        	schedule.setOrder(order);        
	        	schedule.setActivation(subscription.getActivation());
	        	schedule.setDeactivation(subscription.getDeactivation());
	        	schedule.setOptionalProducts(subscription.getOptionalProducts());
	        	schedule.setServices(order.getServicePackage().getServices());
        	}
        	
        	try {
        		os.insertOrder(order, schedule);
        	}catch (PersistenceException e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while inserting the order in the database");
                return;
        	}
		}
        
        //payment failed
        if (payment == 0) { 
            consumer.setIs_insolvent(1);
            consumer.setFailed_payments(consumer.getFailed_payments()+1);
            
            if (consumer.getFailed_payments() == 3) {
            	try {
            		as.insertFailedOrder(consumer, order);
            	} catch (PersistenceException e) {
            		response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while inserting the failed order in the database");
                    return;
            	}
            }
        }
        else {
        	
        	if (consumer.getFailed_payments() > 0)
        		consumer.setFailed_payments(consumer.getFailed_payments()-1);
        	
        	if (consumer.getFailed_payments() == 0) {
        		if (consumer.getIs_insolvent() == 1) {
        			consumer.setIs_insolvent(0);
        		}
        		try {
        			as.updateFailedOrder(consumer);
            	} catch (PersistenceException e) {
            		response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while inserting the failed order in the database");
                    return;
            	}
        	}
        }
        cs.updateConsumer(consumer);
        
		request.getSession().setAttribute("payment",payment);

		ServletContext servletContext = getServletContext();
        String path;
		path = servletContext.getContextPath() + "/Home";		
		response.sendRedirect(path);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
