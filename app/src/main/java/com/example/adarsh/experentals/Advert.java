package com.example.adarsh.experentals;

/**
 * Created by ADARSH on 20/02/2018.
 */

public class Advert {

    public String itemName,category,description,monthlyRent,location;

    public Advert(){

    }


    public Advert(String itemName, String category, String description, String monthlyRent, String location){
        this.itemName=itemName;
        this.category=category;
        this.description=description;
        this.monthlyRent=monthlyRent;
        this.location=location;
    }

    public String getItemName() {
        return itemName;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getMonthlyRent() {
        return monthlyRent;
    }

    public String getLocation() {
        return location;
    }
}
