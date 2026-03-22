package com.maia.controllers;


import com.maia.models.Post;
import com.maia.models.Token;
import com.maia.services.PostService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.List;


    public class PostPostController {

        @FXML
        private TextField postTitle;
        @FXML
        private TextField description;
        @FXML
        private TextField content;
        @FXML
        private TextField categoryId;

        @FXML
        private Label statusLabel;

        public void sendPostRequest() {
            statusLabel.setText("");

            if (categoryId.getText().isBlank() || !categoryId.getText().matches("\\d+")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("you must fill the categoryId by a number" );
                alert.showAndWait();
                return;
            }

            Post newPost = new Post();
            newPost.setTitle(postTitle.getText());
            newPost.setDescription(description.getText());
            newPost.setContent(content.getText());
            newPost.setCategoryId(Long.parseLong(categoryId.getText()));

            PostService postService = new PostService();

            String token = Token.getToken();

            try {

                String postArrayResponse = postService.createPost(newPost, token);
                statusLabel.setText("created successfully");
                statusLabel.setStyle("-fx-text-fill: green;");

            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error while creating Post" );
                alert.setContentText(e.getMessage());

                alert.showAndWait();
            }


        }

    }


