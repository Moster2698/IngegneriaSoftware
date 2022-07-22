package com.example.ingsoft.Controllers;

import com.example.ingsoft.Controllers.TextFormatters.TextFormatterFactory;
import com.example.ingsoft.Controllers.Validation.Validator;
import com.example.ingsoft.Model.Lavoro.Lavoro;
import com.example.ingsoft.Model.Model;
import com.example.ingsoft.Model.Persona.Lavoratore;
import com.example.ingsoft.Model.guiData.ComuniProvider;
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

    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            model = Model.OttieniIstanza();
            listaLavori = FXCollections.observableArrayList();
            listaLavori.addAll(model.OttieniLavori(lavoratoreDaModificare));
            tbcAzienda.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().OttieniNomeAzienda()));
            tbcMansione.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().OttieniMansioneSvolte()));
            tbcLuogo.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().OttieniLuogoLavoro()));
            tbcRetribuzione.setCellValueFactory(p -> new SimpleStringProperty(String.valueOf(p.getValue().OttieniRetribuzioneOrariaLorda())));
            tbcPeriodo.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().OttieniPeriodo()));
            tableViewLavori.setItems(listaLavori);
            validator = new Validator();
            validator.addStringTextField(txtMansione);
            validator.addStringTextField(txtAzienda);
            validator.addComboBox(comuneComboBox);
            validator.addNumberTextField(txtRetribuzione,-1);
            InserisciComuniNelleComboBox();
            TextFormatterFactory textFormatterFactory = new TextFormatterFactory();
            txtMansione.setTextFormatter(textFormatterFactory.OttieniTextFormatter("string"));
            txtRetribuzione.setTextFormatter(textFormatterFactory.OttieniTextFormatter("numero"));
            modifica = false;
            lavoroDaModificare = null;
            tableViewLavori.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    ModificaLavoratore(newSelection);
                }
            });
            tableViewLavori.setRowFactory(tv -> {
                TableRow<Lavoro> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && (!row.isEmpty())) {
                        modifica = false;
                        lavoroDaModificare = null;
                        tableViewLavori.getSelectionModel().clearSelection();
                        resetFields();
                    }
                });
                return row;
            });
            dtpInizioLavoro.getEditor().setEditable(false);
            dtpFineLavoro.getEditor().setEditable(false);
            txtCognome.setText(lavoratoreDaModificare.getCognome());
            txtNome.setText(lavoratoreDaModificare.getNome());
        });

    }

    private void ModificaLavoratore(Lavoro lavoro) {
        txtAzienda.setText(lavoro.OttieniNomeAzienda());
        txtRetribuzione.setText(String.valueOf(lavoro.OttieniRetribuzioneOrariaLorda()));
        txtMansione.setText(lavoro.OttieniMansioneSvolte());
        comuneComboBox.getSelectionModel().select(lavoro.OttieniLuogoLavoro());
        dtpInizioLavoro.setValue(lavoro.OttieniDataInizioLavoro());
        dtpFineLavoro.setValue(lavoro.OttieniDataFineLavoro());
        modifica = true;
        lavoroDaModificare = lavoro;
    }

    private void InserisciComuniNelleComboBox() {
        ComuniProvider cp = ComuniProvider.getInstance();
        ObservableList<String> comuniDaModel = model.OttieniComuni();
        comuneComboBox.setItems(comuniDaModel);
    }

    public void setLavoratoreDaModificare(Lavoratore lavoratoreDaModificare) {
        this.lavoratoreDaModificare = lavoratoreDaModificare;
    }

    @FXML
    public void backToPrincipalMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Ricerca.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

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
        if(validator.validate()) {
            Lavoro lavoro = new Lavoro(dataInizioLavoro, dataFineLavoro, nomeAzienda, comune, mansione, Integer.valueOf(retribuzioneLorda));
            if (PossoInserireLavoro(lavoro)) {
                if (!modifica) {
                    listaLavori.add(lavoro);
                    model.AggiungiLavoroAlLavoratore(lavoratoreDaModificare, lavoro);
                } else {
                    lavoroDaModificare.CambiaDataInizio(dataInizioLavoro);
                    lavoroDaModificare.CambiaLuogoLavoro(comune);
                    lavoroDaModificare.CambiaDataFine(dataFineLavoro);
                    lavoroDaModificare.CambiaMansioneSvolta(mansione);
                    lavoroDaModificare.CambiaRetribuzione(Integer.valueOf(retribuzioneLorda));
                    lavoroDaModificare.CambiaNomeAzienda(nomeAzienda);
                    model.SalvaSuFile();
                }
                modifica = false;
                lavoroDaModificare = null;
                resetFields();
                tableViewLavori.refresh();
            }
        }
    }

    private void resetFields() {
        txtRetribuzione.setText("");
        txtAzienda.setText("");
        txtMansione.setText("");
        dtpInizioLavoro.setValue(null);
        dtpFineLavoro.setValue(null);
        comuneComboBox.getSelectionModel().select("");

    }

    private boolean PossoInserireLavoro(Lavoro lavoro) {
        return !listaLavori.contains(lavoro) && checkDtp() && checkTextFields();
    }

    private boolean checkDtp() {
        boolean isValid = true;
        if (dtpInizioLavoro != null && dtpFineLavoro != null) {
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

    private boolean checkTextFields() {
        String nomeAz = txtNome.getText().trim();
        String mansione = txtMansione.getText().trim();
        boolean isValid = true;
        if (nomeAz.isBlank() || nomeAz.isEmpty())
            isValid = false;
        if (mansione.isBlank() || mansione.isEmpty())
            isValid = false;
        if (txtRetribuzione.getText().isEmpty() || txtRetribuzione.getText().isBlank())
            isValid = false;
        return isValid;
    }

    public void handleElimina(ActionEvent actionEvent) {
        if (tableViewLavori.getSelectionModel().getSelectedItem() != null) {
            model.RimuoviLavoroAlLavoratore(lavoratoreDaModificare, tableViewLavori.getSelectionModel().getSelectedItem());
            modifica = false;
            lavoroDaModificare = null;
            tableViewLavori.getSelectionModel().clearSelection();
            tableViewLavori.refresh();
        }
    }
}
