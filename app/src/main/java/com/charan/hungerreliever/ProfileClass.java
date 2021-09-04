package com.charan.hungerreliever;

public class ProfileClass {
    private String name;
    private String email;
    private String phone;
    private String user;
    private String formSubmitted;
    private String verificationStatus;


    public ProfileClass() {
    }

    public ProfileClass(String name, String email, String phone, String user, String formSubmitted, String verificationStatus) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.user = user;
        this.formSubmitted = formSubmitted;
        this.verificationStatus = verificationStatus;
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

    public String getFormSubmitted() {
        return formSubmitted;
    }

    public void setFormSubmitted(String formSubmitted) {
        this.formSubmitted = formSubmitted;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }
}
