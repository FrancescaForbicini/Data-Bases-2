package it.polimi.db2.utils;

import java.io.Serializable;
import java.util.*;
import java.util.Date;

import it.polimi.db2.entities.OptionalProduct;
import it.polimi.db2.entities.ServicePackage;
import it.polimi.db2.entities.ValidityPeriod;

public class Subscription implements Serializable{
	private static final long serialVersionUID = 1L;
    private int id;
    private Date activation;
    private Date deactivation;
    private ValidityPeriod validityPeriod;
    private ServicePackage servicePackage;
    private ArrayList<OptionalProduct> optionalProducts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public ValidityPeriod getValidityPeriod() {
        return validityPeriod;
    }
    
    public int getTotalPrice(){
        return ((validityPeriod.getFee()+ optionalProducts.stream().mapToInt(OptionalProduct::getFee).sum()))* validityPeriod.getMonths();
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

    public ArrayList<OptionalProduct> getOptionalProducts() {
        return optionalProducts;
    }

    public void setOptionalProducts(ArrayList<OptionalProduct> optionalProducts) {
        this.optionalProducts = optionalProducts;
    }
}
