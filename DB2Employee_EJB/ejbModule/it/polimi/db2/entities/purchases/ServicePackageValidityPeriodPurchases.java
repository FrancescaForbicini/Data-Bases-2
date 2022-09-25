package it.polimi.db2.entities.purchases;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import it.polimi.db2.entities.ServicePackage;
import it.polimi.db2.entities.ValidityPeriod;

@Entity
@Table(name = "package_validityperiod_purchases", schema = "db_test")
@NamedQuery(name = "ServicePackageValidityPeriodPurchases.getAll", query = "SELECT spvp FROM ServicePackageValidityPeriodPurchases spvp")
public class ServicePackageValidityPeriodPurchases implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "sp_id")
	private ServicePackage servicePackage;
	
	@ManyToOne
	@JoinColumn(name = "vp_id")
	private ValidityPeriod validityPeriod;
	
	@Column(name="purchases")
	private int purchases;
	
	public void setServicePackage(ServicePackage sp) {
		this.servicePackage = sp;
	}
	
	public ServicePackage getServicePackage() {
		return this.servicePackage;
	}
	
	public void setValidityPeriod(ValidityPeriod vp) {
		this.validityPeriod = vp;
	}
	
	public ValidityPeriod getValidityPeriod() {
		return this.validityPeriod;
	}
	
	public void setPurchases(int amount) {
		this.purchases = amount;
	}

	public int getPurchases() {
		return this.purchases;
	}
	

}
