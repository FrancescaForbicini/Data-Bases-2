package it.polimi.db2.entities;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "employee", schema = "db_test")
@NamedQuery(name = "Employee.checkCredentials",
			query = "SELECT r FROM Employee r  WHERE r.username = ?1 and r.password = ?2")

public class Employee implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username;
	private String password;
	
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username=username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password=password;
	}
}
