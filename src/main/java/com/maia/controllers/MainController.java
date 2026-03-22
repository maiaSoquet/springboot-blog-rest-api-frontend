package com.maia.controllers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maia.Main;
import com.maia.models.Login;
import com.maia.models.Post;
import com.maia.models.Token;
import com.maia.services.AuthService;
import com.maia.services.PostService;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.io.IOException;
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



        postTable.setRowFactory(
                new Callback<TableView<Post>, TableRow<Post>>() {
                    @Override
                    public TableRow<Post> call(TableView<Post> tableView) {
                        final TableRow<Post> row = new TableRow<>();
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem editItem = new MenuItem("Edit");
                        editItem.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {

                                FXMLLoader loader = new FXMLLoader();
                                loader.setLocation(Main.class.getResource("views/putPost.fxml"));

                                Post post = row.getItem();

                                try {
                                    Parent parent = (AnchorPane) loader.load();
                                    Scene scene = new Scene(parent);
                                    Main.getCentralStage().setScene(scene);
                                    PutPostController controller = loader.getController();
                                    controller.setPost(post);
                                    Main.getCentralStage().show();
                                    controller.fillHardwareFields();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        });



                        MenuItem removeItem = new MenuItem("Delete");
                        removeItem.setOnAction(new EventHandler<ActionEvent>() {


                            @Override
                            public void handle(ActionEvent event) {

                                Post post = row.getItem();

                                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                                confirm.setTitle("Delete Confirmation");
                                confirm.setHeaderText("You are about to delete: " + post.getTitle());
                                confirm.setContentText("Are you sure you want to delete this record?");

                                confirm.showAndWait().ifPresent(response -> {
                                    if (response == ButtonType.OK) {
                                        try {

                                            PostService postService = new PostService();
                                            String token = Token.getToken();

                                            postService.deletePost(post, token);
                                            postTable.getItems().remove(post);
                                            welcomeText.setText("post deleted successfully!");

                                        } catch (Exception e) {
                                            Alert alert = new Alert(Alert.AlertType.ERROR);
                                            alert.setTitle("Error while deleting Post");
                                            alert.setContentText(e.getMessage());
                                            alert.showAndWait();
                                        }
                                    }
                                });
                            }
                        });
                        rowMenu.getItems().addAll(editItem, removeItem);

                        // only display context menu for non-empty rows:
                        row.contextMenuProperty().bind(
                                Bindings.when(row.emptyProperty())
                                        .then((ContextMenu) null)
                                        .otherwise(rowMenu));
                        return row;
                    }
                });
    }

}


