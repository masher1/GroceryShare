package com.example.groceryshare;

public class buyerPref {

    String buyerID;
    String store;
    String payment;
    String rewards;
    String others;

    public buyerPref(String buyerID, String store, String payment, String rewards, String others) {
        this.buyerID = buyerID;
        this.store = store;
        this.payment = payment;
        this.rewards= rewards;
        this.others = others;
    }

  public String getBuyerID() {
        return buyerID;
    }

    public String getStore() {
        return store;
    }

    public String getPayment() {
        return payment;
    }

    public String getRewards() {
        return rewards;
    }

    public String getOthers() {
        return others;
    }

}