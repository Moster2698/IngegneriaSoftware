package com.example.ingsoft.Model.Persona;

import com.example.ingsoft.Model.Lavoro.Lavoro;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

public class Lavoratore extends Persona implements Serializable {
    @Serial
    private static final long serialVersionUID = 3L;
    private static int ID = 1;
    private int id;

    private String luogoDiNascita, nazionalita, recTel, email, cittaResidenza, viaResidenza, civicoResidenza, capResidenza;

    private LocalDate dataDiNascita, inizioDisponibilita, fineDisponibilita;
    private PersonaUrgente personaUrgente;
    private SortedSet<String> comuni;
    private List<String> lingueParlate;
    private List<String> specializzazioni;
    private String mansione;
    private List<String> patenti;
    private boolean automunito;
    private  List<Lavoro> lavori;
    public Lavoratore(String nome, String cognome) {
        super(nome, cognome);
        ID++;

    }

    public Lavoratore(String nome, String cognome, String luogoDiNascita, LocalDate dataDiNascita, String nazionalita, String recTel,
                      String email, LocalDate inizioDisponibilita, LocalDate fineDisponibilita, SortedSet<String> comuni, List<String> lingueParlate,
                      boolean automunito, List<String> patenti, List<String> specializzazioni,String mansione, String cittaResidenza, String viaResidenza,
                      String civicoResidenza, String capResidenza, PersonaUrgente personaUrgente) {
        this(nome, cognome);
        this.luogoDiNascita = luogoDiNascita;
        this.dataDiNascita = dataDiNascita;
        this.nazionalita = nazionalita;
        this.recTel = recTel;
        this.email = email;
        this.inizioDisponibilita = inizioDisponibilita;
        this.fineDisponibilita = fineDisponibilita;
        this.comuni = comuni;
        this.lingueParlate = lingueParlate;
        this.automunito = automunito;
        this.patenti = patenti;
        this.specializzazioni = specializzazioni;
        lavori = new ArrayList<Lavoro>();
        this.mansione = mansione;
        this.cittaResidenza = cittaResidenza;
        this.viaResidenza = viaResidenza;
        this.civicoResidenza = civicoResidenza;
        this.capResidenza = capResidenza;
        this.personaUrgente = personaUrgente;
        this.id = ID;
    }

    @Override
    public String toString() {
        return "Lavoratore{" +
                "id=" + id +
                ", luogoDiNascita='" + luogoDiNascita + '\'' +
                ", nazionalita='" + nazionalita + '\'' +
                ", recTel='" + recTel + '\'' +
                ", email='" + email + '\'' +
                ", patente='" + getStringPatente() + '\'' +
                ", cittaResidenza='" + cittaResidenza + '\'' +
                ", viaResidenza='" + viaResidenza + '\'' +
                ", civicoResidenza='" + civicoResidenza + '\'' +
                ", capResidenza='" + capResidenza + '\'' +
                ", dataDiNascita=" + dataDiNascita +
                ", inizioDisponibilita=" + inizioDisponibilita +
                ", fineDisponibilita=" + fineDisponibilita +
                ", personaUrgente=" + personaUrgente +
                ", comuni=" + comuni +
                ", lingueParlate=" + lingueParlate +
                ", mansioni effettuate=" + specializzazioni +
                ", automunito=" + automunito +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if(o instanceof Lavoratore other){
            return nome.equals(other.nome) && cognome.equals(other.cognome) && dataDiNascita.equals(((Lavoratore) o).dataDiNascita);
        }
        return false;
    }

    public SortedSet<String> getComuni(){
        return comuni;
    }
    public List<String> getPatente() {
        return patenti;
    }
    public String getCognome(){
        return cognome;
    }
    public String getNome(){
        return nome;
    }
    public String getLuogoDiNascita(){return luogoDiNascita;}
    public LocalDate getInizioDisponibilita(){
        return inizioDisponibilita;
    }
    public LocalDate getFineDisponibilita(){
        return fineDisponibilita;
    }
    public boolean getAutomunito(){
        return automunito;
    }
    public String getMansione(){
        return mansione;
    }
    public void AggiungiLavoro(Lavoro lavoro){
        lavori.add(lavoro);
    }

    public List<String> getLingueParlate() {
        return lingueParlate;
    }
    public LocalDate getDataDiNascita(){
        return dataDiNascita;
    }

    public String getStringComuni(){
        String com = comuni.toString();
        com = com.substring(1,com.length()-1);
        return com;
    }
    public String getStringLingue(){
        String ling = lingueParlate.toString();
        ling = ling.substring(1,ling.length()-1);
        return ling;
    }
    public List<String> getSpecializzazioni(){
        return specializzazioni;
    }
    public String getStringSpecializzazioni(){
        String mansioni = specializzazioni.toString();
        mansioni = mansioni.substring(1,mansioni.length()-1);
        return mansioni;
    }
    public String getStringPatente(){
        String patente = patenti.toString();
        patente = patente.substring(1,patente.length()-1);
        return patente;
    }
    public String getDisponibilita(){
        return inizioDisponibilita.toString() + " / " + fineDisponibilita.toString();
    }
    public String getCittaResidenza(){
        return cittaResidenza;
    }
    public List<Lavoro> OttieniLavori(){
        return lavori;
    }

}
