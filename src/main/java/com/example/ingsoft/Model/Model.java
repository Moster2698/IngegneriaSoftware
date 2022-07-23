package com.example.ingsoft.Model;

import com.example.ingsoft.Model.Lavoro.Lavoro;
import com.example.ingsoft.Model.Persona.Lavoratore;
import com.example.ingsoft.Model.Persona.LavoratoreDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.List;

public class Model {
    public static Model model = new Model();
    private LavoratoreDaoImpl lavoratoreDao;
    private ComuniProvider comuniProvider;
    private Model(){
        lavoratoreDao = new LavoratoreDaoImpl();
        comuniProvider = ComuniProvider.getInstance();
    }

    /***
     * Ottiene l'istanza della classe
     * @return istanza della classe
     */
    public static Model OttieniIstanza(){
        return model;
    }

    /***
     * Salva lo stato corrente dell'applicazione, invocato a ogni modifica del lavoratoreDAOimpl
     */
    public void SalvaSuFile(){
        try {
            FileOutputStream fileOutputStream
                    = new FileOutputStream("yourfile.txt");
            ObjectOutputStream objectOutputStream
                    = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(lavoratoreDao.ottieniLavoratori());
        }catch(Exception e){
            System.out.println(e);
        }
    }

    /***
     * Inserisce il lavoratore all'interno del sistema e salva lo stato corrente su file
     * @param lavoratore  Lavoratore da inserire nel sistema
     */
    public void AggiungiLavoratore(Lavoratore lavoratore){
        lavoratoreDao.aggiungiLavoratore(lavoratore);
        SalvaSuFile();
    }

    /***
     * Rimuove il lavoratore all'interno del sistema e salva lo stato corrente su file
     * @param lavoratore Lavoratore da rimuovere
     */
    public void RimuoviLavoratore(Lavoratore lavoratore){
        lavoratoreDao.rimuoviLavoratore(lavoratore);
        SalvaSuFile();
    }

    /***
     * Ritorna una lista dei lavoratori attualmente inseriti nel sistema
     * @return Lista di lavoratori
     */
    public ObservableList<Lavoratore> OttieniLavoratori(){
        return FXCollections.observableArrayList(lavoratoreDao.ottieniLavoratori());
    }

    /***
     * Cerca un lavoratore specificato da questi parametri
     * @param nome Nome lavoratore
     * @param cognome Cognome lavoratore
     * @param lingueParlate Lingue parlate del lavoratore
     * @param dataInizio Giorno d'inizio del periodo di disponibilità
     * @param dataFine Giorno di fine del periodo di disponibilità
     * @param mansioni Lista di mansioni che un lavoratore può eseguire
     * @param zonaDisponibilita Lista di comuni dove un lavoratore può lavorare
     * @param cittaResidenza Città di residenza del Lavoratore
     * @param automunito Valore booleano che identifica se il lavoratore è automunito
     * @param patente Lista di patenti che un lavoratore deve avere
     * @param isOr Se true la ricerca è fatta in OR, false in AND
     * @return Lista di lavoratori che corrispondono alle specifiche date
     */
    public ObservableList<Lavoratore> cercaLavoratori(String nome, String cognome, List<String> lingueParlate, LocalDate dataInizio, LocalDate dataFine, List<String> mansioni,
                              List<String> zonaDisponibilita, String cittaResidenza, String automunito, String patente,boolean isOr){
        return FXCollections.observableArrayList(lavoratoreDao.cercaLavoratori(nome,cognome,lingueParlate,dataInizio,dataFine,mansioni,zonaDisponibilita,cittaResidenza,automunito,patente,isOr));
    }

    /***
     * Ottieni una lista dei comuni italiani, è un Wrapper della classe ComuniProvider
     * @return Lista di comuni italiani
     */
    public ObservableList<String> ottieniListaComuni(){
        return FXCollections.observableList(comuniProvider.ottieniListaComuni());
    }

    /***
     * Ottiene i lavori fatti dal lavoratore specificato nell'argomento
     * @param lavoratore Lavoratore da cui vogliamo ottenere i lavori
     * @return I lavori che ha svolto il lavoratore
     */
    public ObservableList<Lavoro> ottieniLavoriDaLavoratore(Lavoratore lavoratore){
        return FXCollections.observableList(lavoratore.ottieniLavori());
    }

    /**
     * Aggiunge un lavoro svolto dal lavoratore specificato
     * @param lavoratore a cui vogliamo aggiungere il lavoro
     * @param lavoro da aggiungere
     */
    public void aggiungiLavoroAlLavoratore(Lavoratore lavoratore, Lavoro lavoro){
        lavoratore.aggiungiLavoro(lavoro);
        SalvaSuFile();
    }

    /***
     * Rimuove un lavoro svolto dal lavoratore
     * @param lavoratore Lavoratore a cui vogliamo eliminare il lavoro
     * @param lavoro Lavoro da eliminare dal lavoratore
     */
    public void rimuoviLavoroAlLavoratore(Lavoratore lavoratore, Lavoro lavoro){
        lavoratore.ottieniLavori().remove(lavoro);
        SalvaSuFile();
    }

}

