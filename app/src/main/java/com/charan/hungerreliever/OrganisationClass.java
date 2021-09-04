package com.charan.hungerreliever;

public class OrganisationClass {
    private String name;
    private String email;
    private String phone;
    private String verificationStatus;
    private  String formSubmitted;


    public OrganisationClass() {

    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public String getFormSubmitted() {
        return formSubmitted;
    }

    public void setFormSubmitted(String formSubmitted) {
        this.formSubmitted = formSubmitted;
    }

    public OrganisationClass(String name, String email, String phone, String verificationStatus, String formSubmitted) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.verificationStatus = verificationStatus;
        this.formSubmitted = formSubmitted;
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




}
