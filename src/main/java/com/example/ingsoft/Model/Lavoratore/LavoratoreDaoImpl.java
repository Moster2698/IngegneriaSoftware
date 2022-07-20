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
        List<Lavoratore> copyOfLavoratore = new ArrayList<Lavoratore>(lavoratori);
        return copyOfLavoratore.stream().filter(new Predicate<Lavoratore>() {
            @Override
            public boolean test(Lavoratore lavoratore) {
                boolean isValid = true;
                if(!nome.isEmpty() || !nome.isBlank())
                    isValid = isValid && lavoratore.getNome().equalsIgnoreCase(nome);
                if(!cognome.isEmpty() || !cognome.isBlank())
                    isValid = isValid && lavoratore.getCognome().equalsIgnoreCase(cognome);
                if(!lingueParlate.isEmpty())
                    isValid = isValid && lavoratore.getLingueParlate().equals(lingueParlate);
                System.out.println(lingueParlate + "" + lavoratore.getLingueParlate());
                return isValid;
            }
        }).collect(Collectors.toList());

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
