package com.example.groceryshare;

public class newBuyerCreds {
    String buyerID;
    String username;
    String email;
    String password;
    String firstName, lastName, address, phoneNumber, birthday;

    public newBuyerCreds(String buyerID, String username, String email, String password, String firstName, String lastName, String address, String phoneNumber, String birthday) {
        this.buyerID = buyerID;
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
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
    public String getAddress() {
        return address;
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
