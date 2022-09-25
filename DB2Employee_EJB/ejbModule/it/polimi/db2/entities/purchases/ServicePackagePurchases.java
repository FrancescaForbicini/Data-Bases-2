package it.polimi.db2.entities.purchases;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import it.polimi.db2.entities.ServicePackage;

@Entity
@Table(name = "package_purchases", schema = "db_test")
@NamedQuery(name = "ServicePackagePurchases.getAll", query = "SELECT spp FROM ServicePackagePurchases spp")
public class ServicePackagePurchases implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sp_id")
	private int sp_id;
	
	@OneToOne
	@PrimaryKeyJoinColumn(name ="sp_id")
	private ServicePackage pack;
	@Column(name="total_purchases")
	private int totalPurchases;
	@Column(name="total_sales_op")
	private int totalSalesOp;
	@Column(name="total_sales_not_op")
	private int totalSalesNotOp;
	@Column(name="average_op")
	private float averageOp;
	
	public void setId(ServicePackage sp) {
		this.sp_id=sp.getId();
		this.pack = sp;
	}
	
	public ServicePackage getId() {
		return this.pack;
	}
	
	public void setTotalPurchases(int totalPurchases) {
		this.totalPurchases = totalPurchases;
	}
	
	public int getTotalPurchases() {
		return this.totalPurchases;
	}
	
	public void setTotalSalesOp(int totalSalesOp) {
		this.totalSalesOp = totalSalesOp;
	}
	
	public int getTotalSalesOp() {
		return this.totalSalesOp;
	}
	
	public void setTotalSalesNotOp(int totalSalesNotOp) {
		this.totalSalesNotOp = totalSalesNotOp;
	}
	
	public int getTotalSalesNotOp() {
		return this.totalSalesNotOp;
	}
	
	public void setAverageOp(float averageOp) {
		this.averageOp = averageOp;
	}
	
	public float getAverageOp() {
		return this.averageOp;
	}

	public ServicePackage getPack() {
		return pack;
	}

	public void setPack(ServicePackage pack) {
		this.pack = pack;
	}

}
