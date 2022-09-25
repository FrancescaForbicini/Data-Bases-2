package it.polimi.db2.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class MobileInternet extends Service implements Serializable{
	private static final long serialVersionUID = 1L;
	@Column(name="nr_gb")
    private int numberGB;
	@Column(name="fee_extragb")
    private int feeExtraGB;

    @Override
    public void setNumberMinutes(int numberMinutes) {

    }

    @Override
    public void setNumberSMS(int numberSMS) {

    }

    @Override
    public void setFeeExtraMin(int feeExtraMin) {

    }

    @Override
    public void setFeeExtraSMS(int feeExtraSMS) {

    }

    @Override
    public int getNumberGB() {
        return this.numberGB;
    }

    @Override
    public void setNumberGB(int numberGB) {
        this.numberGB=numberGB;
    }

    @Override
    public int getFeeExtraGB() {
        return this.feeExtraGB;
    }

    @Override
    public void setFeeExtraGB(int feeExtraGB) {
        this.feeExtraGB=feeExtraGB;
    }
}

