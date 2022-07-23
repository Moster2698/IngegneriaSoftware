package com.example.ingsoft.Model.Persona;

import java.util.List;

public interface DipendenteDao {
    void aggiungiDipendente(Dipendente dipendente);
    void rimuoviDipendente(Dipendente dipendente);
    List<Dipendente> ottieniDipendenti();
}
