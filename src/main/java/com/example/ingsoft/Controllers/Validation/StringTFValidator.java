package com.example.ingsoft.Controllers.Validation;

import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

class StringTFValidator implements  Validate{
    private final List<TextField> textFields;
    public StringTFValidator(){
        textFields = new ArrayList<>();
    }
    public void add(List<TextField> textFields)
    {
        this.textFields.addAll(textFields);
    }

    /***
     * Controlla che tutte le textField non siano vuote o che non esistano
     * @return Se le TextFields sono valide
     */
    @Override
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
                    tf.setStyle("-fx-border-color: transparent transparent  #c9d1de transparent; -fx-background-color: transparent;");
            }
        }
        return flag;
    }

    public void add(TextField tf) {
        textFields.add(tf);
    }
}
