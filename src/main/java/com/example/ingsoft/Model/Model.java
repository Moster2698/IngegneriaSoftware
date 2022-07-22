package com.example.ingsoft.Model;

import com.example.ingsoft.Model.Lavoro.Lavoro;
import com.example.ingsoft.Model.Persona.Lavoratore;
import com.example.ingsoft.Model.Persona.LavoratoreDaoImpl;
import com.example.ingsoft.Model.guiData.ComuniProvider;
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
    public static Model OttieniIstanza(){
        return model;
    }
    public void SalvaSuFile(){
        try {
            FileOutputStream fileOutputStream
                    = new FileOutputStream("yourfile.txt");
            ObjectOutputStream objectOutputStream
                    = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(lavoratoreDao.getLavoratori());
        }catch(Exception e){
            System.out.println(e);
        }
    }
    public void AggiungiLavoratore(Lavoratore lavoratore){
        lavoratoreDao.add(lavoratore);
        SalvaSuFile();
    }
    public void RimuoviLavoratore(Lavoratore lavoratore){
        lavoratoreDao.remove(lavoratore);
        SalvaSuFile();
    }
    public ObservableList<Lavoratore> OttieniLavoratori(){
        return FXCollections.observableArrayList(lavoratoreDao.getLavoratori());
    }
    public ObservableList<Lavoratore> cercaLavoratori(String nome, String cognome, List<String> lingueParlate, LocalDate dataInizio, LocalDate dataFine, List<String> mansioni,
                              List<String> zonaDisponibilita, String cittaResidenza, String automunito, String patente,boolean isOr){
        return FXCollections.observableArrayList(lavoratoreDao.cercaLavoratori(nome,cognome,lingueParlate,dataInizio,dataFine,mansioni,zonaDisponibilita,cittaResidenza,automunito,patente,isOr));
    }
    public ObservableList<String> OttieniComuni(){
        return comuniProvider.getListaComuni();
    }

    public ObservableList<Lavoro> OttieniLavori(Lavoratore lavoratore){
        return FXCollections.observableList(lavoratore.OttieniLavori());
    }
    public void AggiungiLavoroAlLavoratore(Lavoratore lavoratore,Lavoro lavoro){
        lavoratore.OttieniLavori().add(lavoro);
        SalvaSuFile();
    }
    public void RimuoviLavoroAlLavoratore(Lavoratore lavoratore, Lavoro lavoro){
        lavoratore.OttieniLavori().remove(lavoro);
        SalvaSuFile();
    }
    public void ModificaLavoro(Lavoro lavoroVecchio, Lavoro lavoroModificato)
    {
        lavoroVecchio = lavoroModificato;
        SalvaSuFile();
    }
}

