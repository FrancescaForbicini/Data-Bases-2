package it.polimi.db2.entities;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class FixedPhone extends Service implements Serializable{
	private static final long serialVersionUID = 1L;

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
    public void setNumberGB(int numberGB) {

    }

    @Override
    public void setFeeExtraGB(int feeExtraGB) {

    }
}
