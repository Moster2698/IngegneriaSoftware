package com.example.ingsoft.Model.Lavoro;

import javafx.collections.ObservableList;

import java.util.List;

public interface LavoroDao {
    public  ObservableList<Lavoro> getLavori();
    public void add(Lavoro lavoro);
    public void add(List<Lavoro> lavori);
}
