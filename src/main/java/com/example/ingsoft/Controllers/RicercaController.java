package com.example.ingsoft.Controllers;

import com.example.ingsoft.Model.Lavoratore.Lavoratore;
import com.example.ingsoft.Model.Lavoratore.LavoratoreDaoImpl;
import com.example.ingsoft.Model.Lavoro.LavoroDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.synedra.validatorfx.Check;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
}
