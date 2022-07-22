package com.example.ingsoft.Controllers;

import com.example.ingsoft.Model.Persona.Lavoratore;
import com.example.ingsoft.Model.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class RicercaController {
    @FXML
    private TextField textCognome, textNome, textMansione, textDisponibilita, textCitta, textPatente, textLingue;
    @FXML
    private CheckBox  checkAutomunito;
    @FXML
    private DatePicker dtpInizio, dtpFine;

    @FXML
    private TableView<Lavoratore> tableViewLavoratori;
    @FXML
    private TableColumn<Lavoratore,String> tbcNome;
    @FXML
    private TableColumn<Lavoratore,String> tbcCognome;
    @FXML
    private TableColumn<Lavoratore,String> tbcComune;
    @FXML
    private TableColumn<Lavoratore,String> tbcLingue;
    @FXML
    private TableColumn<Lavoratore,String> tbcDisponibilita, tbcAutomunito, tbcPatente, tbcComuniDisponibilita;
    @FXML
    private TableColumn<Lavoratore,String> tbcMansione;
    @FXML
    private  TableColumn<Lavoratore,String> tbcSpecializzazioni;
    @FXML
    private RadioButton radioAutomunito,radioNonAutomunito, radioOr,radioAnd;
    @FXML
    private ToggleGroup toggleGiunzioniRicerca, toggleAutomunito;
    private ObservableList<Lavoratore> observableListlavoratori;
    private Model model;
    @FXML
    public void initialize(){
        model = Model.OttieniIstanza();
        observableListlavoratori = FXCollections.observableList(model.OttieniLavoratori());
        tbcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tbcCognome.setCellValueFactory(new PropertyValueFactory<>("cognome"));
        tbcComune.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getCittaResidenza()));
        tbcLingue.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getStringLingue()));
        tbcMansione.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getMansione()));
        tbcAutomunito.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getAutomunito() ? "SI" : "NO"));
        tbcPatente.setCellValueFactory(p -> new SimpleStringProperty( p.getValue().getStringPatente()));
        tbcDisponibilita.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getDisponibilita()));
        tbcComuniDisponibilita.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getStringComuni()));
        tbcSpecializzazioni.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getStringSpecializzazioni()));
        tableViewLavoratori.setItems(observableListlavoratori);

    }
    @FXML
    private void Ricerca(){
        String nome, cognome, cittaResidenza, patente,automunito;
        List<String> lingueParlate, zoneDisponibilita,mansioni;
        LocalDate dataInizio, dataFine;
        nome = textNome.getText().trim();
        cognome = textCognome.getText().trim();
        cittaResidenza = textCitta.getText().trim();
        patente = textPatente.getText().trim();
        dataInizio = dtpInizio.getValue();
        dataFine = dtpFine.getValue();
        if(toggleAutomunito.getSelectedToggle()==null){
            automunito = "";
        }
        else{
            automunito = ((RadioButton)toggleAutomunito.getSelectedToggle()).getText();
        }
        boolean isOr = ((RadioButton) toggleGiunzioniRicerca.getSelectedToggle()).getText().equals("OR");
        lingueParlate = new ArrayList<>(Arrays.asList(textLingue.getText().toUpperCase().trim().split(" ")));
        zoneDisponibilita = new ArrayList<>(Arrays.asList(textDisponibilita.getText().trim().split(",")));
        mansioni = new ArrayList<>(Arrays.asList(textMansione.getText().trim().split(" ")));

        observableListlavoratori.setAll(model.cercaLavoratori(nome,cognome,lingueParlate,dataInizio,dataFine,mansioni,zoneDisponibilita,cittaResidenza,automunito,patente,isOr));

        /*
        Sarebbe utile mettere un ALERT se la ricerca non produce risultati.
         */

    }
    @FXML
    public void backToPrincipalMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("PrincipalMenu.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/ingsoft/Controllers/styles.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }



    public void Modifica(ActionEvent event) throws IOException {
        if(tableViewLavoratori.getSelectionModel().getSelectedItem()!=null){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Aggiornamento.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Parent root = (Parent)fxmlLoader.load();
            AggiornamentoController controller = fxmlLoader.<AggiornamentoController>getController();
            controller.setLavoratoreDaModificare(tableViewLavoratori.getSelectionModel().getSelectedItem());
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    /***
     * Elimina il lavoratore selezionato nella tabella
     *
     */
    public void Elimina() {
        if(tableViewLavoratori.getSelectionModel().getSelectedItem()!=null) {
            Lavoratore lavoratoreDaEliminare = tableViewLavoratori.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Rimuovi Lavoratore");
            alert.setHeaderText("Sei sicuro di volere eliminare il lavoratore " + lavoratoreDaEliminare.getNome() + " " + lavoratoreDaEliminare.getCognome() + "? L'operazione è irreversibile.");
            alert.setContentText("Premere Sì per confermare, altrimenti No per annullare.");
            ButtonType buttonYes = new ButtonType("Sì");
            ButtonType btnCancel = new ButtonType("No");
            alert.getButtonTypes().setAll(buttonYes, btnCancel);
            Optional<ButtonType> userChoice = alert.showAndWait();
            if (userChoice.isPresent() && userChoice.get() == buttonYes) {
                observableListlavoratori.remove(lavoratoreDaEliminare);
                model.RimuoviLavoratore(lavoratoreDaEliminare);
                tableViewLavoratori.refresh();
            }
        }
    }
}
