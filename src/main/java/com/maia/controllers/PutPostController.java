package com.maia.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maia.Main;
import com.maia.models.Post;
import com.maia.models.Token;
import com.maia.services.PostService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PutPostController {


    @FXML
    private TextField postTitle;
    @FXML
    private TextField description;
    @FXML
    private TextField content;
    @FXML
    private TextField categoryId;

        private Post postToUpdate;

        public void initialize(){

        }

        public void sendPutRequest(){
            try {
                Post newPost = new Post();
                newPost.setId(postToUpdate.getId());
                newPost.setTitle(postTitle.getText());
                newPost.setDescription(description.getText());
                newPost.setContent(content.getText());
                newPost.setCategoryId(Long.parseLong(categoryId.getText()));

                PostService postService = new PostService();
                String token = Token.getToken();

                postService.updatePost(newPost, token);


                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success!");
                alert.setHeaderText("The post was successfully updated!");
                alert.setContentText(newPost.getTitle() + " successfully updated!");

                alert.showAndWait();

                MenuController controller = new MenuController();
                controller.openGetAllScreen();
            }
            catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("The post could not be updated!");
                alert.setContentText("Details: " + e.getMessage());
                alert.showAndWait();
            }
        }

        public void setPost(Post post){
        this.postToUpdate = post;
    }

        public void fillHardwareFields(){
            postTitle.setText(postToUpdate.getTitle());
            description.setText(postToUpdate.getDescription());
            content.setText(postToUpdate.getContent());
            categoryId.setText(String.valueOf(postToUpdate.getCategoryId()));
        }


}



