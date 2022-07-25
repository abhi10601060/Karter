package com.example.karter;

public class User {
    private String name;
    private String email;
    private String phone_No;
    private String id;

    public User(String name, String email, String phone_No, String id) {
        this.name = name;
        this.email = email;
        this.phone_No = phone_No;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone_No() {
        return phone_No;
    }

    public String getId() {
        return id;
    }
}
