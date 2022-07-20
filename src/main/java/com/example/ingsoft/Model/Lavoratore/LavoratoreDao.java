package com.example.ingsoft.Model.Lavoratore;

import com.example.ingsoft.Model.Lavoratore.Lavoratore;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.List;

public interface LavoratoreDao {
    public List<Lavoratore> getLavoratori();
    public void add(Lavoratore lavoratore);
    public void remove(Lavoratore lavoratore);
    public List<Lavoratore> research (String nome, String cognome, List<String> lingueParlate, LocalDate dataInizio, LocalDate dataFine, String mansione,
                                      List<String> zonaDisponibilita, String cittaResidenza,boolean automunito,String patente );
}
