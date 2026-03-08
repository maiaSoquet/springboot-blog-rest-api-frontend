package com.maia.services;

import com.maia.models.Category;
import com.maia.models.Login;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class CategoryService {
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public List<Category> getAllCategories () throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/categories"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return mapper.readerForListOf(Category.class).readValue(response.body());
        } else if (response.statusCode() == 404){
            throw new RuntimeException("no categories found : " + response.statusCode());
        } else {
            throw new RuntimeException("error : " + response.statusCode());
        }
    }


    public Category getCategoryById (Long id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/categories/"+id.toString()))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return mapper.readValue(response.body(), Category.class);
        } else if (response.statusCode() == 404){
            throw new RuntimeException("category not found : " + response.statusCode());
        } else {
            throw new RuntimeException("error : " + response.statusCode());
        }
    }

    public String createCategory (Category category, String token) throws IOException, InterruptedException {
        String jsonBody = mapper.writeValueAsString(category);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/categories"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200||response.statusCode() == 201) {
            return "category created successfully";
        } else if (response.statusCode() == 401) {
            throw new RuntimeException("forbidden access" + response.body());
        } else {
            throw new RuntimeException("category creation failed : " + response.statusCode()+ " - " + response.body());
        }
    }


    public String updateCategory (Category category, String token) throws IOException, InterruptedException {
        String jsonBody = mapper.writeValueAsString(category);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/categories/"+category.getId().toString()))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200||response.statusCode() == 201) {
            return "category updated successfully";
        } else if (response.statusCode() == 401) {
            throw new RuntimeException("forbidden access" + response.body());
        } else {
            throw new RuntimeException("category update failed : " + response.statusCode()+ " - " + response.body());
        }
    }


    public String deleteCategory (Category category, String token) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/categories/"+category.getId().toString()))
                .header("Authorization", "Bearer " + token)
                .DELETE()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200||response.statusCode() == 201) {
            return "category deleted successfully";
        } else if (response.statusCode() == 401) {
            throw new RuntimeException("forbidden access" + response.body());
        } else {
            throw new RuntimeException("category deletion failed : " + response.statusCode()+ " - " + response.body());
        }
    }

}
