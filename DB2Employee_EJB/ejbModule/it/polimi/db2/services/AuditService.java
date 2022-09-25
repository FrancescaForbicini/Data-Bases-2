package it.polimi.db2.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import it.polimi.db2.entities.Audit;

/**
 * Session Bean implementation class AuditService
 */
@Stateless
public class AuditService {
	@PersistenceContext(unitName = "DB2Employee_EJB")
	private EntityManager em;
	
    /**
     * Default constructor. 
     */
    public AuditService() {}
    
    /*public void insertFailedOrder(Consumer consumer, Order order) throws PersistenceException{
    	Audit audit = new Audit();
    	audit.setConsumer(consumer);
    	audit.setOrder(order);
    	audit.setRejectionTimestamp(order.getTimestamp());
    	audit.setTotalPrice(order.getTotal());
    	em.persist(audit);
    }*/

    public List<Audit> getAlerts() throws PersistenceException{
    	return em.createNamedQuery("Audit.getAll", Audit.class).getResultList();
	}
}
