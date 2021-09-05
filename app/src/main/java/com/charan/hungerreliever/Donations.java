package com.charan.hungerreliever;

public class Donations {
    private String food_name;
    private String user_email;

    public Donations(String food_name, String user_email) {
        this.food_name = food_name;
        this.user_email = user_email;
    }

    public Donations() {
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }
}
