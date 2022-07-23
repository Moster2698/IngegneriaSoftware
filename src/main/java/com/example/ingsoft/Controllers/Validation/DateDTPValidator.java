package com.example.ingsoft.Controllers.Validation;

import javafx.scene.control.DatePicker;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class DateDTPValidator implements  Validate{
    List<DatePicker> dtpSingoli;
    HashMap<DatePicker,DatePicker> dtpPrePost;
    public DateDTPValidator(){
        dtpSingoli = new ArrayList<>();
        dtpPrePost = new HashMap<>();
    }

    public void add(DatePicker dtp){
        dtpSingoli.add(dtp);
    }
    public void add(DatePicker pre,DatePicker post){
        dtpPrePost.put(pre,post);
    }

    /***
     * Controlla un singolo DatePicker, se l'anno è minore di 16 o maggiore di 67 va in errore
     * @return Se DatePicker singolo è valido
     */
    private boolean controllaDatePickerData(){
        boolean flag = true;
        for(DatePicker dtp : dtpSingoli){
            int age = calcolaEta(dtp.getValue(), LocalDate.now());
            if(dtp.getValue() == null || (age< 16 || age>67)){
                flag = false;
                dtp.setStyle(cssRedBorder);
            }
            else{
                dtp.setStyle("-fx-border-color: transparent transparent  #c9d1de transparent; -fx-background-color: transparent;");
            }
        }
        return flag;
    }

    /***
     * Controlla se due DatePicker hanno i dati correttemente inseriti. Il DatePicker precedente deve avere una data minore di quello successivo, inoltre le due date non devono essere nulle.
     * @return Se le coppie dei DatePicker sono valide
     */
    private  boolean controllaDueDatePicker(){
        boolean flag = true;
        DatePicker dtpInizioLavoro, dtpFineLavoro;
        for(Map.Entry<DatePicker,DatePicker> entry : dtpPrePost.entrySet()){
             dtpInizioLavoro = entry.getKey();
             dtpFineLavoro = entry.getValue();
             if(dtpFineLavoro.getValue() == null || dtpInizioLavoro.getValue() == null){
                 if(dtpInizioLavoro.getValue()==null)
                     dtpInizioLavoro.setStyle(cssRedBorder);
                 if(dtpFineLavoro.getValue()==null)
                     dtpFineLavoro.setStyle(cssRedBorder);
                 flag = false;
             }
            else {
                  if(dtpInizioLavoro.getValue().compareTo(LocalDate.now()) < 0 || dtpInizioLavoro.getValue().compareTo(dtpFineLavoro.getValue()) >= 0){
                     flag = false;
                     dtpInizioLavoro.setStyle(cssRedBorder);
                     dtpFineLavoro.setStyle(cssRedBorder);
                 }
                 else
                 {
                     dtpInizioLavoro.setStyle("-fx-border-color: transparent transparent  #c9d1de transparent; -fx-background-color: transparent;");
                     dtpFineLavoro.setStyle("-fx-border-color: transparent transparent  #c9d1de transparent; -fx-background-color: transparent;");
                 }
             }
        }
        return flag;
    }
    @Override
    public boolean validate() {
        boolean checkSingoli = controllaDatePickerData();
        boolean checkPrePost = controllaDueDatePicker();
        return  checkSingoli && checkPrePost;
    }

    private int calcolaEta(LocalDate birthDate, LocalDate currentDate) {
        if ((birthDate != null) && (currentDate != null)) {
            return Period.between(birthDate, currentDate).getYears();
        } else {
            return 0;
        }
    }
}
