package com.example.adarsh.experentals;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ADARSH on 20/02/2018.
 */

public class Advert implements Parcelable {

    public String itemName,category,description,monthlyRent,location;
    public String image, renterId, latitude, longitude, address;
    public String bid, id, bidder;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(itemName);
        parcel.writeString(address);
        parcel.writeString(category);
        parcel.writeString(description);
        parcel.writeString(monthlyRent);
        parcel.writeString(location);
        parcel.writeString(image);
        parcel.writeString(renterId);
        parcel.writeString(latitude);
        parcel.writeString(longitude);
        parcel.writeString(bid);
        parcel.writeString(id);

    }


    public static final Parcelable.Creator<Advert> CREATOR
            = new Parcelable.Creator<Advert>() {
        public Advert createFromParcel(Parcel in) {
            return new Advert(in);
        }
        public Advert[] newArray(int size) {
            return new Advert[size];
        }
    };



    public Advert(Parcel in) {

        itemName = in.readString();
        address = in.readString();
        category = in.readString();
        description = in.readString();
        monthlyRent = in.readString();
        location = in.readString();
        image = in.readString();
        renterId = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        bid = in.readString();
        id = in.readString();

    }



    public Advert(){

    }


    public Advert(String itemName, String category, String description, String monthlyRent,
                  String location, String image, String renterId, String Latitude,
                  String Longitude, String address, String id){

        this.itemName=itemName;
        this.category=category;
        this.description=description;
        this.monthlyRent=monthlyRent;
        this.location=location;
        this.image = image;
        this.renterId = renterId;
        this.latitude = Latitude;
        this.longitude = Longitude;
        this.address = address;
        this.bid = monthlyRent;
        this.id = id;
        this.bidder = "NULL";

    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }

    public String getImage() {
        return image;
    }

    public String getRenterId() {
        return renterId;
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
