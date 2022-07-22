package com.example.ingsoft.Controllers;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;


public class LoginController {
    @FXML
    private Label labelError;
    @FXML
    private TextField textUsername;
    @FXML
    private PasswordField textPassword;
    @FXML
    private Button btnAccedi;

    /***
     * Se
     */
    @FXML
    private void onButtonClicked() {
        String username = textUsername.getText();
        String password = textPassword.getText();

        if(username.equals("admin") && password.equals("admin")) {
            try {
                URL fxmlLocation = LoginController.class.getResource("PrincipalMenu.fxml");
                FXMLLoader loader = new FXMLLoader(fxmlLocation);
                Stage stage = (Stage) btnAccedi.getScene().getWindow();
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
            } catch (IOException io) {
                io.printStackTrace();
            }
        }
        else
        {
            labelError.setVisible(true);
            delay(2500, () -> labelError.setVisible(false));
        }

        // System.out.println(tex    tUsername.toString());
    }
    public  void delay(long millis, Runnable continuation) {
        Task<Void> sleeper = new Task<>() {
            @Override
            protected Void call() {
                try { Thread.sleep(millis); }
                catch (InterruptedException e) {System.out.println(e); }
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> continuation.run());
        new Thread(sleeper).start();
    }

    @FXML
    private void onInputChanged(){
        String username = textUsername.getText();
        String password = textPassword.getText();
        btnAccedi.setDisable(username.length() <= 0 || password.length() <= 0);
    }

}
