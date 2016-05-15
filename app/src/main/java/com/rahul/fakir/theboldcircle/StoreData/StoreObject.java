package com.rahul.fakir.theboldcircle.StoreData;

/**
 * Created by rahul.fakir on 2016/05/12.
 */
public class StoreObject {
    private String ID, addressLocation;

    public StoreObject(String ID,  String addressLocation){
        this.ID = ID;
        this.addressLocation = addressLocation;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String addressLocation() {
        return addressLocation;
    }

    public void setAddress(String addressLocation) {
        this.addressLocation = addressLocation;
    }
}
