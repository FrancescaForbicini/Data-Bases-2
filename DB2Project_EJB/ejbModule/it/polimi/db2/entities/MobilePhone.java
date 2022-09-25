package it.polimi.db2.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class MobilePhone extends Service implements Serializable{
	private static final long serialVersionUID = 1L;
	@Column(name="nr_min")
    private int numberMinutes;
	@Column(name="nr_sms")
    private int numberSMS;
	@Column(name="fee_extramin")
    private int feeExtraMin;
	@Column(name="fee_extrasms")
    private int feeExtraSMS;

    @Override
    public int getNumberMinutes() {
        return this.numberMinutes;
    }

    @Override
    public void setNumberMinutes(int numberMinutes) {
        this.numberMinutes=numberMinutes;
    }

    @Override
    public int getNumberSMS() {
        return this.numberSMS;
    }

    @Override
    public void setNumberSMS(int numberSMS) {
        this.numberSMS=numberSMS;
    }

    @Override
    public int getFeeExtraMin() {
        return this.feeExtraMin;
    }

    @Override
    public void setFeeExtraMin(int feeExtraMin) {
        this.feeExtraMin=feeExtraMin;
    }

    @Override
    public int getFeeExtraSMS() {
        return this.feeExtraSMS;
    }

    @Override
    public void setFeeExtraSMS(int feeExtraSMS) {
        this.feeExtraSMS=feeExtraSMS;
    }

    @Override
    public void setNumberGB(int numberGB) {

    }

    @Override
    public void setFeeExtraGB(int feeExtraGB) {

    }
}

