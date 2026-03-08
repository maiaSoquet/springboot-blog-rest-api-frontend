package com.maia.models;

public class Comment {
    private Long id;
    private String name;
    private String email;
    private String body;
    private Post post;

    public Comment() {
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getBody() {return body;}
    public void setBody(String body) {this.body = body;}

    public Post getPost() {return post;}
    public void setPost(Post post) {this.post = post;}
}
