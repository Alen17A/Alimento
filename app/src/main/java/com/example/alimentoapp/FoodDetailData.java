package com.example.alimentoapp;

public class FoodDetailData {

    String foodname, foodprice, foodim;

    public FoodDetailData(){}

    public FoodDetailData(String foodname, String foodprice, String foodim) {
        this.foodname = foodname;
        this.foodprice = foodprice;
        this.foodim = foodim;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public String getFoodprice() {
        return foodprice;
    }

    public void setFoodprice(String foodprice) {
        this.foodprice = foodprice;
    }

    public String getFoodim() {
        return foodim;
    }

    public void setFoodim(String foodim) {
        this.foodim = foodim;
    }
}
