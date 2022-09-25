package it.polimi.db2.entities;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "optional_product", schema = "db_test")
@NamedQuery(name = "OptionalProduct.findAll", query = "SELECT op FROM OptionalProduct op")

public class OptionalProduct implements Serializable{
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int fee;

    
    
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

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }
}

