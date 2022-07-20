package com.example.ingsoft.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PrincipalMenuController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToRicerca(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Ricerca.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToIscrizione(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Iscrizione.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/com/example/ingsoft/Controllers/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
    public void backToLogin(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
