package com.example.ingsoft.Model.Persona;

import java.io.Serial;
import java.io.Serializable;

public class PersonaUrgente extends Persona implements  Serializable {
    @Serial
    private static final long serialVersionUID = 4L;
    private final String numeroTelefono;
    private final String email;
    public PersonaUrgente(String nome,String cognome,String numeroTelefono, String email){
        super(nome,cognome);
        this.numeroTelefono = numeroTelefono;
        this.email = email;
    }

}
