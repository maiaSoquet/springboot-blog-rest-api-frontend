package com.maia.services;


import com.maia.models.Login;
import com.maia.models.Post;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class PostService {
    private final  HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public List<Post> getAllPosts () throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/posts"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            JsonNode node = mapper.readTree(response.body()).get("content");
            return mapper.readerForListOf(Post.class).readValue(node);
        } else {
            throw new RuntimeException("no post found : " + response.statusCode());
        }
    }



    public List<Post> getPostsByCategory (Long id_category) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/posts/category/"+id_category.toString()))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return mapper.readerForListOf(Post.class).readValue(response.body());
        } else {
            throw new RuntimeException("no post found : " + response.statusCode());
        }
    }



    public Post getPostById (Long id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/posts/"+id.toString()))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return mapper.readValue(response.body(), Post.class);
        } else {
            throw new RuntimeException("post not found : " + response.statusCode());
        }

    }


    public String createPost (Post post, String token) throws IOException, InterruptedException {
        String jsonBody = mapper.writeValueAsString(post);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/posts"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200||response.statusCode() == 201) {
            return "post created successfully";
        } else if (response.statusCode() == 401) {
            throw new RuntimeException("forbidden access" + response.body());
        } else {
            throw new RuntimeException("post creation failed : " + response.statusCode()+ " - " + response.body());
        }

    }

    public String updatePost (Post post, String token) throws IOException, InterruptedException {
        String jsonBody = mapper.writeValueAsString(post);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/posts/"+post.getId().toString()))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200||response.statusCode() == 201) {
            return "post updated successfully";
        } else if (response.statusCode() == 401) {
            throw new RuntimeException("forbidden access" + response.body());
        } else {
            throw new RuntimeException("post update failed : " + response.statusCode()+ " - " + response.body());
        }
    }


    public String deletePost (Post post, String token) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/posts/"+post.getId().toString()))
                .header("Authorization", "Bearer " + token)
                .DELETE()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200||response.statusCode() == 201) {
            return "post deleted successfully";
        } else if (response.statusCode() == 401) {
            throw new RuntimeException("forbidden access" + response.body());
        } else {
            throw new RuntimeException("post deletion failed : " + response.statusCode()+ " - " + response.body());
        }
    }

}
