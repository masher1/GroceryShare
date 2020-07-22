package com.example.groceryshare;

public class newBuyerCreds {
    String buyerID, email, firstName, lastName, address, phoneNumber, birthday, disabilities;

    public newBuyerCreds(String buyerID, String email, String firstName, String lastName, String address, String phoneNumber, String birthday, String disabilities) {
        this.buyerID = buyerID;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.disabilities = disabilities;
    }

    public String getBuyerID() {
        return buyerID;
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
    public String getDisabilities() {
        return disabilities;
    }
    public newBuyerCreds(){
    }
}
