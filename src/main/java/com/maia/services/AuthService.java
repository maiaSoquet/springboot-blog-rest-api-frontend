package com.maia.services;

import com.maia.models.Login;
import com.maia.models.Register;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AuthService {
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();


    public String login (Login loginData) throws IOException, InterruptedException {
        String jsonBody = mapper.writeValueAsString(loginData);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/auth/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return mapper.readTree(response.body()).get("accessToken").asText();
        } else {
            throw new RuntimeException("Échec d'authentification : " + response.statusCode());
        }
    }

    public String register (Register userData) throws IOException, InterruptedException {
        String jsonBody = mapper.writeValueAsString(userData);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/auth/register"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200 || response.statusCode() == 201) {
            return ("new user created successfully");
        } else {
            throw new RuntimeException("Échec d'authentification : " + response.statusCode());
        }

    }

}
