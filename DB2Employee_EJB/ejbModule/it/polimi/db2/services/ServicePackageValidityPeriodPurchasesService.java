package it.polimi.db2.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import it.polimi.db2.entities.purchases.ServicePackageValidityPeriodPurchases;

/**
 * Session Bean implementation class ServicePackageValidityPeriodPurchasesService
 */
@Stateless
public class ServicePackageValidityPeriodPurchasesService {
	@PersistenceContext(unitName = "DB2Employee_EJB")
	private EntityManager em;
	
    /**
     * Default constructor. 
     */
    public ServicePackageValidityPeriodPurchasesService() {
    }

    public List<ServicePackageValidityPeriodPurchases> getAll() throws PersistenceException{
    	return em.createNamedQuery("ServicePackageValidityPeriodPurchases.getAll", ServicePackageValidityPeriodPurchases.class).getResultList();
    }
}
