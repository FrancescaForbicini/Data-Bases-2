package it.polimi.db2.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "order_optprod", schema = "db_test")

public class OrderOptionalProducts implements Serializable {
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;
    
    @ManyToOne
    @JoinColumn(name="optprod_id")
    private OptionalProduct optionalProduct;
    
    @Column(name="order_status")
    private int status;
    
    
    public Order getOrder() {
    	return this.order;
    }
    
    public void setOrder(Order order) {
    	this.order = order;
    }
    
    public OptionalProduct getOptionalProduct () {
    	return this.optionalProduct;
    }
    
    public void setOptionalProduct(OptionalProduct optionalProduct) {
    	this.optionalProduct = optionalProduct;
    }
    
    public int getStatus() {
    	return this.status;
    }
    
    public void setStatus(int status) {
    	this.status = status;
    }

}
