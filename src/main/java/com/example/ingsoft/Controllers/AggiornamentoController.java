package com.example.ingsoft.Controllers;

import com.example.ingsoft.Model.Lavoro.Lavoro;
import com.example.ingsoft.Model.Model;
import com.example.ingsoft.Model.Persona.Lavoratore;
import com.example.ingsoft.Model.guiData.ComuniProvider;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
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

    private ObservableList<Lavoro> listaLavori;
    @FXML
    private TableColumn<Lavoro,String> tbcAzienda, tbcMansione, tbcPeriodo, tbcRetribuzione, tbcLuogo;
    @FXML
    private TableView<Lavoro> tableViewLavori;
    @FXML
    private TextField txtMansione,txtAzienda,txtRetribuzione;
    @FXML
    private ComboBox<String> comuneComboBox;
    @FXML
    private DatePicker dtpInizioLavoro,dtpFineLavoro;
    @FXML
    private void initialize(){
        Platform.runLater(() -> {
            model = Model.OttieniIstanza();
            listaLavori = model.OttieniLavori(lavoratoreDaModificare);
            tbcAzienda.setCellValueFactory( p-> new SimpleStringProperty(p.getValue().OttieniNomeAzienda()));
            tbcMansione.setCellValueFactory( p-> new SimpleStringProperty(p.getValue().OttieniMansioneSvolte()));
            tbcLuogo.setCellValueFactory( p-> new SimpleStringProperty(p.getValue().OttieniLuogoLavoro()));
            tbcRetribuzione.setCellValueFactory( p-> new SimpleStringProperty(String.valueOf(p.getValue().OttieniRetribuzioneOrariaLorda())));
            tbcPeriodo.setCellValueFactory( p-> new SimpleStringProperty(p.getValue().OttieniPeriodo()));
            tableViewLavori.setItems(listaLavori);
            InserisciComuniNelleComboBox();
        });
    }
    private void InserisciComuniNelleComboBox(){
        ComuniProvider cp = ComuniProvider.getInstance();
        ObservableList<String> comuniDaModel = model.OttieniComuni();
        comuneComboBox.setItems(comuniDaModel);
    }
    public void setLavoratoreDaModificare(Lavoratore lavoratoreDaModificare){
        this.lavoratoreDaModificare = lavoratoreDaModificare;
    }
    @FXML
    public void backToPrincipalMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Ricerca.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleInserimento(ActionEvent actionEvent) {
        String mansione, comune, retribuzioneLorda,nomeAzienda;
        LocalDate dataInizioLavoro, dataFineLavoro;
        mansione = txtMansione.getText().trim();
        comune = comuneComboBox.getSelectionModel().getSelectedItem();
        retribuzioneLorda = txtRetribuzione.getText().trim();
        nomeAzienda = txtAzienda.getText().trim();
        dataInizioLavoro = dtpInizioLavoro.getValue();
        dataFineLavoro = dtpFineLavoro.getValue();
        Lavoro lavoro = new Lavoro(dataInizioLavoro, dataFineLavoro, nomeAzienda, comune,mansione,Integer.valueOf(retribuzioneLorda));
        listaLavori.add(lavoro);
    }
}
