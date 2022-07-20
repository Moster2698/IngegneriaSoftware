package com.example.ingsoft.Controllers.Validation;

import javafx.scene.control.DatePicker;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DateDTPValidator implements  Validate{
    List<DatePicker> dtpSingoli;
    HashMap<DatePicker,DatePicker> dtpPrePost = new HashMap<DatePicker,DatePicker>();
    public DateDTPValidator(){
        dtpSingoli = new ArrayList<DatePicker>();
        dtpPrePost = new HashMap<DatePicker,DatePicker>();
    }

    public void add(DatePicker dtp){
        dtpSingoli.add(dtp);
    }
    public void add(DatePicker pre,DatePicker post){
        dtpPrePost.put(pre,post);
    }
    private boolean checkSingoli(){
        boolean flag = true;
        for(DatePicker dtp : dtpSingoli){
            if(dtp.getValue()==null || dtp.getValue().getYear() > LocalDate.now().getYear()-18){
                flag = false;
                dtp.setStyle(cssRedBorder);
            }
            else{
                dtp.setStyle("");
            }
        }
        return flag;
    }
    private  boolean checkPrePost(){
        boolean flag = true;
        DatePicker dtpInizioLavoro, dtpFineLavoro;
        for(Map.Entry<DatePicker,DatePicker> entry : dtpPrePost.entrySet()){
             dtpInizioLavoro = entry.getKey();
             dtpFineLavoro = entry.getValue();
             if(dtpFineLavoro.getValue() == null || dtpInizioLavoro.getValue()==null){
                 if(dtpInizioLavoro.getValue()==null)
                     dtpInizioLavoro.setStyle(cssRedBorder);
                 if(dtpFineLavoro.getValue()==null)
                     dtpFineLavoro.setStyle(cssRedBorder);
                 flag = false;
             }
            else{
                  if(dtpInizioLavoro.getValue().compareTo(LocalDate.now())<0 || dtpInizioLavoro.getValue().compareTo(dtpFineLavoro.getValue()) >= 0){
                     flag = false;
                     dtpInizioLavoro.setStyle(cssRedBorder);
                 }
                 else
                 {
                     dtpInizioLavoro.setStyle("");
                 }
             }
        }
        return flag;
    }
    @Override
    public boolean validate() {

        boolean checkSingoli = checkSingoli();
        boolean checkPrePost = checkPrePost();
        return  checkSingoli && checkPrePost;
    }


}
