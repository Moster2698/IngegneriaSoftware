package com.example.ingsoft.Model.Persona;

import javafx.collections.FXCollections;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LavoratoreDaoImpl implements LavoratoreDao, Serializable {
    private final List<Lavoratore> lavoratori;

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
            return new ArrayList<>();
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
    public List<Lavoratore> research(String nome, String cognome, List<String> lingueParlate, LocalDate dataInizio, LocalDate dataFine, List<String> mansioni, List<String> zonaDisponibilita, String cittaResidenza, boolean automunito, String patente) {
        return  lavoratori.stream().filter(lavoratore -> {
            boolean isValid = true;
            if(!nome.isEmpty() || !nome.isBlank())
                isValid = lavoratore.getNome().equalsIgnoreCase(nome);
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
            if(!mansioni.isEmpty() && (!mansioni.get(0).isBlank() || !mansioni.get(0).isEmpty())){
                isValid = isValid && mansioni.stream().anyMatch(s -> {
                    System.out.println(s + " " + lavoratore.getMansione());
                    return s.equalsIgnoreCase(lavoratore.getMansione());
                });
            }
            if(!lingueParlate.isEmpty() && (!lingueParlate.get(0).isBlank() || !lingueParlate.get(0).isEmpty())){
                isValid = isValid && lavoratore.getLingueParlate().stream().anyMatch(lingueParlate::contains);
            }
            if(!zonaDisponibilita.isEmpty() && (!zonaDisponibilita.get(0).isBlank() || !zonaDisponibilita.get(0).isEmpty())){
                isValid = isValid && lavoratore.getLingueParlate().stream().anyMatch(s -> {
                    for (String zona : zonaDisponibilita) {
                        if (zona.equalsIgnoreCase(s))
                            return true;
                    }
                    return false;
                });
            }
            if(!cittaResidenza.isEmpty() || !cittaResidenza.isBlank()) {
                System.out.println("ciao2");
                isValid = isValid && cittaResidenza.equalsIgnoreCase(lavoratore.getCittaResidenza());
            }
            isValid = isValid && lavoratore.getAutomunito()==automunito;
            if(!patente.isEmpty() || !patente.isBlank()){
                isValid = isValid && lavoratore.getPatente().stream().anyMatch(patente::equalsIgnoreCase);
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
