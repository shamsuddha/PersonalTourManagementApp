package com.example.shamsuddha.tourmate;

public class ProfilePicture {


    private String id;
    private String userId;
    private String uri;

    public ProfilePicture(String id, String userId, String uri) {
        this.id = id;
        this.userId = userId;
        this.uri = uri;
    }

    public ProfilePicture(String userId, String uri) {
        this.userId = userId;
        this.uri = uri;
    }

    public ProfilePicture() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
