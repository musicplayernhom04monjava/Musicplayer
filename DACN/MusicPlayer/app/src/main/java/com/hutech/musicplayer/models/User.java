package com.hutech.musicplayer.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

//    @SerializedName("id")
//    @Expose
//    private String id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("fullname")
    @Expose
    private String fullname;
//    @SerializedName("gender")
//    @Expose
//    private Boolean gender;
//    @SerializedName("username")
//    @Expose
//    private String username;
    @SerializedName("password")
    @Expose
    private String password;

    public User(){}
//    public User(String id, String email, String fullname, Boolean gender, String username, String password){
//        this.id = id;
//        this.email = email;
//        this.fullname = fullname;
//        this.gender = gender;
//        this.username = username;
//        this.password = password;
//    }
    public User(String email, String fullname, String password){
        this.email = email;
        this.fullname = fullname;
        this.password = password;
    }
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

//    public String Gender() {
//        if(this.gender == true) return "Nam";
//        else return "Nữ";
//        //return gender;
//    }
//    public Boolean getGender() {
//        return gender;
//    }
//
//    public void setGender(Boolean gender) {
//        this.gender = gender;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
