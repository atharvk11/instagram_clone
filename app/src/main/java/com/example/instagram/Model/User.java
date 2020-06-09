package com.example.instagram.Model;

public class User {
    private String name;
    private String username;
    private String email;
    private String bio;
    private String imageurl;
    private String id;

    public User(String name, String username, String email, String bio, String imageurl, String id) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.bio = bio;
        this.imageurl = imageurl;
        this.id = id;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
