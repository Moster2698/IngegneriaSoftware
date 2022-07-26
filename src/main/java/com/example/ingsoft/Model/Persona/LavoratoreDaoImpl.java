package com.example.ingsoft.Model.Persona;

import com.example.ingsoft.Model.Lavoro.Lavoro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
    /***
     * Legge il file dove sono salvati i dati del sistema e li salva all'interno di una lista
     */
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

    /***
     * Ottieni i lavoratori del sistema
     * @return Lavoratori attualmente inseriti nel sistema
     */
    @Override
    public List<Lavoratore> ottieniLavoratori() {
        return lavoratori;
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
    @Override
    public List<Lavoratore> cercaLavoratori(String nome, String cognome, List<String> lingueParlate, LocalDate dataInizio, LocalDate dataFine, List<String> mansioni, List<String> zonaDisponibilita, String cittaResidenza, String automunito, String patente,boolean isOr) {

        if (isOr == false) {
            return lavoratori.stream().filter(lavoratore -> {
                boolean isValid = true;
                if (!nome.isEmpty() || !nome.isBlank())
                    isValid = lavoratore.ottieniNome().equalsIgnoreCase(nome);
                if (!cognome.isEmpty() || !cognome.isBlank())
                    isValid = isValid && lavoratore.ottieniCognome().equalsIgnoreCase(cognome);
                if (dataInizio != null && dataFine != null) {
                    isValid = isValid && dataInizio.isBefore(lavoratore.ottieniInizioDisponibilita().plusDays(1)) && dataFine.isAfter(lavoratore.ottieniFineDisponibilita().minusDays(1));
                } else if (dataInizio != null) {
                    isValid = isValid && lavoratore.ottieniInizioDisponibilita().isAfter( dataInizio.minusDays(1));
                } else if (dataFine != null) {
                    isValid = isValid && lavoratore.ottieniFineDisponibilita().isBefore(dataFine.plusDays(1));
                }
                if (!mansioni.isEmpty() && (!mansioni.get(0).isBlank() || !mansioni.get(0).isEmpty())) {
                    isValid = isValid && mansioni.stream().anyMatch(s -> {
                        return s.equalsIgnoreCase(lavoratore.ottieniMansione());
                    });
                }
                if (!lingueParlate.isEmpty() && (!lingueParlate.get(0).isBlank() || !lingueParlate.get(0).isEmpty())) {
                        isValid = isValid && lavoratore.ottieniLingueParlate().containsAll(lingueParlate);
                }
                if (!zonaDisponibilita.isEmpty() && (!zonaDisponibilita.get(0).isBlank() || !zonaDisponibilita.get(0).isEmpty())) {
                    isValid = isValid && zonaDisponibilita.stream().allMatch(s -> {
                        for (String zona : lavoratore.ottieniComuni()) {
                            if (zona.equalsIgnoreCase(s))
                                return true;
                        }
                        return false;
                    });
                }
                if (!cittaResidenza.isEmpty() || !cittaResidenza.isBlank()) {
                    isValid = isValid && cittaResidenza.equalsIgnoreCase(lavoratore.ottieniCittaResidenza());
                }
                if (!automunito.isBlank()) {
                    boolean boolAuto = automunito.equals("Si");
                    isValid = isValid && lavoratore.ottieniAutomunito() == boolAuto;
                }
                if (!patente.isEmpty() || !patente.isBlank()) {
                    isValid = isValid && lavoratore.ottieniPatente().stream().anyMatch(patente::equalsIgnoreCase);
                }
                return isValid;
            }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        }
        else{
            return lavoratori.stream().filter(lavoratore -> {
                boolean isValid = false;
                boolean empty = true;
                if (!nome.isEmpty() || !nome.isBlank())
                {
                    isValid = isValid || lavoratore.ottieniNome().equalsIgnoreCase(nome);
                    empty = false;
                }
                if (!cognome.isEmpty() || !cognome.isBlank())
                {
                    isValid = isValid ||  lavoratore.ottieniCognome().equalsIgnoreCase(cognome);
                    empty = false;
                }
                if (dataInizio != null && dataFine != null) {
                    isValid = isValid || lavoratore.ottieniInizioDisponibilita().isAfter( dataInizio.minusDays(1)) && lavoratore.ottieniFineDisponibilita().isBefore(dataFine.plusDays(1));
                } else if (dataInizio != null) {
                    isValid = isValid || lavoratore.ottieniInizioDisponibilita().isAfter( dataInizio.minusDays(1));
                    empty = false;
                } else if (dataFine != null) {
                    isValid = isValid || lavoratore.ottieniFineDisponibilita().isBefore(dataFine.plusDays(1));
                    empty = false;
                }
                if (!mansioni.isEmpty() && (!mansioni.get(0).isBlank() || !mansioni.get(0).isEmpty())) {
                    isValid = isValid || mansioni.stream().anyMatch(s -> s.equalsIgnoreCase(lavoratore.ottieniMansione()));
                    empty = false;
                }
                if (!lingueParlate.isEmpty() && (!lingueParlate.get(0).isBlank() || !lingueParlate.get(0).isEmpty())) {
                    isValid = isValid || lavoratore.ottieniLingueParlate().stream().anyMatch(lingueParlate::contains);
                    empty = false;
                }
                if (!zonaDisponibilita.isEmpty() && (!zonaDisponibilita.get(0).isBlank() || !zonaDisponibilita.get(0).isEmpty())) {
                    isValid =isValid || lavoratore.ottieniComuni().stream().anyMatch(s -> {
                        for (String zona : zonaDisponibilita) {
                            if (zona.equalsIgnoreCase(s))
                                return true;
                        }
                        return false;
                    });
                    empty = false;
                }
                if (!cittaResidenza.isEmpty() || !cittaResidenza.isBlank()) {
                    isValid = isValid || cittaResidenza.equalsIgnoreCase(lavoratore.ottieniCittaResidenza());
                    empty = false;
                }
                if (!automunito.isBlank()) {
                    boolean boolAuto = automunito.equals("Si");
                    isValid = isValid || lavoratore.ottieniAutomunito() == boolAuto;
                    empty = false;
                }
                if (!patente.isEmpty() || !patente.isBlank()) {
                    isValid = isValid || lavoratore.ottieniPatente().stream().anyMatch(patente::equalsIgnoreCase);
                    empty = false;
                }
                if(empty)
                    return true;
                return isValid;
            }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        }
    }

    /***
     * Ottieni la lista dei lavori svolti dal lavoratore
     * @param lavoratore Lavoratore da cui vogliamo ottenere i lavori svolti
     * @return Lista dei lavori svolti dal lavoratore
     */
    @Override
    public ObservableList<Lavoro> OttieniLavoro(Lavoratore lavoratore) {
        return  FXCollections.observableList(lavoratore.ottieniLavori());
    }

    /***
     * Aggiunge un lavoratore al sistema
     * @param lavoratore  Lavoratore da aggiungere
     */
    @Override
    public void aggiungiLavoratore(Lavoratore lavoratore) {
        if(!lavoratori.contains(lavoratore))
            lavoratori.add(lavoratore);
    }

    /***
     * Rimuovi un lavoratore dal sistema
     * @param lavoratore  Lavoratore da rimuovere
     */
    public void rimuoviLavoratore(Lavoratore lavoratore) {
        lavoratori.remove(lavoratore);
    }
}
