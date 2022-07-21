package com.example.ingsoft.Controllers;

import com.example.ingsoft.Model.Lavoratore.Lavoratore;
import com.example.ingsoft.Model.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class RicercaController {
    @FXML
    private TextField textCognome, textNome, textMansione, textDisponibilita, textCitta, textPatente;
    @FXML
    private CheckBox checkCinese, checkIndiano, checkInglese, checkItaliano, checkFrancese, checkPortoghese,
            checkRusso, checkSpagnolo, checkTedesco, checkOther, checkAutomunito;
    @FXML
    private DatePicker dtpInizio, dtpFine;
    @FXML
    private ListView listViewLavoratori;
    @FXML
    private TableView<Lavoratore> tableViewLavoratori;
    @FXML
    private TableColumn<Lavoratore,String> tbcNome;
    @FXML
    private TableColumn<Lavoratore,String> tbcCognome;
    @FXML
    private TableColumn<Lavoratore, String> tbcDataNascita;
    @FXML
    private TableColumn<Lavoratore,String> tbcComune;
    @FXML
    private TableColumn<Lavoratore,String> tbcLingue;
    @FXML
    private TableColumn<Lavoratore,String> tbcDisponibilita;
    @FXML
    private TableColumn<Lavoratore,String> tbcMansioni;
    private List<CheckBox> checkBoxes;
    private ObservableList<Lavoratore> observableListlavoratori;
    private Model model;
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
        observableListlavoratori =model.ricerca(nome,cognome,lingueParlate,dataInizio,dataFine,mansione,zonaDisponibilita,cittaResidenza,automunito,patente);
        //listViewLavoratori.setItems(model.ricerca(nome,cognome,lingueParlate,dataInizio,dataFine,mansione,zonaDisponibilita,cittaResidenza,automunito,patente));

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
        model = Model.OttieniIstanza();
        observableListlavoratori = model.OttieniLavoratori();
        tbcNome.setCellValueFactory(new PropertyValueFactory<Lavoratore, String>("nome"));
        tbcCognome.setCellValueFactory(new PropertyValueFactory<Lavoratore, String>("cognome"));
        tbcDataNascita.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getDataDiNascita().toString()));
        tbcComune.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getLuogoDiNascita()));
        tbcLingue.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getStringLingue()));
        tbcMansioni.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getStringMansioni()));
        tbcDisponibilita.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getDisponibilita()));
        tableViewLavoratori.getItems().setAll(observableListlavoratori);
    }

    @FXML
    private void EliminaLavoratore(){
        if(tableViewLavoratori.getSelectionModel().getSelectedItem()!=null) {
            Lavoratore lavoratoreDaEliminare = tableViewLavoratori.getSelectionModel().getSelectedItem();
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Conferma eliminazione");
            alert1.setHeaderText("Sei sicuro di volere eliminare il lavoratore " + lavoratoreDaEliminare.getNome() + " " + lavoratoreDaEliminare.getCognome() + "? L'operazione è irreversibile.");
            alert1.setContentText("Premere Sì per confermare, altrimenti Cancel per annullare.");
            ButtonType buttonYes = new ButtonType("Sì");
            ButtonType btnCancel = new ButtonType("Elimina");
            alert1.getButtonTypes().setAll(buttonYes, btnCancel);
            Optional<ButtonType> result1 = alert1.showAndWait();
            if (result1.get() == buttonYes) {
                model.RimuoviLavoratore(lavoratoreDaEliminare);
                observableListlavoratori.remove(lavoratoreDaEliminare);
            }
        }
    }
    @FXML
    private void ModificaLavoratore(){
        Lavoratore lavoratoreDaEliminare = tableViewLavoratori.getSelectionModel().getSelectedItem();
        System.out.println(lavoratoreDaEliminare);
    }
    @FXML
    public void handleMouseClick(MouseEvent mouseEvent) {
        // se si clicca su null non deve succede niente
        if(listViewLavoratori.getSelectionModel().getSelectedItem()!=null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Modifica Lavoratore ");
            alert.setHeaderText("Hai cliccato su " + listViewLavoratori.getSelectionModel().getSelectedItem());
            alert.setContentText("Selezionare una opzione per continuare.");
            ButtonType btnElimina = new ButtonType("Elimina");
            ButtonType btnModifica = new ButtonType("Modifica");
            ButtonType btnCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(btnElimina, btnModifica, btnCancel);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == btnElimina) {
                // elimina lavoratore
                // ma prima chiedo conferma
                Alert alert1 = new Alert(Alert.AlertType.WARNING);
                alert1.setTitle("Conferma eliminazione");
                alert1.setHeaderText("Sei sicuro di volere eliminare il lavoratore selezionato? L'operazione è irreversibile.");
                alert1.setContentText("Premere Sì per confermare, altrimenti Cancel per annullare.");
                ButtonType buttonYes = new ButtonType("Sì");
                alert1.getButtonTypes().setAll(buttonYes, btnCancel);
                Optional<ButtonType> result1 = alert1.showAndWait();
                if (result1.get() == buttonYes) {
                    Lavoratore tmp = (Lavoratore) listViewLavoratori.getSelectionModel().getSelectedItem();
                    model.RimuoviLavoratore((Lavoratore) listViewLavoratori.getSelectionModel().getSelectedItem());
                    observableListlavoratori.remove(tmp);
                }
            } else if (result.get() == btnModifica) {

                // modifica lavoratore
            } else {
                // non succede u'cazz
            }
            listViewLavoratori.getSelectionModel().select(null);
        }
    }
}
