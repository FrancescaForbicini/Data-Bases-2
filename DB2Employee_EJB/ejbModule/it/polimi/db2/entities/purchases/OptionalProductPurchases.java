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

import it.polimi.db2.entities.OptionalProduct;

@Entity
@Table(name = "op_purchases", schema = "db_test")
@NamedQuery(name = "OptionalProductPurchases.getMax", query = "SELECT opp FROM OptionalProductPurchases opp WHERE opp.opSales= (SELECT MAX(opp1.opSales) FROM OptionalProductPurchases opp1)")
public class OptionalProductPurchases implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="op_id")
	private int id;
	
	@OneToOne
	@PrimaryKeyJoinColumn(name ="op_id")
	private OptionalProduct optionalProduct;
	@Column(name="op_sales")
	private int opSales;
	
	
	public void setId (int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setOpSales(int sales) {
		this.opSales = sales;
	}
	
	public int getOpSales() {
		return this.opSales;
	}

	public OptionalProduct getOptionalProduct() {
		return this.optionalProduct;
	}
}
	
