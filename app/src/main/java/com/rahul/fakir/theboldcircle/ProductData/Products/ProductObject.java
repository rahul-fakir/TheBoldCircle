package com.rahul.fakir.theboldcircle.ProductData.Products;

import java.util.List;

/**
 * Created by rahul.fakir on 2016/05/13.
 */
public class ProductObject {
    private String name, description, type, sku, price, category, appointmentStore = "";
    private boolean selectedStatus = false, appointmentStatus = false;


  public ProductObject(){

  }
   public ProductObject(String sku, String name, String description, String type, String price, String category) {
        this.sku = sku;
        this.name = name;
        this.type = type;
        this.price = price;
        this.description = description;
       this.category = category;
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

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory( String category )
    {
        this.category = category;
    }

    public void setAppointmentSet(boolean status){
        selectedStatus = status;
    }

    public boolean getSelectedStatus(){
        return selectedStatus;
    }

    public void setSelectedStatus(boolean status){
        selectedStatus = status;
    }

    public void setAppointmentStatus(boolean status){
        appointmentStatus = status;
    }

    public boolean getAppointmentStatus(){
        return appointmentStatus;
    }

    public String getAppointmentStore(){
        return appointmentStore;
    }

    public void setAppointmentStore(String store) {
        appointmentStore = store;
    }



}
