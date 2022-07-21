package com.example.ingsoft.Model.guiData;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;

public class ComuniProvider {
    private final static ComuniProvider instance = new ComuniProvider();
    private final ObservableList<String> listaComuni = comuni();

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
            com.sort(Comparator.naturalOrder());
            return com;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

}
