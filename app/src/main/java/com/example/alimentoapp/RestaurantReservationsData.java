package com.example.alimentoapp;

public class RestaurantReservationsData {

    String hotelname;

    public RestaurantReservationsData(){}

    public RestaurantReservationsData(String hotelname) {
        this.hotelname = hotelname;
    }

    public String getHotelname() {
        return hotelname;
    }

    public void setHotelname(String hotelname) {
        this.hotelname = hotelname;
    }
}
