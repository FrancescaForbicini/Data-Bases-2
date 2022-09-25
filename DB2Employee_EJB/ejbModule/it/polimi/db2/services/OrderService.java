package it.polimi.db2.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.polimi.db2.entities.*;

/**
 * Session Bean implementation class OrderService
 */
@Stateless
public class OrderService {
	@PersistenceContext(unitName = "DB2Employee_EJB")
	private EntityManager em;

    /**
     * Default constructor. 
     */
    public OrderService() {
    }
    
    public List<Order> findRejectedOrders(){
    	return em.createNamedQuery("Order.findRejectedOrders", Order.class).getResultList();
    }
    
    /*public Order findOrder(int id) {
    	return em.find(Order.class,id);
    }
    
    public void insertOrder(Order order) throws PersistenceException{ 
    	try {
    		em.persist(order);
    		em.flush();
    	}catch(PersistenceException e) {
    		System.out.print("CIAO: " + order.getId());
    		throw e;
    	}
    }
    
    public Order updateOrder(int id, int status) {
    	Order order = em.find(Order.class,id);
    	order.setStatus(status);
    	return order;
    }*/

}
