package it.polimi.db2.entities;

import javax.persistence.*;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "order", schema = "db_test")
@NamedQuery(name = "Order.findRejectedOrders", query = "SELECT o FROM Order o WHERE o.status=0")

public class Order implements Serializable{
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="creation_timestamp")
    private Date timestamp;
    private int total;
    @Temporal(TemporalType.DATE)
    @Column(name="start_date")
    private Date startDate;
    /*@Temporal(TemporalType.DATE)
    @Column(name="end_date")
    private Date endDate;*/
    private int status;
    @ManyToOne
    @JoinColumn(name="consumer_id")
    private Consumer consumer;
    @ManyToOne
    @JoinColumn(name = "vp_id")
    private ValidityPeriod validityPeriod;
    @ManyToOne
    @JoinColumn(name = "sp_id")
    private ServicePackage servicePackage;
    @OneToMany(fetch= FetchType.EAGER, mappedBy="order", cascade= CascadeType.ALL)
    private List<OrderOptionalProducts> orderOptProds; 

    public List<OrderOptionalProducts> getOrderOptProds() {
		return orderOptProds;
	}

	public void setOrderOptProds(List<OrderOptionalProducts> orderOptProds) {
		this.orderOptProds = orderOptProds;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    /*public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }*/

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    public ValidityPeriod getValidityPeriod() {
        return validityPeriod;
    }
   
    
    public void setValidityPeriod(ValidityPeriod validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public ServicePackage getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(ServicePackage servicePackage) {
        this.servicePackage = servicePackage;
    }
}
