package com.example.ingsoft.Controllers;

import com.example.ingsoft.Model.Lavoratore.Lavoratore;
import com.example.ingsoft.Model.Lavoratore.LavoratoreDaoImpl;
import com.example.ingsoft.Model.Lavoro.LavoroDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class RicercaController {
    private LavoratoreDaoImpl lavoratoreDaoImpl;
    @FXML
    private TextField textCognome, textNome, textMansione, textDisponibilita, textCitta, textPatente;
    @FXML
    private CheckBox checkCinese, checkIndiano, checkInglese, checkItaliano, checkFrancese, checkPortoghese,
            checkRusso, checkSpagnolo, checkTedesco, checkOther, checkAutomunito;
    @FXML
    private DatePicker dtpInizio, dtpFine;
    @FXML
    private ListView listViewLavoratori;
    private List<CheckBox> checkBoxes;
    private ObservableList<Lavoratore> lavoratori;
    @FXML
    private void Ricerca(){
        String nome, cognome, mansione, cittaResidenza, patente;
        List<String> lingueParlate, zonaDisponibilita;
        LocalDate dataInizio, dataFine;
        boolean automunito;
        nome = textNome.getText();
        cognome = textCognome.getText();
        mansione = textMansione.getText();
        cittaResidenza = textCitta.getText();
        patente = textPatente.getText();
        lingueParlate = new ArrayList<String>();
        zonaDisponibilita = new ArrayList<String>();
        dataInizio = dtpInizio.getValue();
        dataFine = dtpFine.getValue();
        automunito = checkAutomunito.isSelected();
        for(CheckBox cb : checkBoxes){
            if(cb.isSelected())
                lingueParlate.add(cb.getText());
        }
        zonaDisponibilita.add(textDisponibilita.getText());
        listViewLavoratori.setItems(FXCollections.observableList(lavoratoreDaoImpl.research(nome,cognome,lingueParlate,dataInizio,dataFine,mansione,zonaDisponibilita,cittaResidenza,automunito,patente)));

        /*
        Sarebbe utile mettere un ALERT se la ricerca non produce risultati.
         */

    }
    @FXML
    public void backToPrincipalMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("PrincipalMenu.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void initialize(){
        checkBoxes = new ArrayList<CheckBox>();
        Collections.addAll(checkBoxes,checkCinese, checkIndiano, checkInglese, checkItaliano, checkFrancese, checkPortoghese,
                checkRusso, checkSpagnolo, checkTedesco, checkOther);
        lavoratoreDaoImpl= new LavoratoreDaoImpl();
        listViewLavoratori.setItems(FXCollections.observableList(lavoratoreDaoImpl.getLavoratori()));
    }

    @FXML
    public void handleMouseClick(MouseEvent arg0) {
        // se si clicca su null non deve succede niente
        if (listViewLavoratori.getSelectionModel().getSelectedItem() == null) {
        }
        else {
            System.out.println("clicked on " + listViewLavoratori.getSelectionModel().getSelectedItem());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Lavoratore cliccato");
            alert.setHeaderText("Hai cliccato su " + listViewLavoratori.getSelectionModel().getSelectedItem());
            alert.setContentText("Selezionare una opzione per continuare.");
            ButtonType buttonElimina = new ButtonType("Elimina");
            ButtonType buttonModifica = new ButtonType("Modifica");
            ButtonType buttonCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonElimina, buttonModifica, buttonCancel);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonElimina) {
                // elimina lavoratore
                // ma prima chiedo conferma
                Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                alert1.setTitle("Conferma eliminazione");
                alert1.setHeaderText("Sei sicuro di volere eliminare il lavoratore selezionato? L'operazione è irreversibile.");
                alert1.setContentText("Premere Sì per confermare, altrimenti Cancel per annullare.");
                ButtonType buttonYes = new ButtonType("Sì");
                alert1.getButtonTypes().setAll(buttonYes, buttonCancel);
                Optional<ButtonType> result1 = alert1.showAndWait();
                if (result1.get() == buttonYes) {
                    lavoratoreDaoImpl.remove((Lavoratore) listViewLavoratori.getSelectionModel().getSelectedItem());
                    listViewLavoratori.setItems(FXCollections.observableList(lavoratoreDaoImpl.getLavoratori()));
                    // bisogna anche aggiornare il file però...
                } else {
                    // non succede un cazzo
                }
            } else if (result.get() == buttonModifica) {
                // modifica lavoratore
            } else {
                // non succede u'cazz
            }
        }
    }
}
