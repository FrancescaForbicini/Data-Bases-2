package it.polimi.db2.services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.openjpa.persistence.PersistenceException;

import it.polimi.db2.entities.ServiceActivationSchedule;

/**
 * Session Bean implementation class OrderOptProdsService
 */
@Stateless
public class ScheduleService {
	@PersistenceContext(unitName = "DB2Project_EJB")
    private EntityManager em;
	
    /**
     * Default constructor. 
     */
    public ScheduleService() {
    }

    public void insertSchedule(ServiceActivationSchedule schedule) throws PersistenceException{
    	em.persist(schedule);
    }
    
}
