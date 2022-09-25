package it.polimi.db2.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import it.polimi.db2.entities.purchases.ServicePackagePurchases;

/**
 * Session Bean implementation class ServicePackagePurchasesService
 */
@Stateless
public class ServicePackagePurchasesService {
	@PersistenceContext(unitName = "DB2Employee_EJB")
	private EntityManager em;
	
    /**
     * Default constructor. 
     */
    public ServicePackagePurchasesService() {
    }

    public List<ServicePackagePurchases> getAll() throws PersistenceException{
    	return em.createNamedQuery("ServicePackagePurchases.getAll", ServicePackagePurchases.class).getResultList();
    }
}
