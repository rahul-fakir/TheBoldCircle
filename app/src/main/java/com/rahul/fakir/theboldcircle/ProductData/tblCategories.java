package com.rahul.fakir.theboldcircle.ProductData;

/**
 * Created by rahul.fakir on 2016/05/16.
 */
public class tblCategories {
    private String category, description;

    public tblCategories(){

    }

    public tblCategories(String category, String description){
        this.category = category;
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory()
    {
        return category;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
