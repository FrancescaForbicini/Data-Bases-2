package it.polimi.db2.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: ServiceActivationSchedule
 *
 */
@Entity
@Table(name = "service_activation_schedule", schema = "db_test")
public class ServiceActivationSchedule implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
	@OneToOne
	@JoinColumn(name="order_id")
	private Order order;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "activation_date")
	private Date activation;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "deactivation_date")
	private Date deactivation;
	
	@ManyToMany
	@JoinTable(name="schedule_service", joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id"))
	private List<Service> services;
	
	@ManyToMany
	@JoinTable(name="schedule_optprod", joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "optprod_id"))
	private List<OptionalProduct> optionalProducts;
	
	public ServiceActivationSchedule() {
		super();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Date getActivation() {
		return activation;
	}

	public void setActivation(Date activation) {
		this.activation = activation;
	}

	public Date getDeactivation() {
		return deactivation;
	}

	public void setDeactivation(Date deactivation) {
		this.deactivation = deactivation;
	}

	public List<Service> getServices() {
		return services;
	}

	public void setServices(List<Service> services) {
		this.services = services;
	}

	public List<OptionalProduct> getOptionalProducts() {
		return optionalProducts;
	}

	public void setOptionalProducts(List<OptionalProduct> optionalProducts) {
		this.optionalProducts = optionalProducts;
	}
   
}
