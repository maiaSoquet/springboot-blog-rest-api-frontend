package com.maia.controllers;
import com.maia.models.Login;
import com.maia.models.Token;
import com.maia.services.AuthService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private Label statusLabel;


    private final AuthService authService = new AuthService();

    public LoginController() {
        // loginTextField et passwordField sont null à ce stade
    }

    @FXML
    void initialize() {
        // Les composants injectés sont prêts à être utilisés
    }

    @FXML
    void handleValidateAction(final ActionEvent event) {
        Login loginData = new Login(usernameField.getText(), passwordField.getText());
        try {
            String token = authService.login(loginData);
            Token.setToken(token);
            statusLabel.setText("connected successfully");
            statusLabel.setStyle("-fx-text-fill: green;");
        } catch(Exception e) {
            statusLabel.setText("wrong username or password");
            statusLabel.setStyle("-fx-text-fill: red;");
        }
    }
}


