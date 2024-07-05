package com.example.myapplication.models;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("email")
    private String email;

    @SerializedName("name")
    private Name name;

    @SerializedName("phone")
    private String phone;

    public User(String username, String password, String email, Name name, String phone) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.phone = phone;
    }

    // Getters and Setters

    public static class Name {
        @SerializedName("firstname")
        private String firstname;

        @SerializedName("lastname")
        private String lastname;

        public Name(String firstname, String lastname) {
            this.firstname = firstname;
            this.lastname = lastname;
        }

        // Getters and Setters
    }
}
