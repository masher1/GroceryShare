package com.example.groceryshare;

public class newShopperCreds {
    String shopperID;
    String username, profilePhoto, email, password, firstName, lastName, address, phoneNumber, birthday, frequency;

    public newShopperCreds(String shopperID, String profilePhoto, String username, String email, String password, String firstName, String lastName, String address, String phoneNumber, String birthday, String frequency) {
        this.shopperID = shopperID;
        this.profilePhoto = profilePhoto;
        this.username = username;
        this.email = email;
        this.password = password;
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
    public String getProfilePhoto() {
        return profilePhoto;
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
    public String getFrequency() {
        return frequency;
    }
    public newShopperCreds(){

    }
}
