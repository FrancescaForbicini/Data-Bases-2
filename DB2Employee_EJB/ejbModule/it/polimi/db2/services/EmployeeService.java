package it.polimi.db2.services;

import java.util.List;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;


import it.polimi.db2.entities.Employee;
import it.polimi.db2.exceptions.CredentialsException;

/**
 * Session Bean implementation class EmployeeService
 */
@Stateless
public class EmployeeService {
	@PersistenceContext(unitName = "DB2Employee_EJB")
    private EntityManager em;
	
    public EmployeeService() {
    }

    public Employee checkCredentials(String username, String password) throws CredentialsException, NonUniqueResultException {
    	List<Employee> employees;
    	try {
            employees = em.createNamedQuery("Employee.checkCredentials", Employee.class).setParameter(1, username).setParameter(2, password)
                    .getResultList();
        } catch (PersistenceException e) {
            throw new CredentialsException("Could not verify credentals");
        }
    	
        if (employees.isEmpty())
            return null;
        else if (employees.size() == 1)
            return employees.get(0);
        throw new NonUniqueResultException("More than one user registered with same credentials");

    }
}
