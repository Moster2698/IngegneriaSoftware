package com.example.ingsoft.Model.Lavoratore;

import com.example.ingsoft.Model.Lavoro.Lavoro;
import com.example.ingsoft.Model.Lavoro.LavoroDaoImpl;
import com.example.ingsoft.Model.Persona.Persona;
import com.example.ingsoft.Model.Persona.PersonaUrgente;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.SortedSet;

public class Lavoratore extends Persona implements Serializable {
    @Serial
    private static final long serialVersionUID = 3L;
    private static int ID = 1;
    private int id;
    private String luogo,nazionalita,patente;
    private String telefonoPersonale;
    private String email;
    private LocalDate dataDiNascita;
    private PersonaUrgente personaUrgente;
    private LavoroDaoImpl lavoriEffettuati;
    private SortedSet<String> comuni;
    private LocalDate inizioDisponibilita;
    private LocalDate fineDisponibilita;
    private List<String> lingueParlate;
    private List<String> esperienzeEffettuate;
    private boolean automunito;
    public Lavoratore(String nome, String cognome) {
        super(nome, cognome);
        ID++;

    }
    public Lavoratore(String nome, String cognome, String telefonoPersonale,String luogo, String email, String nazionalita, LocalDate dataDiNascita, PersonaUrgente personaUrgente,
                      List<String> lingueParlate, boolean automunito, String patente,List<String> esperienzeEffettuate, SortedSet<String> comuni
    ,LocalDate inizioDisponibilita, LocalDate fineDisponibilita){
        this(nome,cognome);
        this.telefonoPersonale = telefonoPersonale;
        this.email = email;
        this.luogo = luogo;
        this.nazionalita = nazionalita;
        this.dataDiNascita = dataDiNascita;
        this.personaUrgente = personaUrgente;
        this.lavoriEffettuati = new LavoroDaoImpl();
        this.esperienzeEffettuate = esperienzeEffettuate;
        this.lingueParlate = lingueParlate;
        this.automunito = automunito;
        this.patente = patente;
        this.comuni = comuni;
        this.inizioDisponibilita = inizioDisponibilita;
        this.fineDisponibilita = fineDisponibilita;
        this.id =  ID;
    }

    @Override
    public String toString() {
        return nome + " " + cognome + " " + email + " " + luogo + " " + lingueParlate;
    }

    @Override
    public boolean equals(Object o) {
        if( o instanceof  Lavoratore){
            Lavoratore other = (Lavoratore) o;
            return nome.equals(other.nome) && cognome.equals(other.cognome) && dataDiNascita.equals(((Lavoratore) o).dataDiNascita);
        }
        return false;
    }

    public SortedSet<String> getComuni() {
        return comuni;
    }

    public String getPatente() {
        return patente;
    }
    public String getCognome(){
        return cognome;
    }
    public String getNome(){
        return nome;
    }
    public LocalDate getInizioDisponibilita(){
        return inizioDisponibilita;
    }
    public LocalDate getFineDisponibilita(){
        return fineDisponibilita;
    }
    public boolean getAutomunito(){
        return automunito;
    }

    public List<String> getLingueParlate() {
        return lingueParlate;
    }
}
