package com.example.ingsoft.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ComuniProvider {
    private static ComuniProvider instance = new ComuniProvider();
    private ObservableList<String> listaComuni = comuni();

    private ComuniProvider() {
    }

    public static ComuniProvider getInstance() {
        return instance;
    }
    public ObservableList<String> getListaComuni(){
        return listaComuni;
    }
    private ObservableList<String> comuni() {
        try {
            ObservableList<String> com = FXCollections.observableArrayList();
            String line;
            //parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader("comuni.csv"));
            while ((line = br.readLine()) != null)
            //returns a Boolean value
            {
                com.add(line.split(";;")[0].split(";")[6]);
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
