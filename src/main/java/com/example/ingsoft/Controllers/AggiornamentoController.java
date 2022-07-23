package com.example.ingsoft.Controllers;

import com.example.ingsoft.Controllers.TextFormatters.TextFormatterFactory;
import com.example.ingsoft.Controllers.Validation.Validator;
import com.example.ingsoft.Model.Lavoro.Lavoro;
import com.example.ingsoft.Model.Model;
import com.example.ingsoft.Model.Persona.Lavoratore;
import javafx.application.Platform;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

public class AggiornamentoController {
    private Lavoratore lavoratoreDaModificare;
    private Model model;
    private  Validator validator;
    private ObservableList<Lavoro> listaLavori;
    @FXML
    private TableColumn<Lavoro, String> tbcAzienda, tbcMansione, tbcPeriodo, tbcRetribuzione, tbcLuogo;
    @FXML
    private TableView<Lavoro> tableViewLavori;
    @FXML
    private TextField txtMansione, txtAzienda, txtRetribuzione, txtNome, txtCognome;
    @FXML
    private ComboBox<String> comuneComboBox;
    @FXML
    private DatePicker dtpInizioLavoro, dtpFineLavoro;
    private boolean modifica;
    private Lavoro lavoroDaModificare;

    /***
     * Inizializza il model e tutti i dati necessari alla tabella. Aggiunge inoltre degli eventHandler alla tabella.
     */
    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            model = Model.OttieniIstanza();
            listaLavori = FXCollections.observableArrayList();
            listaLavori.addAll(model.ottieniLavoriDaLavoratore(lavoratoreDaModificare));
            tbcAzienda.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().ottieniNomeAzienda()));
            tbcMansione.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().ottieniMansioneSvolte()));
            tbcLuogo.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().ottieniLuogoLavoro()));
            tbcRetribuzione.setCellValueFactory(p -> new SimpleStringProperty(String.valueOf(p.getValue().ottieniRetribuzioneGiornalieraLorda())));
            tbcPeriodo.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().ottieniPeriodo()));
            tableViewLavori.setItems(listaLavori);
            validator = new Validator();
            validator.aggiungiStringTextField(txtMansione);
            validator.aggiungiStringTextField(txtAzienda);
            validator.aggiungiComboBox(comuneComboBox);
            validator.aggiungiNumberTextField(txtRetribuzione,-1);
            inserisciComuniNelleComboBox();
            TextFormatterFactory textFormatterFactory = new TextFormatterFactory();
            txtMansione.setTextFormatter(textFormatterFactory.OttieniTextFormatter("string"));
            txtRetribuzione.setTextFormatter(textFormatterFactory.OttieniTextFormatter("numero"));
            modifica = false;
            lavoroDaModificare = null;
            tableViewLavori.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    modificaLavoratore(newSelection);
                }
            });
            tableViewLavori.setRowFactory(tv -> {
                TableRow<Lavoro> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && (!row.isEmpty())) {
                        modifica = false;
                        lavoroDaModificare = null;
                        tableViewLavori.getSelectionModel().clearSelection();
                        resettaCampiGui();
                    }
                });
                return row;
            });
            dtpInizioLavoro.getEditor().setEditable(false);
            dtpFineLavoro.getEditor().setEditable(false);
            txtCognome.setText(lavoratoreDaModificare.ottieniCognome());
            txtNome.setText(lavoratoreDaModificare.ottieniNome());
        });

    }

    /***
     * Quando un utente clicca un Lavoro all'interno della tabella, inizializziamo i campi della Gui come quelli del Lavoro
     * selezionato e poi entriamo in modalità modifica.
     * @param lavoro Lavoro Selezionato all'interno della tabella
     */
    private void modificaLavoratore(Lavoro lavoro) {
        txtAzienda.setText(lavoro.ottieniNomeAzienda());
        txtRetribuzione.setText(String.valueOf(lavoro.ottieniRetribuzioneGiornalieraLorda()));
        txtMansione.setText(lavoro.ottieniMansioneSvolte());
        comuneComboBox.getSelectionModel().select(lavoro.ottieniLuogoLavoro());
        dtpInizioLavoro.setValue(lavoro.ottieniDataInizioLavoro());
        dtpFineLavoro.setValue(lavoro.ottieniDataFineLavoro());
        modifica = true;
        lavoroDaModificare = lavoro;
    }

    /***
     * Inserisce i comuni all'interno delle ComboBox
     */
    private void inserisciComuniNelleComboBox() {
        ObservableList<String> comuniDaModel = model.ottieniListaComuni();
        comuneComboBox.setItems(comuniDaModel);
    }

    /***
     * Quando viene invocato lo stage, passiamo come parametro un Lavoratore
     * @param lavoratoreDaModificare Lavoratore da modificare
     */
    public void setLavoratoreDaModificare(Lavoratore lavoratoreDaModificare) {
        this.lavoratoreDaModificare = lavoratoreDaModificare;
    }

    /***
     * Cambia stage e ritorna alla fase di ricerca
     * @param event Click da parte dell'utente del Bottone Indietro
     * @throws IOException Se Ricerca.fxml non esiste
     */
    @FXML
    public void cambiaStageRicerca(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Ricerca.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /***
     * Vengono controllati i dati inseriti e se sono corretti si inserisce il lavoratore all'interno del sistema
     * , altrimenti vengono evidenziate i dati inseriti scorrettamente.
     * @param actionEvent Click del bottone Inserisci.
     */
    @FXML
    private void handleInserimento(ActionEvent actionEvent) {
        String mansione, comune, retribuzioneLorda, nomeAzienda;
        LocalDate dataInizioLavoro, dataFineLavoro;
        mansione = txtMansione.getText().trim();
        comune = comuneComboBox.getSelectionModel().getSelectedItem();
        retribuzioneLorda = txtRetribuzione.getText().trim();
        nomeAzienda = txtAzienda.getText().trim();
        dataInizioLavoro = dtpInizioLavoro.getValue();
        dataFineLavoro = dtpFineLavoro.getValue();
        boolean n1 = controllaDatePickers();
        boolean n2 = validator.validate();
        if(n1 && n2) {
            Lavoro lavoro = new Lavoro(dataInizioLavoro, dataFineLavoro, nomeAzienda, comune, mansione, Integer.valueOf(retribuzioneLorda));
            if (possoInserireLavoro(lavoro)) {
                if (!modifica) {
                    listaLavori.add(lavoro);
                    model.aggiungiLavoroAlLavoratore(lavoratoreDaModificare, lavoro);
                } else {
                    lavoroDaModificare.cambiaDataInizio(dataInizioLavoro);
                    lavoroDaModificare.cambiaLuogoLavoro(comune);
                    lavoroDaModificare.cambiaDataFine(dataFineLavoro);
                    lavoroDaModificare.cambiaMansioneSvolta(mansione);
                    lavoroDaModificare.cambiaRetribuzione(Integer.valueOf(retribuzioneLorda));
                    lavoroDaModificare.cambiaNomeAzienda(nomeAzienda);
                    model.SalvaSuFile();
                }
                modifica = false;
                lavoroDaModificare = null;
                resettaCampiGui();
                tableViewLavori.refresh();
            }
        }
    }

    /***
     * Resetta i campi della Gui quando viene inserito o modificato un lavoratore
     */
    private void resettaCampiGui() {
        txtRetribuzione.setText("");
        txtAzienda.setText("");
        txtMansione.setText("");
        dtpInizioLavoro.setValue(null);
        dtpFineLavoro.setValue(null);
        comuneComboBox.getSelectionModel().select("");

    }

    /**
     * Controlla se il lavoro esiste già
     * @param lavoro Lavoro da controllare
     * @return true se il lavoro non è già all'interno della lista dei lavori fatti, false altrimenti.
     */
    private boolean possoInserireLavoro(Lavoro lavoro) {
        return !listaLavori.contains(lavoro);
    }

    /***
     * Controlla se le date inserite sono corrette.
     * @return true se le date sono inserite correttamente, false altrimenti.
     */
    private boolean controllaDatePickers() {
        boolean isValid = true;
        if(dtpInizioLavoro.getValue() == null || dtpFineLavoro.getValue() == null)
            isValid = false;
        else if (dtpInizioLavoro.getValue() != null && dtpFineLavoro.getValue() != null) {
            isValid = dtpInizioLavoro.getValue().isAfter(LocalDate.now().minusYears(5)) && dtpFineLavoro.getValue().isAfter(dtpInizioLavoro.getValue())
                    && dtpFineLavoro.getValue().isBefore(LocalDate.now());
        }

        if (!isValid) {
            dtpInizioLavoro.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            dtpFineLavoro.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
        } else {
            dtpInizioLavoro.setStyle("-fx-border-color: transparent transparent  #c9d1de transparent; -fx-background-color: transparent;");
            dtpFineLavoro.setStyle("-fx-border-color: transparent transparent  #c9d1de transparent; -fx-background-color: transparent;");
        }

        return isValid;
    }


    /***
     * Elimina un lavoratore selezionato
     * @param actionEvent Click bottone elimina
     */
    public void handleElimina(ActionEvent actionEvent) {
        if (tableViewLavori.getSelectionModel().getSelectedItem() != null) {
            model.rimuoviLavoroAlLavoratore(lavoratoreDaModificare, tableViewLavori.getSelectionModel().getSelectedItem());
            modifica = false;
            lavoroDaModificare = null;
            tableViewLavori.getSelectionModel().clearSelection();
            tableViewLavori.refresh();
        }
    }
}
