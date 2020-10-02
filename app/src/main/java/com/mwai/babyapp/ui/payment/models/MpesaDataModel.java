package com.mwai.babyapp.ui.payment.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MpesaDataModel {
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("amount")
    @Expose
    private String amount;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
