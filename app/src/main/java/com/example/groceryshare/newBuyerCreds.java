package com.example.groceryshare;

public class newBuyerCreds {
    String buyerID;
    String username;
    String email;
    String password;
    String firstName, lastName, addressLine1, addressLine2, city, state, zipCode, phoneNumber, birthday;

    public newBuyerCreds(String buyerID, String username, String email, String password, String firstName, String lastName, String addressLine1, String addressLine2, String city, String state, String zipCode, String phoneNumber, String birthday) {
        this.buyerID = buyerID;
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
    }

    public String getBuyerID() {
        return buyerID;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getBirthday() {
        return birthday;
    }

    public newBuyerCreds(){

    }
}
