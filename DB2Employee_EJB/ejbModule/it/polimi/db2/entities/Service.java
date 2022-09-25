package it.polimi.db2.entities;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "service", schema = "db_test")
//to declare the upper class
@Inheritance
@DiscriminatorColumn(name = "s_type")
@NamedQuery(name = "Service.findAll",
			query = "SELECT s FROM Service s")
public abstract class Service implements Serializable{
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="s_type")
    private String serviceType;
    @Column(name="nr_gb")
    private int numberGB;
	@Column(name="fee_extragb")
    private int feeExtraGB;
	@Column(name="nr_min")
    private int numberMinutes;
	@Column(name="nr_sms")
    private int numberSMS;
	@Column(name="fee_extramin")
    private int feeExtraMin;
	@Column(name="fee_extrasms")
    private int feeExtraSMS;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setServiceType(String serviceType){ this.serviceType = serviceType;}

    public String getServiceType() {
        return serviceType;
    }

    public int getNumberMinutes() {return 0;}

    public abstract void setNumberMinutes(int numberMinutes);

    public int getNumberSMS() {return 0;}

    public abstract void setNumberSMS(int numberSMS);

    public int getFeeExtraMin() {
        return 0;
    }

    public abstract void setFeeExtraMin(int feeExtraMin);

    public int getFeeExtraSMS() {return 0;}

    public abstract void setFeeExtraSMS(int feeExtraSMS);

    public int getNumberGB() {return 0;}

    public abstract void setNumberGB(int numberGB);

    public int getFeeExtraGB() {
        return 0;
    }

    public abstract void setFeeExtraGB(int feeExtraGB);
}

