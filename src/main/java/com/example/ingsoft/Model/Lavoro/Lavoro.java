package com.example.ingsoft.Model.Lavoro;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

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
    public LocalDate OttieniDataInizioLavoro(){
        return inizioDataLavoro;
    }
    public LocalDate OttieniDataFineLavoro(){
        return fineDataLavoro;
    }
    public String OttieniNomeAzienda(){
        return nomeAzienda;
    }
    public String OttieniMansioneSvolte(){
        return mansioneSvolta;
    }
    public String OttieniLuogoLavoro(){
        return luogoLavoro;
    }
    public int OttieniRetribuzioneOrariaLorda(){
        return  retribuzioneGiornaliera;
    }
    public String OttieniPeriodo(){
        return OttieniDataInizioLavoro().toString() + " / " + OttieniDataFineLavoro().toString();
    }
}
