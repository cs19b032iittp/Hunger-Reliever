package com.charan.hungerreliever;

public class OrganisationClass {
    private String name;
    private String email;
    private String phone;
    private boolean verificationStatus;
    private  boolean formSubmitted;

    public OrganisationClass(String name, String email, String phone,boolean  formSubmitted, boolean verificationStatus) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.verificationStatus = verificationStatus;
        this.formSubmitted =formSubmitted;
    }

    public boolean isFormSubmitted() {
        return formSubmitted;
    }

    public void setFormSubmitted(boolean formSubmitted) {
        this.formSubmitted = formSubmitted;
    }

    public OrganisationClass() {

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

    public boolean isVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(boolean verificationStatus) {
        this.verificationStatus = verificationStatus;
    }
}
