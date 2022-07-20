package com.example.ingsoft.Controllers;


import com.example.ingsoft.Controllers.Validation.Validator;
import com.example.ingsoft.Crea;
import com.example.ingsoft.Model.AutoCompleteBox;
import com.example.ingsoft.Model.ComuniProvider;
import com.example.ingsoft.Model.Lavoratore.Lavoratore;
import com.example.ingsoft.Model.Lavoratore.LavoratoreDaoImpl;
import com.example.ingsoft.Model.LingueProvider;
import com.example.ingsoft.Model.Model;
import com.example.ingsoft.Model.Persona.PersonaUrgente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class IscrizioneController {
    private  Validator validator;
    private SortedSet<String> comuni;
    @FXML
    private TextField txtCognome, txtNome, txtLuogoNascita, txtNazionalita, txtRecTel, txtCitta, txtVia, txtCivico
            ,txtCap,txtEmail,txtPatente,txtNomeEmergenza, txtCognomeEmergenza, txtIndirizzoEmergenza, txtTelefonoEmergenza;
    private List<TextField> stringTextFields;
    @FXML
    private CheckBox checkAutomunito, checkBagnino, checkBarman, checkViticultore, checkFloricultore, checkIstruttoreNuoto;
    private List<CheckBox> specializzazioni;
    private List<String> lingueParlate, mansioniEffettuate;
    @FXML
    private DatePicker dPickerNascita,dPInizioLavoro,dPFineLavoro;
    @FXML
    private ComboBox<String> comuneComboBox,lingueComboBox;
    private LavoratoreDaoImpl lavoratoreDaoImpl;
    private Model model;
    @FXML
    public void initialize() {

        stringTextFields = new ArrayList<TextField>();

        Collections.addAll(stringTextFields, txtCognome, txtNome, txtLuogoNascita, txtNazionalita, txtCitta, txtVia, txtCivico
                ,txtEmail,txtNomeEmergenza, txtCognomeEmergenza, txtIndirizzoEmergenza);

        lavoratoreDaoImpl = new LavoratoreDaoImpl();

        comuni = new TreeSet<String>();
        validator = new Validator();
        specializzazioni = new ArrayList<CheckBox>();

        Collections.addAll(specializzazioni,checkBagnino,checkBarman,checkFloricultore,checkViticultore,checkIstruttoreNuoto);

        validator.add(stringTextFields);
        validator.add(txtCap,5);
        validator.add(txtTelefonoEmergenza,10);
        validator.add(txtRecTel,10,true);
        validator.add(dPickerNascita);
        validator.add(dPInizioLavoro,dPFineLavoro);

        fillComuniComboBox();
        fillLingueComboBox();

        lingueParlate = new ArrayList<String>();
        mansioniEffettuate = new ArrayList<String>();
        new AutoCompleteBox(lingueComboBox);
        new AutoCompleteBox(comuneComboBox);
        Crea crea = new Crea(100);
        crea.CreaLavoratore();
        model = Model.OttieniIstanza();
    }
    @FXML
    private void handleIscrizione(ActionEvent event) {
        if(FormValid()){
            String nome, cognome,  telefonoPersonale,  email,  nazionalita,  patente, luogo;
            LocalDate dataDiNascita, inizioDisponibilita,fineDisponibilita;
            PersonaUrgente personaUrgente;
            boolean automunito;
            nome = txtNome.getText();
            cognome = txtCognome.getText();
            telefonoPersonale = txtRecTel.getText();
            email = txtEmail.getText();
            luogo = txtLuogoNascita.getText();
            nazionalita = txtNazionalita.getText();
            patente = txtPatente.getText();
            dataDiNascita = dPickerNascita.getValue();
            inizioDisponibilita = dPInizioLavoro.getValue();
            fineDisponibilita = dPFineLavoro.getValue();
            OttieniEsperienzeCheckBoxes();
            personaUrgente = new PersonaUrgente(txtNomeEmergenza.getText(),txtCognomeEmergenza.getText(),txtTelefonoEmergenza.getText(),txtIndirizzoEmergenza.getText());
            automunito = checkAutomunito.isSelected();
            Lavoratore lavoratore = new Lavoratore(nome,cognome,telefonoPersonale, luogo, email, nazionalita, dataDiNascita, personaUrgente
                    ,lingueParlate,automunito, patente, mansioniEffettuate,comuni, inizioDisponibilita, fineDisponibilita);
            model.AggiungiLavoratore(lavoratore);
            String nuovoLavoratore = "";
            nuovoLavoratore += nome + " " + cognome;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Inserimento lavoratore");
            alert.setHeaderText("Inserimento del nuovo lavoratore " + nuovoLavoratore + " avvenuto correttamente.");
            alert.setContentText("Premere OK per continuare.");
            alert.showAndWait();
            reset();
            // sarebbe utile passare alla pagina della ricerca o del menu principale così si risolve
            // anche il problema dello style delle varie textfield
        }
    }
    private void reset(){
        //comune disponiblita

        //lingue parlate

        //specializzazioni

        for(TextField tf : stringTextFields )
            tf.setText("");
        lingueParlate.clear();
        mansioniEffettuate.clear();
        txtRecTel.clear();
        txtTelefonoEmergenza.clear();
        txtCap.clear();
        dPFineLavoro.setValue(null);
        dPickerNascita.setValue(null);
        dPInizioLavoro.setValue(null);

    }
    private void fillLingueComboBox() {
        LingueProvider lp = LingueProvider.getInstance();
        lingueComboBox.setItems(lp.getListaLingue());
    }
    private void fillComuniComboBox(){
        ComuniProvider cp = ComuniProvider.getInstance();
        comuneComboBox.setItems(cp.getListaComuni());
    }

    private void OttieniEsperienzeCheckBoxes(){
        for(CheckBox cb : specializzazioni){
            if(cb.isSelected())
                mansioniEffettuate.add(cb.getText());
        }
    }
    private boolean FormValid() {
        return  validator.validate();
    }

    @FXML
    private void CbComuneSelezionato(){
        String comuneScelto = comuneComboBox.getSelectionModel().getSelectedItem();
        if(comuneScelto!=null)
            comuni.add(comuneScelto);
    }
    @FXML
    private void CbiLnguaSelezionata(){
        if(lingueParlate.contains(lingueComboBox.getValue()))
            lingueParlate.remove(lingueComboBox.getValue());
        else
            lingueParlate.add(lingueComboBox.getValue());
    }
    public void backToPrincipalMenu(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("PrincipalMenu.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene  scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
