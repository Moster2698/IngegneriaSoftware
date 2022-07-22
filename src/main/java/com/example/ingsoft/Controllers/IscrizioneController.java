package com.example.ingsoft.Controllers;


import com.example.ingsoft.Controllers.Validation.Validator;
import com.example.ingsoft.Crea;
import com.example.ingsoft.Model.AutoCompleteBox;
import com.example.ingsoft.Model.guiData.ComuniProvider;
import com.example.ingsoft.Model.Persona.Lavoratore;
import com.example.ingsoft.Model.guiData.Lingua;
import com.example.ingsoft.Model.Model;
import com.example.ingsoft.Model.Persona.PersonaUrgente;
import com.example.ingsoft.Model.guiData.Mansione;
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
            ,txtCap,txtEmail,txtNomeEmergenza, txtCognomeEmergenza, txtIndirizzoEmergenza, txtTelefonoEmergenza;
    private List<TextField> stringTextFields;
    @FXML
    private CheckBox checkAutomunito, checkBagnino, checkBarman, checkViticultore, checkFloricultore, checkIstruttoreNuoto;
    private List<String> mansioniEffettuate, lingueParlate, patenti;
    @FXML
    private DatePicker dPickerNascita,dPInizioLavoro,dPFineLavoro;
    @FXML
    private ComboBox<String> comuneComboBox,comuneNascitaComboBox,comuneResidenzaComboBox;
    @FXML
    private ComboBox<Lingua> lingueComboBox;
    @FXML
    private ComboBox<Mansione> mansioneComboBox;
    @FXML
    private ComboBox<Patente> patenteComboBox;
    private Model model;
    @FXML
    public void initialize() {

        stringTextFields = new ArrayList<>();

        Collections.addAll(stringTextFields, txtCognome, txtNome, txtNazionalita, txtVia, txtCivico
                ,txtEmail,txtNomeEmergenza, txtCognomeEmergenza, txtIndirizzoEmergenza);
        comuni = new TreeSet<>();
        validator = new Validator();
        validator.add(stringTextFields);
        validator.add(txtCap,5);
        validator.add(txtTelefonoEmergenza,10);
        validator.add(txtRecTel,10,true);

        validator.add(dPickerNascita);
        validator.add(dPInizioLavoro,dPFineLavoro);

        InserisciComuninellaComuniComboBox();
        lingueComboBox.getItems().setAll(Arrays.asList(Lingua.values()));
        patenteComboBox.getItems().setAll(Arrays.asList(Patente.values()));
        mansioneComboBox.getItems().setAll(Arrays.asList(Mansione.values()));
        lingueParlate = new ArrayList<>();
        mansioniEffettuate = new ArrayList<>();
        patenti = new ArrayList<>();
        new AutoCompleteBox(comuneComboBox);
        new AutoCompleteBox(comuneNascitaComboBox);
        new AutoCompleteBox(comuneResidenzaComboBox);
        model = Model.OttieniIstanza();
    }

    /***
     * Controlla se i dati della form sono corretti, nel caso affermativo crea un lavoratore
     *
     */
    @FXML
    private void handleIscrizione() {
        if(FormValid()){
            String nome, cognome,  telefonoPersonale,  email,  nazionalita, luogo,cittaResidenza,viaResidenza,civicoResidenza,capResidenza;
            LocalDate dataDiNascita, inizioDisponibilita,fineDisponibilita;
            PersonaUrgente personaUrgente;
            boolean automunito;
            nome = txtNome.getText();
            cognome = txtCognome.getText();
            telefonoPersonale = txtRecTel.getText();
            email = txtEmail.getText();
            luogo = comuneNascitaComboBox.getSelectionModel().getSelectedItem();
            nazionalita = txtNazionalita.getText();
            dataDiNascita = dPickerNascita.getValue();
            inizioDisponibilita = dPInizioLavoro.getValue();
            fineDisponibilita = dPFineLavoro.getValue();
            cittaResidenza = comuneResidenzaComboBox.getSelectionModel().getSelectedItem();
            viaResidenza = txtVia.getText();
            civicoResidenza = txtCivico.getText();
            capResidenza = txtCap.getText();
            OttieniSpecializzazioniDalleCheckBox();
            System.out.println(mansioniEffettuate);
            String mansione = mansioneComboBox.getSelectionModel().getSelectedItem().name();
            personaUrgente = new PersonaUrgente(txtNomeEmergenza.getText(),txtCognomeEmergenza.getText(),txtTelefonoEmergenza.getText(),txtIndirizzoEmergenza.getText());
            automunito = checkAutomunito.isSelected();
            Lavoratore lavoratore = new Lavoratore(nome,cognome,luogo,dataDiNascita,nazionalita,telefonoPersonale,email,inizioDisponibilita,fineDisponibilita,
                    comuni,lingueParlate,automunito,patenti,mansioniEffettuate,mansione, cittaResidenza,viaResidenza,civicoResidenza,capResidenza,personaUrgente);
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

    /***
     * Resetta i campi della Gui e le relative strutture dati associate
     */
    private void reset(){
        //comune disponiblita

        //lingue parlate

        //specializzazioni
        for(TextField tf : stringTextFields )
            tf.setText("");
        lingueParlate = new ArrayList<>();
        mansioniEffettuate = new ArrayList<>();
        patenti = new ArrayList<>();
        comuni = new TreeSet<>() {
        };
        txtRecTel.clear();
        txtTelefonoEmergenza.clear();
        txtCap.clear();
        dPFineLavoro.setValue(null);
        dPickerNascita.setValue(null);
        dPInizioLavoro.setValue(null);

    }

    /***
     *Inserisce la lista dei comuni italiani all'interno della ComboBox dedicata
     */
    private void InserisciComuninellaComuniComboBox(){
        ComuniProvider cp = ComuniProvider.getInstance();
        comuneComboBox.setItems(cp.getListaComuni());
        comuneNascitaComboBox.setItems(cp.getListaComuni());
        comuneResidenzaComboBox.setItems(cp.getListaComuni());

    }

    /***
     * Ottiene dalle checkBox specializzazioni tutti i valori selezionati
     */
    private void OttieniSpecializzazioniDalleCheckBox(){
        if(checkBagnino.isSelected())
            mansioniEffettuate.add("Bagnino");
        if(checkBarman.isSelected())
            mansioniEffettuate.add("Barman");
        if(checkFloricultore.isSelected())
            mansioniEffettuate.add("Floricultore");
        if(checkViticultore.isSelected())
            mansioniEffettuate.add("Viticultore");
        if(checkIstruttoreNuoto.isSelected())
            mansioniEffettuate.add("Istruttore di nuoto");

    }

    /***
     *
     * @return booleano che indica se i dati sono stati inseriti correttamente
     */
    private boolean FormValid() {
        return  validator.validate();
    }

    /***
     * EventHandler per la selezione del comune all'interno della ComboBox dedicata
     */
    @FXML
    private void CbComuneSelezionato(){
        String comuneScelto = comuneComboBox.getSelectionModel().getSelectedItem();
        if(comuneScelto!=null)
            comuni.add(comuneScelto);
    }

    /***
     * EventHandler per la selezione della lingua all'interno della ComboBox dedicata
     */
    @FXML
    private void CbiLinguaSelezionata(){
        if(lingueComboBox.getSelectionModel().getSelectedItem()!=null){
            if(lingueParlate.contains(lingueComboBox.getSelectionModel().getSelectedItem().name()))
                lingueParlate.remove(lingueComboBox.getSelectionModel().getSelectedItem().name());
            else
            {
                lingueParlate.add(lingueComboBox.getSelectionModel().getSelectedItem().name());
            }
        }
    }
    /***
     * EventHandler per la selezione della patente all'interno della ComboBox dedicata
     */
    @FXML
    public void cBPatenteSelezionata(){
        if(patenteComboBox.getSelectionModel().getSelectedItem()!=null){
            if(patenti.contains(patenteComboBox.getSelectionModel().getSelectedItem().name()))
                patenti.remove(patenteComboBox.getSelectionModel().getSelectedItem().name());
            else{
                patenti.add(patenteComboBox.getSelectionModel().getSelectedItem().name());
            }
        }
    }

    /***
     * Riporta al menù principale
     * @param event Click dell'utente
     * @throws IOException eccezione se non esiste il file PrincipalMenu.fxml
     */
    public void backToPrincipalMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("PrincipalMenu.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene  scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
