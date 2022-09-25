package it.polimi.db2.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import it.polimi.db2.entities.OptionalProduct;
import it.polimi.db2.exceptions.NotFoundException;

/**
 * Session Bean implementation class OptionalProductService
 */
@Stateless
public class OptionalProductService {
	@PersistenceContext(unitName = "DB2Employee_EJB")
	private EntityManager em;
	
    /**
     * Default constructor. 
     */
	 public List<OptionalProduct> findAllOptionalProduct(){
	    	return em.createNamedQuery("OptionalProduct.findAll", OptionalProduct.class).getResultList();
	 }
	 
	 public void insertOptionalProduct(OptionalProduct op) throws PersistenceException{
		 em.persist(op);
		 em.flush();
	 }

	public List<OptionalProduct> findAllById(String[] optProdIds) throws NotFoundException, NumberFormatException{
		int id;
		List<OptionalProduct> result = new ArrayList<>();
		OptionalProduct op;
		
		for(String stringId : optProdIds) {
			id = Integer.parseInt(stringId);
			op = em.find(OptionalProduct.class, id);
			
			if(op==null) {
				throw new NotFoundException("OptionalProduct not found!");
			}
			
			result.add(op);
		}
		
		return result;
	}
	 
}
