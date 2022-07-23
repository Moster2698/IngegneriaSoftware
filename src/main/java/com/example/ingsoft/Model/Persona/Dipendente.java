package com.example.ingsoft.Model.Persona;

import java.io.Serializable;

public class Dipendente extends  Persona implements Serializable{
    private String nome,cognome,username,password,email,telefono;

    public Dipendente(String nome, String cognome, String username, String password, String email,String telefono){
        super(nome,cognome);
        this.username = username;
        this.password = password;
        this.email = email;
        this.telefono = telefono;
    }
    public String ottieniNome(){
        return nome;
    }
    public String ottieniCognome(){
        return cognome;
    }
    public String ottieniEmail(){
        return email;
    }
    public String ottieniTelefono(){
        return telefono;
    }
    public String ottieniUsername(){
        return username;
    }
    public String ottieniPassword(){
        return password;
    }

}
