package com.maia.controllers;

import com.maia.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class MenuController {

    public void openLoginScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        Main.getCentralStage().setTitle("LOGIN");
        Main.getCentralStage().setScene(scene);
        Main.getCentralStage().show();
    }

    public void openPostScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/postPost.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        Main.getCentralStage().setTitle("POST");
        Main.getCentralStage().setScene(scene);
        Main.getCentralStage().show();
    }

    public void openGetByIdScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/getPostById.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        Main.getCentralStage().setTitle("GET by ID");
        Main.getCentralStage().setScene(scene);
        Main.getCentralStage().show();
    }

    public void openGetAllScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        Main.getCentralStage().setTitle("GET ALL");
        Main.getCentralStage().setScene(scene);
        Main.getCentralStage().show();
    }

}
