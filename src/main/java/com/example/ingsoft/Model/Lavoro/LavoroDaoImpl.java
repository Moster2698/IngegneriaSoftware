package com.example.ingsoft.Model.Lavoro;

import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.List;

public class LavoroDaoImpl implements LavoroDao, Serializable {
    private ObservableList<Lavoro> listaLavori;

    @Override
    public ObservableList<Lavoro> getLavori() {
        return listaLavori;
    }

    @Override
    public void add(Lavoro lavoro) {
        listaLavori.add(lavoro);
    }

    @Override
    public void add(List<Lavoro> lavori) {

        for(Lavoro lavoro : lavori)
            listaLavori.add(lavoro);
    }
}
