package com.example.ingsoft.Model.guiData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ComuniProvider {
    private final static ComuniProvider instance = new ComuniProvider();
    private final List<String> listaComuni = leggiComuniDaFile();

    private ComuniProvider() {
    }

    public static ComuniProvider getInstance() {
        return instance;
    }
    public List<String> ottieniListaComuni(){
        return listaComuni;
    }
    private List<String> leggiComuniDaFile() {
        try {
            List<String> com = new ArrayList<>();
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
