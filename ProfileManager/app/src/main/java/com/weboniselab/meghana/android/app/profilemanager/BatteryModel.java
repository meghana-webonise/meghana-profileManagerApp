package com.weboniselab.meghana.android.app.profilemanager;

/**
 * Created by webonise on 10/9/15.
 */
public class BatteryModel {
    int id;
    String batteryLevel,modeOfPhoneBattery,modeOfNetwork;

    public BatteryModel(){
        this.id=0;
        this.batteryLevel="";
        this.modeOfPhoneBattery="";
        this.modeOfNetwork="";

    }
    public BatteryModel(int id,String batteryLevel,String modeOfPhoneBattery,String modeOfmodeOfNetworkPhone){
        this.id=id;
        this.batteryLevel=batteryLevel;
        this.modeOfPhoneBattery=modeOfPhoneBattery;
        this.modeOfNetwork=modeOfNetwork;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(String batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public String getModeOfPhoneBattery() {
        return modeOfPhoneBattery;
    }

    public void setModeOfPhoneBattery(String modeOfPhoneBattery) {
        this.modeOfPhoneBattery = modeOfPhoneBattery;
    }

    public String getModeOfNetwork() {
        return modeOfNetwork;
    }

    public void setModeOfNetwork(String modeOfNetwork) {
        this.modeOfNetwork = modeOfNetwork;
    }
}
