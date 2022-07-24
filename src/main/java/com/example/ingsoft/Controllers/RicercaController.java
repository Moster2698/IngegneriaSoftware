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

    /***
     * Si inizializzano i campi della tabella, il model e i dati che la tabella avrà al suo interno.
     */
    @FXML
    public void initialize(){
        model = Model.OttieniIstanza();
        observableListlavoratori = FXCollections.observableList(model.OttieniLavoratori());
        tbcNome.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().ottieniNome()));
        tbcCognome.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().ottieniCognome()));
        tbcComune.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().ottieniCittaResidenza()));
        tbcLingue.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().ottieniStringLingue()));
        tbcMansione.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().ottieniMansione()));
        tbcAutomunito.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().ottieniAutomunito() ? "SI" : "NO"));
        tbcPatente.setCellValueFactory(p -> new SimpleStringProperty( p.getValue().ottieniStringPatente()));
        tbcDisponibilita.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().ottieniPeriodoDisponibilita()));
        tbcComuniDisponibilita.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().ottieniStringComuni()));
        tbcSpecializzazioni.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().ottieniStringSpecializzazioni()));
        tableViewLavoratori.setItems(observableListlavoratori);
    }

    /***
     * Evento di quando l'utente clicca il pulsante Ricerca. Si ricavano i dati inseriti nelle Input Box e si esegue una ricerca
     * all'interno del sistema dei lavoratori disponibili. Successivamente si aggiorna la tabella.
     */
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
        String[] lingueP = textLingue.getText().trim().split(",");
        try {
            if (!lingueP[0].isEmpty() && !lingueP[0].isBlank()) {
                for (int i = 0; i < lingueP.length; i++) {
                    lingueP[i] = lingueP[i].trim().toLowerCase();
                    lingueP[i] = lingueP[i].substring(0, 1).toUpperCase() + lingueP[i].substring(1);
                }
            }
            lingueParlate = Arrays.asList(lingueP);
        }
        catch(IndexOutOfBoundsException e){
            lingueParlate = new ArrayList<>();
        }

        String[] zoneDisp = textDisponibilita.getText().trim().split(",");
        for(int i=0;i<zoneDisp.length;i++)
            zoneDisp[i] = zoneDisp[i].trim();
        zoneDisponibilita = new ArrayList<>(Arrays.asList(zoneDisp));

        mansioni = new ArrayList<>(Arrays.asList(textMansione.getText().trim().split(" ")));

        observableListlavoratori.setAll(model.cercaLavoratori(nome,cognome,lingueParlate,dataInizio,dataFine,mansioni,zoneDisponibilita,cittaResidenza,automunito,patente,isOr));
        resettaCampiGui();
        /*
        Sarebbe utile mettere un ALERT se la ricerca non produce risultati.
         */

    }

    /***
     * Ritorna al menù principale
     * @param event Click del pulsante Indietro
     * @throws IOException
     */
    @FXML
    public void backToPrincipalMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("PrincipalMenu.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    /***
     * Evento quando si clicca il pulsante Modifica. Se un lavoratore è selezionato nella TableView si apre un nuovo stage contenente la GUI per l'aggiornamento dei dati
     * @param event Click del pulsante Modifica
     * @throws IOException Se il file Aggiornamento.fxml non esiste
     */
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
            alert.setHeaderText("Sei sicuro di volere eliminare il lavoratore " + lavoratoreDaEliminare.ottieniNome() + " " + lavoratoreDaEliminare.ottieniCognome() + "? L'operazione è irreversibile.");
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
    private void resettaCampiGui(){
        dtpInizio.setValue(null);
        dtpFine.setValue(null);
    }
}
