package it.polimi.db2.services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import it.polimi.db2.entities.ServicePackage;

/**
 * Session Bean implementation class ServicePackageService
 */
@Stateless
public class ServicePackageService {
	@PersistenceContext(unitName = "DB2Employee_EJB")
	private EntityManager em;
	
    /**
     * Default constructor. 
     */
    public ServicePackageService() {
    }

    /*public List<ServicePackage> findAllServicePackages(){
    	return em.createNamedQuery("ServicePackage.findAll", ServicePackage.class).getResultList();
    }
    
    public ServicePackage findServicePackage(int id) {
    	return em.find(ServicePackage.class, id);    
	}*/
    
    public void insertServicePackage(ServicePackage sp) throws PersistenceException{
    	em.persist(sp);
    	em.flush();
    }
    
}
