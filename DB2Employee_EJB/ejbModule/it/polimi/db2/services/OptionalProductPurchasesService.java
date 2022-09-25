package it.polimi.db2.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import it.polimi.db2.entities.purchases.OptionalProductPurchases;

/**
 * Session Bean implementation class OptionalProductPurchasesService
 */
@Stateless
public class OptionalProductPurchasesService {
	@PersistenceContext(unitName = "DB2Employee_EJB")
	private EntityManager em;
	
    /**
     * Default constructor. 
     */
    public OptionalProductPurchasesService() {
    }

    public OptionalProductPurchases getMax() throws PersistenceException{
    	List<OptionalProductPurchases> result = em.createNamedQuery("OptionalProductPurchases.getMax", OptionalProductPurchases.class).getResultList();
    	if (result.size()!=1) throw new PersistenceException("Max not found!");
    	return result.get(0);
    }
}
