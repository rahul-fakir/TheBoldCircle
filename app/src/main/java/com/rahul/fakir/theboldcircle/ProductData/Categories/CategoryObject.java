package com.rahul.fakir.theboldcircle.ProductData.Categories;

/**
 * Created by rahul.fakir on 2016/05/16.
 */
public class CategoryObject {
    private String category;

    public CategoryObject(){

    }

    public CategoryObject(String category, String description){
        this.category = category;

    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory()
    {
        return category;
    }

}
