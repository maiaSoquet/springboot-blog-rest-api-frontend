package com.maia.controllers;

import com.maia.models.Post;
import com.maia.services.PostService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetPostByIdController {

    @FXML
    private TextField postIdTextField;

    @FXML
    private TextField postTitleTextField;

    @FXML
    private TextField postDescriptionTextField;

    @FXML
    private TextField postContentTextField;

    public void getPostById() {
        Long postId = Long.parseLong(postIdTextField.getText());

        try {
            PostService postService = new PostService();
            Post post = postService.getPostById(postId);


            postTitleTextField.setText(post.getTitle());
            postDescriptionTextField.setText(String.valueOf(post.getDescription()));
            postContentTextField.setText(String.valueOf(post.getContent()));
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error while fetching Post");
            alert.setHeaderText("the post with the given code does not exist");
            alert.setContentText("oops, there is no post with the ID : "+postIdTextField.getText());

            alert.showAndWait();
        }
    }

}
