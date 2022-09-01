package com.jay.jphsql.models;

import javax.persistence.*;

@Entity
@Table(name = "Comment")
public class CommentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private int id;

    private int postId;

    private String name;
    private String email;
    private String body;

    public int getId() {
        return id;
    }

    public int getPostId() {
        return postId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getBody() {
        return body;
    }
}
