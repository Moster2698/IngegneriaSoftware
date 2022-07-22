package com.example.ingsoft.Model.Persona;

import java.time.LocalDate;
import java.util.List;

public interface LavoratoreDao {
     List<Lavoratore> getLavoratori();
     void add(Lavoratore lavoratore);
     void remove(Lavoratore lavoratore);
     List<Lavoratore> cercaLavoratori (String nome, String cognome, List<String> lingueParlate, LocalDate dataInizio, LocalDate dataFine, List<String> mansioni,
                                      List<String> zonaDisponibilita, String cittaResidenza,String automunito,String patente,boolean isOr );
}
