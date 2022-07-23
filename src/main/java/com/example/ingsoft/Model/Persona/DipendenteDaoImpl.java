package com.example.ingsoft.Model.Persona;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DipendenteDaoImpl implements DipendenteDao {
    private List<Dipendente> dipendenti;
    public DipendenteDaoImpl(){
        dipendenti = deSerializzaFile();
    }
    public List<Dipendente> deSerializzaFile(){
        try{
            FileInputStream fileInputStream
                    = new FileInputStream("dipendenti.txt");
            ObjectInputStream objectInputStream
                    = new ObjectInputStream(fileInputStream);
            return (List<Dipendente>) objectInputStream.readObject();
        }
        catch(EOFException e){
            return new ArrayList<>();
        }
        catch(IOException e){
            System.out.println(e);
        }
        catch(ClassNotFoundException e){
            System.out.println(e);
        }
        return null;
    }

    public void aggiungiDipendente(Dipendente dipendente){
        dipendenti.add(dipendente);
        salvaSuFile();
    }

    @Override
    public void rimuoviDipendente(Dipendente dipendente) {
        dipendenti.remove(dipendente);
        salvaSuFile();
    }

    public List<Dipendente> ottieniDipendenti(){
        return dipendenti;
    }

    public void salvaSuFile(){
        try {
            FileOutputStream fileOutputStream
                    = new FileOutputStream("dipendenti.txt");
            ObjectOutputStream objectOutputStream
                    = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(dipendenti);
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
