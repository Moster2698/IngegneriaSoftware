package com.example.ingsoft.Controllers.Validation;

import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class StringTFValidator implements  Validate{
    private final List<TextField> textFields;
    public StringTFValidator(){
        textFields = new ArrayList<>();
    }
    public void add(List<TextField> textFields)
    {
        this.textFields.addAll(textFields);
    }
    @Override
    /*
      Controllo che tutti i textFields sono non nulli e che ci sia un valore al loro interno
     */
    public boolean validate() {
        boolean flag = true;
        for(TextField tf : textFields){
            if(tf.getText().isEmpty() || tf.getText().isBlank())
            {
                flag = false;
                tf.setStyle(cssRedBorder);
            }
            else
            {
                if(tf.getStyle().equals(cssRedBorder))
                    tf.setStyle("");
            }
        }
        return flag;
    }
}
