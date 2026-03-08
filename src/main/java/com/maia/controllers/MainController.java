package com.maia.controllers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maia.models.Login;
import com.maia.models.Post;
import com.maia.services.AuthService;
import com.maia.services.PostService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class MainController {
    @FXML
    private Label welcomeText;

    @FXML
    private TableView<Post> postTable;

    @FXML
    private TableColumn<Post, Long> idColumn;

    @FXML
    private TableColumn<Post, String> titleColumn;

    @FXML
    private TableColumn<Post, String> descriptionColumn;

    @FXML
    private TableColumn<Post, String> contentColumn;

    @FXML
    private TableColumn <Post, List> commentsColumn;

    @FXML
    private TableColumn<Post, Long> categoryIdColumn;


    @FXML
    private void onHelloButtonClick(ActionEvent event) {
        welcomeText.setText("Welcome to JavaFX Application!");

        try {

            PostService postService = new PostService();
            List<Post> posts = postService.getAllPosts();

            ObservableList<Post> postsList =
                    FXCollections.observableArrayList(posts);

            postTable.setItems(postsList);

        } catch (Exception e) {
            e.printStackTrace();
            welcomeText.setText("Error fetching posts data!");
        }
    }


    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        contentColumn.setCellValueFactory(new PropertyValueFactory<>("content"));
        commentsColumn.setCellValueFactory(new PropertyValueFactory<>("comments"));
        categoryIdColumn.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
    }

}