package com.example.groceryshare;

public class newShopperCreds {
    String shopperID, email, password, firstName, lastName, address, phoneNumber, birthday, frequency;
    public newShopperCreds(String shopperID, String email, String firstName, String lastName, String address, String phoneNumber, String birthday, String frequency) {
        this.shopperID = shopperID;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.frequency = frequency;
    }
    public String getShopperID() {
        return shopperID;
    }
    public String getEmail() {
        return email;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getAddress() {
        return address;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getBirthday() {
        return birthday;
    }
    public String getFrequency() {
        return frequency;
    }
    public newShopperCreds(){

    }
}