package it.polimi.db2.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import it.polimi.db2.entities.*;

/**
 * Session Bean implementation class OrderService
 */
@Stateless
public class OrderService {
	@PersistenceContext(unitName = "DB2Project_EJB")
    private EntityManager em;
	
	@EJB(name="it.polimi.db2.services/OrderOptProdsService")
	private ScheduleService ss;

    /**
     * Default constructor. 
     */
    public OrderService() {
    }
    
    public List<Order> findRejectedOrdersByConsumer(Consumer consumer){
    	return em.createNamedQuery("Order.findRejectedOrders", Order.class).setParameter(1, consumer).getResultList();
    }
    
    public Order findOrder(int id) {
    	return em.find(Order.class,id);
    }
    
    public void insertOrder(Order order, ServiceActivationSchedule schedule) throws PersistenceException{ 
    	try {
    		
    		em.persist(order);
    		
    		if (schedule!=null) {
    			ss.insertSchedule(schedule);
    		}
    		
    		em.flush();
    	}catch(PersistenceException e) {
    		System.out.println("I am in the catch of order service");
    		throw e;
    	}
    }
    
    public Order updateOrder(int orderid, int status) {
    	Order order= em.find(Order.class, orderid);
    	if (status==0) {
    		return order;
    	}
    	ServiceActivationSchedule schedule=  new ServiceActivationSchedule();
    	
    	Calendar c = Calendar.getInstance();
		c.setTime(order.getStartDate());
		c.add(Calendar.MONTH,order.getValidityPeriod().getMonths());
    	schedule.setOrder(order);        
    	schedule.setActivation(order.getStartDate());
    	schedule.setDeactivation(c.getTime());
    	List<OptionalProduct> optProds= new ArrayList<>();
    	for (OrderOptionalProducts oop: order.getOrderOptProds()) {
    		optProds.add(oop.getOptionalProduct());
    	}
    	schedule.setOptionalProducts(optProds);
    	schedule.setServices(order.getServicePackage().getServices());
    	
    	order.setStatus(status);
    	for(OrderOptionalProducts oop: order.getOrderOptProds()) {
    		oop.setStatus(status);
    	}
    	ss.insertSchedule(schedule);
    	
    	em.flush();
    	
    	return order;
    }

}
