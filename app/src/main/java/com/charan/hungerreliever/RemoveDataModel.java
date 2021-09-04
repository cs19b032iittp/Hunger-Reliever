package com.charan.hungerreliever;

public class RemoveDataModel {
    String org, upi;

    public RemoveDataModel(String org, String upi) {
        this.org = org;
        this.upi = upi;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getUpi() {
        return upi;
    }

    public void setUpi(String upi) {
        this.upi = upi;
    }
}
