package com.example.alimentoapp;

public class RestaurantFoodData {

    String hotelname, hoteladdress;

    public RestaurantFoodData(){}

    public RestaurantFoodData(String hotelname, String hoteladdress) {

        this.hotelname = hotelname;
        this.hoteladdress = hoteladdress;
    }


    public String getHotelname() {
        return hotelname;
    }

    public void setHotelname(String hotelname) {
        this.hotelname = hotelname;
    }

    public String getHoteladdress() { return hoteladdress;}

    public void setHoteladdress(String hoteladdress) { this.hoteladdress = hoteladdress; }

}
