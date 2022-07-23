package com.example.ingsoft.Model.Persona;

import java.io.Serial;
import java.io.Serializable;

public class PersonaUrgente extends Persona implements  Serializable {
    @Serial
    private static final long serialVersionUID = 4L;
    private final String numeroTelefono;
    private final String email;

    /***
     * Crea una persona urgente
     * @param nome  Nome della persona urgente
     * @param cognome Cognome della persona urgente
     * @param numeroTelefono Numero di telefono della persona urgente
     * @param email Email associata alla persona urgente
     */
    public PersonaUrgente(String nome,String cognome,String numeroTelefono, String email){
        super(nome,cognome);
        this.numeroTelefono = numeroTelefono;
        this.email = email;
    }

    /***
     * Ottieni il numero di telefono
     * @return Numero di telefono della persona urgente
     */
    public String getNumeroTelefono(){
        return numeroTelefono;
    }

    /***
     * Ottieni l'email
     * @return L'email della persona urgente
     */
    public String getEmail(){
        return email;
    }

}
