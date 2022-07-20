package com.example.ingsoft.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LingueProvider {
    private static LingueProvider instance = new LingueProvider();
    private ObservableList<String> listaLingue = lingue();

    private LingueProvider() {
    }

    public static LingueProvider getInstance() {
        return instance;
    }
    public ObservableList<String> getListaLingue(){
        return listaLingue;
    }
    private ObservableList<String> lingue() {
        try {
            ObservableList<String> com = FXCollections.observableArrayList();
            String line;
            //parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader("lingue.csv"));
            while ((line = br.readLine()) != null)
            //returns a Boolean value
            {
                String[] lingue = line.split(";");
                for(String lingua : lingue)
                    com.add(lingua);
            }
            com.sort((o1, o2) -> o1.compareTo(o2));
            return com;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
