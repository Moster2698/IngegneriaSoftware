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

    /***
     *
     * @return Data primo giorno di lavoro
     */
    public LocalDate ottieniDataInizioLavoro(){
        return inizioDataLavoro;
    }

    /***
     *
     * @return Data ultimo giorno di lavoro
     */
    public LocalDate ottieniDataFineLavoro(){
        return fineDataLavoro;
    }

    /***
     *
     * @return Nome azienda per cui si ha lavorato
     */
    public String ottieniNomeAzienda(){
        return nomeAzienda;
    }

    /***
     *
     * @return Ottieni le mansioni svolte all'interno dell'azienda
     */
    public String ottieniMansioneSvolte(){
        return mansioneSvolta;
    }

    /***
     *
     * @return Comune dove si ha lavorato
     */
    public String ottieniLuogoLavoro(){
        return luogoLavoro;
    }

    /***
     *
     * @return RetribuzioneGiornaliera
     */
    public int ottieniRetribuzioneGiornalieraLorda(){
        return  retribuzioneGiornaliera;
    }

    /***
     * Modifica nome dell'azienda
     * @param nomeAzienda Nome Azienda
     */
    public void cambiaNomeAzienda(String nomeAzienda){
        this.nomeAzienda = nomeAzienda;
    }

    /**
     * Modifica la mansione svolta
     * @param mansioneSvolta Mansione svolta all'interno dell'azienda
     */
    public void cambiaMansioneSvolta(String mansioneSvolta){
        this.mansioneSvolta = mansioneSvolta;
    }

    /**
     * Modifica il luogo di lavoro
     * @param luogoLavoro Luogo di lavoro aggiornato
     */
    public void cambiaLuogoLavoro(String luogoLavoro){
        this.luogoLavoro = luogoLavoro;
    }

    /***
     * Modifica la retribuzione giornaliera
     * @param retribuzioneGiornaliera Retribuzione Giornaliera
     */
    public void cambiaRetribuzione(int retribuzioneGiornaliera){
        this.retribuzioneGiornaliera = retribuzioneGiornaliera;
    }

    /***
     * Modifica data inizio lavoro
     * @param inizioDataLavoro Giorno di inizio del lavoro
     */
    public void cambiaDataInizio(LocalDate inizioDataLavoro){
        this.inizioDataLavoro = inizioDataLavoro;
    }

    /**
     * Modifica data fine lavoro
     * @param fineDataLavoro  Giorno di fine del lavoro
     */
    public void cambiaDataFine(LocalDate fineDataLavoro){
        this.fineDataLavoro = fineDataLavoro;
    }

    /***
     * Ottieni periodo per cui si ha lavorato
     * @return Periodo di lavoro
     */
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
