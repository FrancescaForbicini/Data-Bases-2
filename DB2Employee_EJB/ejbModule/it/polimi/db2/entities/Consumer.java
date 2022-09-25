package it.polimi.db2.entities;

import javax.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "consumer", schema = "db_test")
@NamedQueries({
	/*@NamedQuery(
			name = "Consumer.checkCredentials", 
			query = "SELECT r FROM Consumer r  WHERE r.username = ?1 and r.password = ?2 and r.email = ?3" ),
	@NamedQuery(
			name = "Consumer.checkDuplicate",
			query = "SELECT r FROM Consumer r  WHERE r.username = ?1"),*/
	@NamedQuery(
			name = "Consumer.getInsolvent",
			query = "SELECT r FROM Consumer r WHERE r.is_insolvent=1")
})

public class Consumer implements Serializable{
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private String email;
    private int is_insolvent;
    private int failed_payments;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public int getIs_insolvent() {
        return is_insolvent;
    }

    public void setIs_insolvent(int is_insolvent) {
        this.is_insolvent = is_insolvent;
    }

    public int getFailed_payments() {
        return failed_payments;
    }

    public void setFailed_payments(int failed_payments) {
        this.failed_payments = failed_payments;
    }
    
    public int getID() {
        return this.id;
    }
}

