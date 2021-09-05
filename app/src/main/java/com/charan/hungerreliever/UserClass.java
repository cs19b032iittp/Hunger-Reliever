package com.charan.hungerreliever;

public class UserClass {
    private String name;
    private String email;
    private String phone;
    private int foodDonations;

    public UserClass() {
    }

    public UserClass(String name, String email, String phone, int foodDonations) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.foodDonations = foodDonations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getFoodDonations() {
        return foodDonations;
    }

    public void setFoodDonations(int foodDonations) {
        this.foodDonations = foodDonations;
    }
}
