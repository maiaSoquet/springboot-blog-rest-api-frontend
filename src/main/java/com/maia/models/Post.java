package com.maia.models;

import java.util.Set;

public class Post {
    private Long id;
    private String title;
    private String description;
    private String content;
    private Set<Comment> comments;
    private Long categoryId;

    public Post () {}

    public Post(Long id, String title, String description, String content, Set<Comment> comments, Long categoryId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
        this.comments = comments;
        this.categoryId = categoryId;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public String getContent() {return content;}
    public void setContent(String content) {this.content = content;}

    public Set<Comment> getComments() {return comments;}
    public void setComments(Set<Comment> comments) {this.comments = comments;}

    public Long getCategoryId() {return categoryId;}
    public void setCategoryId(Long categoryId) {this.categoryId = categoryId;}
}
