package com.charan.hungerreliever;

// FoodDonorDetails Class
public class FoodDonorDetails {

    private String email,quantity,  city;

    public FoodDonorDetails(String food, String quantity, String city) {
        this.email = food;
        this.quantity = quantity;
        this.city = city;
    }

    public FoodDonorDetails() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
