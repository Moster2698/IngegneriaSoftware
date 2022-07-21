package com.example.ingsoft.Model.Lavoratore;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class LavoratoreDaoImpl implements LavoratoreDao, Serializable {
    private List<Lavoratore> lavoratori;

    public LavoratoreDaoImpl(){
        lavoratori = deSerializzaFile();
    }
    private List<Lavoratore> deSerializzaFile(){
        try{
            FileInputStream fileInputStream
                    = new FileInputStream("yourfile.txt");
            ObjectInputStream objectInputStream
                    = new ObjectInputStream(fileInputStream);
            return (List<Lavoratore>) objectInputStream.readObject();
        }
        catch(EOFException e){
            return new ArrayList<Lavoratore>();
        }
        catch(IOException e){
            System.out.println(e);
        }
        catch(ClassNotFoundException e){
            System.out.println(e);
        }
        return null;
    }
    @Override
    public List<Lavoratore> getLavoratori() {
        return lavoratori;
    }


    @Override
    public List<Lavoratore> research(String nome, String cognome, List<String> lingueParlate, LocalDate dataInizio, LocalDate dataFine, String mansione, List<String> zonaDisponibilita, String cittaResidenza, boolean automunito, String patente) {
        return  lavoratori.stream().filter(lavoratore -> {
            boolean isValid = true;
            if(!nome.isEmpty() || !nome.isBlank())
                isValid = isValid && lavoratore.getNome().equalsIgnoreCase(nome);
            if(!cognome.isEmpty() || !cognome.isBlank())
                isValid = isValid && lavoratore.getCognome().equalsIgnoreCase(cognome);
            if(dataInizio!=null && dataFine != null)
            {
                isValid = isValid && dataInizio.isBefore(lavoratore.getInizioDisponibilita()) && dataFine.isAfter(lavoratore.getFineDisponibilita());
            }
            else if(dataInizio!=null){
                isValid = isValid && lavoratore.getInizioDisponibilita().isAfter(dataInizio);
            }
            else if(dataFine!=null){
                isValid = isValid && lavoratore.getFineDisponibilita().isBefore(dataFine);
            }
            if(!mansione.isBlank() || !mansione.isEmpty()){
                isValid = isValid && lavoratore.getMansioniEffettuate().stream().filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) {
                        return s.equalsIgnoreCase(mansione);
                    }
                }).count() > 0;
            }
            if(!lingueParlate.isEmpty() && (!lingueParlate.get(0).isBlank() || !lingueParlate.get(0).isEmpty())){
                isValid = isValid && lavoratore.getLingueParlate().stream().filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) {
                        for(String lingua : lingueParlate){
                            if(lingua.equalsIgnoreCase(s))
                                return true;
                        }
                        return false;
                    }
                }).count() > 0;
            }
            if(!zonaDisponibilita.isEmpty() && (!zonaDisponibilita.get(0).isBlank() || !zonaDisponibilita.get(0).isEmpty())){
                isValid = isValid && lavoratore.getLingueParlate().stream().filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) {
                        for(String zona : zonaDisponibilita){
                            if(zona.equalsIgnoreCase(s))
                                return true;
                        }
                        return false;
                    }
                }).count() > 0;
            }
            if(!cittaResidenza.isEmpty() || !cittaResidenza.isBlank()){
                System.out.println("ciao2");
                isValid = isValid && cittaResidenza.equalsIgnoreCase(lavoratore.getCittaResidenza());
            }
            isValid = isValid && automunito;
            if(!patente.isEmpty() || !patente.isBlank()){
                isValid = isValid && lavoratore.getPatente().equalsIgnoreCase(patente);
            }
            return isValid;
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));

    }

    @Override
    public void add(Lavoratore lavoratore) {
        if(!lavoratori.contains(lavoratore))
            lavoratori.add(lavoratore);
    }

    public void remove(Lavoratore lavoratore) {
        lavoratori.remove(lavoratore);
    }
}
