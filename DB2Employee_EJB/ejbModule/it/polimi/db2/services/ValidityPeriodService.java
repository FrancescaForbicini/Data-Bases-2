package it.polimi.db2.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.polimi.db2.entities.ValidityPeriod;
import it.polimi.db2.exceptions.NotFoundException;

/**
 * Session Bean implementation class ValidityPeriodService
 */
@Stateless
public class ValidityPeriodService {
	@PersistenceContext(unitName = "DB2Employee_EJB")
	private EntityManager em;
	
    /**
     * Default constructor. 
     */
	 public List<ValidityPeriod> findAllValidityPeriod(){
	    	return em.createNamedQuery("ValidityPeriod.findAll", ValidityPeriod.class).getResultList();
	    }
	 
	 /*public ValidityPeriod findValidityPeriod(int id) {
		 return em.find(ValidityPeriod.class, id);
	 }*/

	public List<ValidityPeriod> findAllById(String[] vpIds) throws NotFoundException, NumberFormatException{
		int id;
		List<ValidityPeriod> result = new ArrayList<>();
		ValidityPeriod vp;
		
		for(String stringId : vpIds) {
			id = Integer.parseInt(stringId);
			vp = em.find(ValidityPeriod.class, id);
			
			if(vp==null) {
				throw new NotFoundException("ValidityPeriod not found!");
			}
			
			result.add(vp);
		}
		
		return result;
	}
}
