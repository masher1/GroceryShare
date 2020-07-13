package com.example.groceryshare;

public class newBuyerCreds {
    String buyerID;
    String profilePhoto;
    String username;
    String email;
    String firstName, lastName, address, phoneNumber, birthday, disabilities;

    public newBuyerCreds(String buyerID, String profilePhoto, String username, String email, String firstName, String lastName, String address, String phoneNumber, String birthday, String disabilities) {
        this.buyerID = buyerID;
        this.profilePhoto = profilePhoto;
        this.username = username;
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
    public String getProfilePhoto() {
        return profilePhoto;
    }
    public String getUsername() {
        return username;
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
