package com.example.ingsoft.Model.Lavoro;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

public class Lavoro  implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final Date startWork;
    private final Date endWork;
    private final String nomeAzienda;
    private final String mansioniSvolte;
    private final String luogoLavoro;
    private final int retribuzioneGiornaliera;
    public Lavoro(Date startWork, Date endWork, String nomeAzienda,String luogoLavoro, String mansioniSvolte, int retribuzioneGiornaliera){
        this.startWork = startWork;
        this.endWork = endWork;
        this.nomeAzienda = nomeAzienda;
        this.mansioniSvolte = mansioniSvolte;
        this.luogoLavoro = luogoLavoro;
        this.retribuzioneGiornaliera = retribuzioneGiornaliera;
    }

    @Override
    public String toString() {
        return "Lavoro{" +
                "startWork=" + startWork +
                ", endWork=" + endWork +
                ", nomeAzienda='" + nomeAzienda + '\'' +
                ", mansioniSvolte='" + mansioniSvolte + '\'' +
                ", luogoLavoro='" + luogoLavoro + '\'' +
                ", retribuzioneGiornaliera=" + retribuzioneGiornaliera +
                '}';
    }
}
