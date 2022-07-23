package com.example.ingsoft.Controllers;

import com.example.ingsoft.Model.Persona.Dipendente;
import com.example.ingsoft.Model.Persona.DipendenteDaoImpl;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.*;
import java.net.URL;
import java.util.FormatFlagsConversionMismatchException;
import java.util.Optional;
import java.util.function.Predicate;


public class LoginController {
    @FXML
    private Label labelError;
    @FXML
    private TextField textUsername;
    @FXML
    private PasswordField textPassword;
    @FXML
    private Button btnAccedi;

    private DipendenteDaoImpl dipendenteDaoImpl;
    @FXML
    public void initialize(){
        dipendenteDaoImpl = new DipendenteDaoImpl();

    }
    /***
     * Evento di quando l'utente clicca il bottone Login. Se i dati inseriti sono corretti si procede al Menù principale,
     * altrimenti viene mostrato un segnale di errore.
     */
    @FXML
    private void onButtonClicked() {
        String username = textUsername.getText();
        String password = textPassword.getText();
        Dipendente dipendente = null;
        Optional<Dipendente> op = dipendenteDaoImpl.ottieniDipendenti().stream().filter(d -> d.ottieniUsername().equals(username)).findFirst();
        if(op.isPresent()){
            dipendente = op.get();
        }

        if(dipendente!=null && dipendente.ottieniPassword().equals(password)) {
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

    /***
     * Evento invocato quando viene inserito un valore all'interno delle TextFields, disabilita il bottone Login se i dati devono ancora essere
     * inseriti
     */
    @FXML
    private void onInputChanged(){
        String username = textUsername.getText();
        String password = textPassword.getText();
        btnAccedi.setDisable(username.length() <= 0 || password.length() <= 0);
    }

}
