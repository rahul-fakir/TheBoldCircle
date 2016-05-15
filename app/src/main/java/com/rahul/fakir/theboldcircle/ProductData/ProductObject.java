package com.rahul.fakir.theboldcircle.ProductData;

/**
 * Created by rahul.fakir on 2016/05/13.
 */
public class ProductObject {
    private String name, description, type, ID;
    private double price;

    public ProductObject(String ID, String name, String description, String type, double price) {
        this.ID = ID;
        this.name = name;
        this.type = type;
        this.price = price;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


}
