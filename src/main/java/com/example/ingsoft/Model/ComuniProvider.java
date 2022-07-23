package com.example.ingsoft.Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class ComuniProvider {
    private final static ComuniProvider instance = new ComuniProvider();
    private final List<String> listaComuni = leggiComuniDaFile();

    /***
     * Costruttore privato di base
     */
    private ComuniProvider() {
    }

    /***
     * Ritorna l'istanza della classe
     * @return oggetto che rappresenta l'istanza della classe
     */
    public static ComuniProvider getInstance() {
        return instance;
    }

    /***
     * Ritorna una lista con tutti i comuni di Italia
     * @return lista di stringhe
     */
    public List<String> ottieniListaComuni(){
        return listaComuni;
    }

    /***
     * Legge dal file comuni.csv ogni riga, ottiene il nome del comune e lo inserisce in una lista
     * @return lista contente tutti i comuni letti dal file comuni.csv
     */
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
