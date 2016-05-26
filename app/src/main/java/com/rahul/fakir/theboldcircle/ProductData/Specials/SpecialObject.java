package com.rahul.fakir.theboldcircle.ProductData.Specials;

import com.rahul.fakir.theboldcircle.ProductData.Products.ProductObject;

/**
 * Created by rahul.fakir on 2016/05/21.
 */
public class SpecialObject {
    private String id, name, description, productSKU, type;
    private ProductObject product;
    public SpecialObject(){

    }

    public SpecialObject(String id, String name, String description, String productSKU, String type, ProductObject product){
       this.id = id;
        this.name = name;
        this.description = description;
        this.productSKU = productSKU;
        this.type = type;
        this.product = product;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getID(){
        return id;
    }

    public void setID(String id){
        this.id = id;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getProductSKU(){
        return productSKU;
    }

    public void setProductSKU(String productSKU){
        this.productSKU = productSKU;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public ProductObject getProduct(){
        return product;
    }

    public void setProduct(ProductObject product){
        this.product = product;
    }
}
