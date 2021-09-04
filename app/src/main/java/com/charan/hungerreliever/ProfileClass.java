package com.charan.hungerreliever;

public class ProfileClass {
    private String name;
    private String email;
    private String phone;
    private String user;


    public ProfileClass() {
    }

    public ProfileClass(String name, String email, String phone, String user) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.user = user;

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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

}
