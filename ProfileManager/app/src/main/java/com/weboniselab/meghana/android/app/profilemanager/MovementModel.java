package com.weboniselab.meghana.android.app.profilemanager;

/**
 * Created by webonise on 3/9/15.
 */
public class MovementModel {
    int id;
    String modeOfMovement,modeOfPhone;
    public MovementModel(){
        this.id=0;
        this.modeOfMovement="";
        this.modeOfPhone="";
    }

    public MovementModel(int id, String modeOfMovement, String modeOfPhone) {
        this.id = id;
        this.modeOfMovement = modeOfMovement;
        this.modeOfPhone = modeOfPhone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModeOfMovement() {
        return modeOfMovement;
    }

    public void setModeOfMovement(String modeOfMovement) {
        this.modeOfMovement = modeOfMovement;
    }

    public String getModeOfPhone() {
        return modeOfPhone;
    }

    public void setModeOfPhone(String modeOfPhone) {
        this.modeOfPhone = modeOfPhone;
    }
}
