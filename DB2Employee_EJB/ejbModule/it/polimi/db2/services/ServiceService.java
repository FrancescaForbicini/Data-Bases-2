package it.polimi.db2.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.polimi.db2.entities.Service;
import it.polimi.db2.exceptions.NotFoundException;

/**
 * Session Bean implementation class ServiceService
 */
@Stateless
public class ServiceService {
	@PersistenceContext(unitName = "DB2Employee_EJB")
	private EntityManager em;
	
    /**
     * Default constructor. 
     */
    public ServiceService() {
    }

    public List<Service> findAllServices(){
    	return em.createNamedQuery("Service.findAll", Service.class).getResultList();
    }

	public List<Service> findAllById(String[] sIds) throws NotFoundException, NumberFormatException{
		int id;
		List<Service> result = new ArrayList<>();
		Service service;
		
		for(String stringId : sIds) {
			id = Integer.parseInt(stringId);
			service = em.find(Service.class, id);
			
			if(service==null) {
				throw new NotFoundException("Service not found!");
			}
			
			result.add(service);
		}
		
		return result;
	}
}
