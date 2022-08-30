package com.jay.jphsql.models;

import javax.persistence.*;

@Entity
@Table(name = "User")
public class UserModel {

    @Id
    private int id;
    @GeneratedValue(strategy = GenerationType.AUTO)

    private String name;
    private String username;
    private String email;
    private String phone;
    private String website;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }
}
