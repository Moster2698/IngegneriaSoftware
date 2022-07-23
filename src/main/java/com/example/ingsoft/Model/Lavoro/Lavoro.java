package com.example.ingsoft.Model.Lavoro;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class Lavoro  implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private  LocalDate inizioDataLavoro;
    private  LocalDate fineDataLavoro;
    private  String nomeAzienda;
    private String mansioneSvolta;
    private  String luogoLavoro;
    private  int retribuzioneGiornaliera;
    public Lavoro(LocalDate inizioDataLavoro, LocalDate fineDataLavoro, String nomeAzienda,String luogoLavoro, String mansioneSvolta, int retribuzioneGiornaliera){
        this.inizioDataLavoro = inizioDataLavoro;
        this.fineDataLavoro = fineDataLavoro;
        this.nomeAzienda = nomeAzienda;
        this.mansioneSvolta = mansioneSvolta;
        this.luogoLavoro = luogoLavoro;
        this.retribuzioneGiornaliera = retribuzioneGiornaliera;
    }

    @Override
    public String toString() {
        return "Lavoro{" +
                "startWork=" + inizioDataLavoro +
                ", endWork=" + fineDataLavoro +
                ", nomeAzienda='" + nomeAzienda + '\'' +
                ", mansioniSvolte='" + mansioneSvolta + '\'' +
                ", luogoLavoro='" + luogoLavoro + '\'' +
                ", retribuzioneGiornaliera=" + retribuzioneGiornaliera +
                '}';
    }
    public LocalDate ottieniDataInizioLavoro(){
        return inizioDataLavoro;
    }
    public LocalDate ottieniDataFineLavoro(){
        return fineDataLavoro;
    }
    public String ottieniNomeAzienda(){
        return nomeAzienda;
    }
    public String ottieniMansioneSvolte(){
        return mansioneSvolta;
    }
    public String ottieniLuogoLavoro(){
        return luogoLavoro;
    }
    public int ottieniRetribuzioneOrariaLorda(){
        return  retribuzioneGiornaliera;
    }
    public void cambiaNomeAzienda(String nomeAzienda){
        this.nomeAzienda = nomeAzienda;
    }
    public void cambiaMansioneSvolta(String mansioneSvolta){
        this.mansioneSvolta = mansioneSvolta;
    }
    public void cambiaLuogoLavoro(String luogoLavoro){
        this.luogoLavoro = luogoLavoro;
    }
    public void cambiaRetribuzione(int retribuzioneGiornaliera){
        this.retribuzioneGiornaliera = retribuzioneGiornaliera;
    }
    public void cambiaDataInizio(LocalDate inizioDataLavoro){
        this.inizioDataLavoro = inizioDataLavoro;
    }
    public void cambiaDataFine(LocalDate fineDataLavoro){
        this.fineDataLavoro = fineDataLavoro;
    }
    public String ottieniPeriodo(){
        return ottieniDataInizioLavoro().toString() + " / " + ottieniDataFineLavoro().toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof  Lavoro){
            Lavoro otherLavoro = (Lavoro) obj;
            return luogoLavoro.equalsIgnoreCase(((Lavoro) obj).luogoLavoro) && nomeAzienda.equals(otherLavoro.nomeAzienda)
                    && retribuzioneGiornaliera == otherLavoro.retribuzioneGiornaliera && fineDataLavoro.equals(otherLavoro.fineDataLavoro)
                    && inizioDataLavoro.equals(otherLavoro.inizioDataLavoro);
        }
        return false;
    }
}
