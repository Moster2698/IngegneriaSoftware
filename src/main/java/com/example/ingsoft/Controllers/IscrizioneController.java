package com.example.ingsoft.Controllers;


import com.example.ingsoft.Controllers.Validation.Validator;
import com.example.ingsoft.HelloApplication;
import com.example.ingsoft.Model.AutoCompleteBox;
import com.example.ingsoft.Model.ComuniProvider;
import com.example.ingsoft.Model.Lavoratore.Lavoratore;
import com.example.ingsoft.Model.Lavoratore.LavoratoreDaoImpl;
import com.example.ingsoft.Model.LingueProvider;
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
    private TextField txtNome, txtCognome, txtLuogoNascita, txtNazionalita, txtRecTel, txtEmail, txtPatente, txtCitta,
            txtVia, txtCivico, txtCap,txtNomeEmergenza, txtCognomeEmergenza, txtTelefonoEmergenza, txtIndirizzoEmergenza;
    private List<TextField> stringTextFields;
    @FXML
    private CheckBox checkAutomunito, checkBagnino, checkBarman, checkIstruttoreNuoto, checkViticultore, checkFloricultore;
    private List<CheckBox> specializzazioni;
    private List<String> lingueParlate, mansioniEffettuate;
    @FXML
    private DatePicker dPickerNascita, dPInizioLavoro, dPFineLavoro;
    @FXML
    private ComboBox<String> comuneComboBox, lingueComboBox;
    private LavoratoreDaoImpl lavoratoreDaoImpl;
    @FXML
    public void initialize() {

        stringTextFields = new ArrayList<TextField>();

        Collections.addAll(stringTextFields, txtNome, txtCognome, txtLuogoNascita, txtNazionalita, txtEmail, txtPatente, txtCitta,
                txtVia, txtCivico, txtNomeEmergenza, txtCognomeEmergenza, txtIndirizzoEmergenza);

        lavoratoreDaoImpl = new LavoratoreDaoImpl();

        comuni = new TreeSet<String>();
        validator = new Validator();
        specializzazioni = new ArrayList<CheckBox>();

        Collections.addAll(specializzazioni, checkBagnino, checkBarman, checkIstruttoreNuoto, checkViticultore, checkFloricultore);

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
    }
    @FXML
    private void handleIscrizione(ActionEvent event) throws IOException {
        if(FormValid()){
            String nome, cognome, luogoNascita, nazionalita, recapitoTelefonico, email, patente, cittaResidenza,
                    viaResidenza, civicoResidenza, capResidenza;
            String nomeEmergenza, cognomeEmergenza, telefonoEmergenza, indirizzoEmergenza;
            LocalDate dataDiNascita, inizioDisponibilita,fineDisponibilita;
            PersonaUrgente personaUrgente;
            boolean automunito = checkAutomunito.isSelected();
            nome = txtNome.getText();
            cognome = txtCognome.getText();
            luogoNascita = txtLuogoNascita.getText();
            nazionalita = txtNazionalita.getText();
            recapitoTelefonico = txtRecTel.getText();
            email = txtEmail.getText();
            patente = txtPatente.getText();

            cittaResidenza = txtCitta.getText();
            viaResidenza = txtVia.getText();
            civicoResidenza = txtCivico.getText();
            capResidenza = txtCap.getText();

            dataDiNascita = dPickerNascita.getValue();
            inizioDisponibilita = dPInizioLavoro.getValue();
            fineDisponibilita = dPFineLavoro.getValue();

            OttieniEsperienzeCheckBoxes();

            personaUrgente = new PersonaUrgente(txtNomeEmergenza.getText(), txtCognomeEmergenza.getText(), txtTelefonoEmergenza.getText(), txtIndirizzoEmergenza.getText());

            Lavoratore lavoratore = new Lavoratore(nome, cognome, luogoNascita, dataDiNascita, nazionalita, recapitoTelefonico,
                    email, inizioDisponibilita, fineDisponibilita, comuni, lingueParlate, automunito, patente, mansioniEffettuate,
                    cittaResidenza, viaResidenza, civicoResidenza, capResidenza, personaUrgente);
            lavoratoreDaoImpl.add(lavoratore);
            SalvasuFile();
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
    private void reset() {
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
    private void SalvasuFile(){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("yourfile.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(lavoratoreDaoImpl.getLavoratori());
        }catch(Exception e){
            System.out.println(e);
        }
    }
    private boolean FormValid() {
        return validator.validate();
    }

    @FXML
    private void CbComuneSelezionato(){
        String comuneScelto = comuneComboBox.getSelectionModel().getSelectedItem();
        if(comuneScelto!=null)
            comuni.add(comuneScelto);
    }
    @FXML
    private void CbLinguaSelezionata(){
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
