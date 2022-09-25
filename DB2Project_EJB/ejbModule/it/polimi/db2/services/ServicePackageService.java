package it.polimi.db2.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.polimi.db2.entities.ServicePackage;

/**
 * Session Bean implementation class ServicePackageService
 */
@Stateless
public class ServicePackageService {
	@PersistenceContext(unitName = "DB2Project_EJB")
    private EntityManager em;
	
    /**
     * Default constructor. 
     */
    public ServicePackageService() {
    }

    public List<ServicePackage> findAllServicePackages(){
    	return em.createNamedQuery("ServicePackage.findAll", ServicePackage.class).getResultList();
    }
    
    public ServicePackage findServicePackage(int id) {
    	return em.find(ServicePackage.class, id);    
	}
    
}
