package com.example.ingsoft.Model.Persona;


import java.io.Serializable;

public abstract class Persona implements Serializable {
    protected String nome, cognome;
    public Persona(String nome, String cognome){
        this.nome = nome;
        this.cognome = cognome;
    }

}
