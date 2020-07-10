package com.example.groceryshare;

public class UploadProfilePic {
    private String userID;
    private String ImageUrl;
    public UploadProfilePic() {

    }
    public UploadProfilePic(String userIDName, String imageUrl) {
        userID = userIDName;
        ImageUrl = imageUrl;
    }
    public String getName() {
        return userID;
    }
    public void setName(String name) {
        userID = name;
    }
    public String getImageUrl() {
        return ImageUrl;
    }
    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}