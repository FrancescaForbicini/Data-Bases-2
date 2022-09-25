package it.polimi.db2.entities;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "audit", schema = "db_test")
@NamedQuery(name = "Audit.findFailedOrder", 
			query = "SELECT a FROM Audit a WHERE a.consumer = ?1" )

public class Audit implements Serializable{
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="rejection_timestamp")
    private Date rejectionTimestamp;
    @Column (name = "amount")
    private int totalPrice;
    @OneToOne
    @JoinColumn(name="order_id")
    private Order order;
    @ManyToOne
    @JoinColumn(name="consumer_id")
    private Consumer consumer;    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getRejectionTimestamp() {
        return rejectionTimestamp;
    }

    public void setRejectionTimestamp(Date rejectionTimestamp) {
        this.rejectionTimestamp = rejectionTimestamp;
    }


    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }
    
    public void setTotalPrice(int totalPrice) {
    	this.totalPrice = totalPrice;	
    }
    
    public int getTotalPrice() {
    	return this.totalPrice;
    }
}