package com.example.ingsoft.Controllers;


import com.example.ingsoft.Controllers.Validation.Validator;
import com.example.ingsoft.Crea;
import com.example.ingsoft.Model.AutoCompleteBox;
import com.example.ingsoft.Model.guiData.ComuniProvider;
import com.example.ingsoft.Model.Lavoratore.Lavoratore;
import com.example.ingsoft.Model.guiData.Lingua;
import com.example.ingsoft.Model.Model;
import com.example.ingsoft.Model.Persona.PersonaUrgente;
import com.example.ingsoft.Model.guiData.Patente;
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
    private List<String> mansioniEffettuate;
    private List<String> lingueParlate;
    private List<String> patenti;
    @FXML
    private DatePicker dPickerNascita,dPInizioLavoro,dPFineLavoro;
    @FXML
    private ComboBox<String> comuneComboBox;
    @FXML
    private ComboBox<Lingua> lingueComboBox;
    @FXML
    private ComboBox<Patente> patenteComboBox;
    private Model model;
    @FXML
    public void initialize() {

        stringTextFields = new ArrayList<TextField>();

        Collections.addAll(stringTextFields, txtCognome, txtNome, txtLuogoNascita, txtNazionalita, txtCitta, txtVia, txtCivico
                ,txtEmail,txtNomeEmergenza, txtCognomeEmergenza, txtIndirizzoEmergenza);

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
        lingueComboBox.getItems().setAll(Arrays.asList(Lingua.values()));
        patenteComboBox.getItems().setAll(Arrays.asList(Patente.values()));
        lingueParlate = new ArrayList<String>();
        mansioniEffettuate = new ArrayList<String>();
        patenti = new ArrayList<String>();
        new AutoCompleteBox(lingueComboBox);
        new AutoCompleteBox(comuneComboBox);
        model = Model.OttieniIstanza();
    }
    @FXML
    private void handleIscrizione(ActionEvent event) {
        if(FormValid()){
            String nome, cognome,  telefonoPersonale,  email,  nazionalita,  patente, luogo,cittaResidenza,viaResidenza,civicoResidenza,capResidenza;
            LocalDate dataDiNascita, inizioDisponibilita,fineDisponibilita;
            PersonaUrgente personaUrgente;
            boolean automunito;
            nome = txtNome.getText();
            cognome = txtCognome.getText();
            telefonoPersonale = txtRecTel.getText();
            email = txtEmail.getText();
            luogo = txtLuogoNascita.getText();
            nazionalita = txtNazionalita.getText();
            patente = patenteComboBox.getSelectionModel().getSelectedItem().name();
            dataDiNascita = dPickerNascita.getValue();
            inizioDisponibilita = dPInizioLavoro.getValue();
            fineDisponibilita = dPFineLavoro.getValue();
            cittaResidenza = txtCitta.getText();
            viaResidenza = txtVia.getText();
            civicoResidenza = txtCivico.getText();
            capResidenza = txtCap.getText();
            OttieniEsperienzeCheckBoxes();
            personaUrgente = new PersonaUrgente(txtNomeEmergenza.getText(),txtCognomeEmergenza.getText(),txtTelefonoEmergenza.getText(),txtIndirizzoEmergenza.getText());
            automunito = checkAutomunito.isSelected();
            Lavoratore lavoratore = new Lavoratore(nome,cognome,luogo,dataDiNascita,nazionalita,telefonoPersonale,email,inizioDisponibilita,fineDisponibilita,
                    comuni,lingueParlate,automunito,patenti,mansioniEffettuate,cittaResidenza,viaResidenza,civicoResidenza,capResidenza,personaUrgente);
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
    private void CbiLinguaSelezionata(){
        if(lingueComboBox.getSelectionModel().getSelectedItem()!=null){
            if(lingueParlate.contains(lingueComboBox.getSelectionModel().getSelectedItem()))
                lingueParlate.remove(lingueComboBox.getSelectionModel().getSelectedItem());
            else
            {
                lingueParlate.add(lingueComboBox.getSelectionModel().getSelectedItem().name());
            }
        }
    }
    @FXML
    public void cBPatenteSelezionata(){
        if(patenteComboBox.getSelectionModel().getSelectedItem()!=null){
            if(patenti.contains(patenteComboBox.getSelectionModel().getSelectedItem()))
                patenti.remove(patenteComboBox.getSelectionModel().getSelectedItem());
            else{
                patenti.add(patenteComboBox.getSelectionModel().getSelectedItem().name());
            }
        }
    }
    public void backToPrincipalMenu(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("PrincipalMenu.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene  scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
