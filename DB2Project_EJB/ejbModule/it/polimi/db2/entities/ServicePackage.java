package it.polimi.db2.entities;

import javax.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "service_package", schema = "db_test")
@NamedQuery(name = "ServicePackage.findAll", query = "SELECT sp FROM ServicePackage sp")
public class ServicePackage implements Serializable{
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="servicepack_service", joinColumns =@JoinColumn(name="sp_id"), inverseJoinColumns = @JoinColumn(name="service_id"))
    private List<Service> services;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="servicepack_optprod", joinColumns = @JoinColumn(name="sp_id"), inverseJoinColumns = @JoinColumn(name = "optprod_id"))
    private List<OptionalProduct> optionalProducts;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name ="servicepack_validityperiod", joinColumns= @JoinColumn(name="sp_id"), inverseJoinColumns= @JoinColumn(name="vp_id"))
    private List<ValidityPeriod> validityPeriods;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    
    public List<ValidityPeriod> getValidityPeriods() {
        return validityPeriods;
    }

    public void setValidityPeriods(List<ValidityPeriod> validityPeriods) {
        this.validityPeriods = validityPeriods;
    }
}

