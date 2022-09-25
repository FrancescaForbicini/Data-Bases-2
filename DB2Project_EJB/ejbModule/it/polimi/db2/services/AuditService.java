package it.polimi.db2.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import it.polimi.db2.entities.Audit;
import it.polimi.db2.entities.Consumer;
import it.polimi.db2.entities.Order;

/**
 * Session Bean implementation class AuditService
 */
@Stateless
public class AuditService {
	@PersistenceContext(unitName = "DB2Project_EJB")
    private EntityManager em;
	
    /**
     * Default constructor. 
     */
    public AuditService() {}
    
    public void insertFailedOrder(Consumer consumer, Order order) throws PersistenceException{
    	Audit audit = new Audit();
    	audit.setConsumer(consumer);
    	audit.setOrder(order);
    	audit.setRejectionTimestamp(order.getTimestamp());
    	audit.setTotalPrice(order.getTotal());
    	em.persist(audit);
    	em.flush();
    }
    
    public void updateFailedOrder(Consumer consumer) {
    	List<Audit> audit = new ArrayList<>();
    	try {
            audit = em.createNamedQuery("Audit.findFailedOrder", Audit.class).setParameter(1, consumer).getResultList();

        } catch (PersistenceException e) {
            throw e;
        }
    	//delete the entry for the user that now is not insolvent anymore
    	if (!audit.isEmpty()) {
    		Audit auditToRemove;
    		for (int i=0; i<audit.size(); i++) {
    			auditToRemove = em.find(Audit.class, audit.get(i).getId());
        		em.remove(auditToRemove);
        		em.flush();
    		} 
    	}
    }

}
