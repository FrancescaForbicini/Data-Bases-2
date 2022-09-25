package it.polimi.db2.services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import it.polimi.db2.entities.Consumer;

import java.util.List;


@Stateless
public class ConsumerService {
    @PersistenceContext(unitName = "DB2Employee_EJB")
    private EntityManager em;
    
    public ConsumerService() {}

    /*public Consumer checkCredentials(String username, String password, String email) throws CredentialsException, NonUniqueResultException {
    	List<Consumer> consumers = null;
    	try {
            consumers = em.createNamedQuery("Consumer.checkCredentials", Consumer.class).setParameter(1, username).setParameter(2, password).setParameter(3,email)
                    .getResultList();

        } catch (PersistenceException e) {
            throw new CredentialsException("Could not verify credentals");
        }
        //System.out.println(consumers.get(0));
        if (consumers.isEmpty())
            return null;
        else if (consumers.size() == 1)
            return consumers.get(0);
        throw new NonUniqueResultException("More than one user registered with same credentials");

    }
    
    public void updateConsumer(Consumer consumer) {
    	em.merge(consumer); 	
    }
    
    public void registerConsumer(String username, String password, String email) throws DuplicateException, PersistenceException {
    	List<Consumer> consumers;
    	try {
            consumers = em.createNamedQuery("Consumer.checkDuplicate", Consumer.class).setParameter(1, username).getResultList();

        } catch (PersistenceException e) {
            throw e;
        }
    	
    	if (consumers.isEmpty()) {
			Consumer consumer = new Consumer();
			consumer.setUsername(username);
			consumer.setPassword(password);
			consumer.setEmail(email);
			consumer.setFailed_payments(0);
			consumer.setIs_insolvent(0);

			em.persist(consumer);
			em.flush();
    	}
    	else {
    		throw new DuplicateException("There is already a user with these credentials");
    	}
    	
    }*/
    
    public List<Consumer> getInsolvent() throws PersistenceException{
    	return em.createNamedQuery("Consumer.getInsolvent", Consumer.class).getResultList();
    }
}
