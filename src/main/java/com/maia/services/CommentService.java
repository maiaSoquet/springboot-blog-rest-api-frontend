package com.maia.services;

import com.maia.models.Category;
import com.maia.models.Comment;
import com.maia.models.Login;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class CommentService {
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public List<Comment> getAllComments (Long id_post) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/posts/"+id_post.toString()+"/comments"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return mapper.readerForListOf(Comment.class).readValue(response.body());
        } else if (response.statusCode() == 404){
            throw new RuntimeException("no comments found : " + response.statusCode());
        } else {
            throw new RuntimeException("error : " + response.statusCode());
        }
    }

    public Comment getCommentById (Long id_comment) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/posts/comments/"+id_comment.toString()))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return mapper.readValue(response.body(), Comment.class);
        } else if (response.statusCode() == 404){
            throw new RuntimeException("comment not found : " + response.statusCode());
        } else {
            throw new RuntimeException("error : " + response.statusCode());
        }
    }

    public String createComment (Comment comment, String token, Long id_post) throws IOException, InterruptedException {
        String jsonBody = mapper.writeValueAsString(comment);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/posts/"+id_post.toString()+"/comments"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200||response.statusCode() == 201) {
            return "comment created successfully";
        } else if (response.statusCode() == 401) {
            throw new RuntimeException("forbidden access" + response.body());
        } else {
            throw new RuntimeException("comment creation failed : " + response.statusCode()+ " - " + response.body());
        }
    }


    public String updateComment (Comment comment, String token, Long id_post) throws IOException, InterruptedException {
        String jsonBody = mapper.writeValueAsString(comment);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/posts/"+id_post.toString()+"/comments/"+comment.getId().toString()))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200||response.statusCode() == 201) {
            return "comment updated successfully";
        } else if (response.statusCode() == 401) {
            throw new RuntimeException("forbidden access" + response.body());
        } else {
            throw new RuntimeException("comment update failed : " + response.statusCode()+ " - " + response.body());
        }
    }


    public String deleteComment (Comment comment, String token, Long id_post) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/posts/"+id_post.toString()+"/comments/"+comment.getId().toString()))
                .header("Authorization", "Bearer " + token)
                .DELETE()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200||response.statusCode() == 201) {
            return "comment deleted successfully";
        } else if (response.statusCode() == 401) {
            throw new RuntimeException("forbidden access" + response.body());
        } else {
            throw new RuntimeException("comment deletion failed : " + response.statusCode()+ " - " + response.body());
        }
    }
}
