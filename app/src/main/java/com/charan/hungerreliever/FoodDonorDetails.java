package com.charan.hungerreliever;

public class FoodDonorDetails {

    private String donor,mobile,  address;


    public FoodDonorDetails(String donor, String mobile, String address) {
        this.donor = donor;
        this.mobile = mobile;
        this.address = address;
    }

    public String getDonor() {
        return donor;
    }

    public void setDonor(String donor) {
        this.donor = donor;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
