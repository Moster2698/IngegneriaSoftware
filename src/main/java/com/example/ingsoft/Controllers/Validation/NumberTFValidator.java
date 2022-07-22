package com.example.ingsoft.Controllers.Validation;

import javafx.scene.control.TextField;

import java.util.*;

public class NumberTFValidator implements  Validate{
    private final HashMap<TextField, Integer> numberToLength;
    private final HashMap<TextField,Boolean> textFieldEmpty;
    private TextField textField;

    public NumberTFValidator(){
        numberToLength = new HashMap<TextField,Integer>();
        textFieldEmpty = new HashMap<TextField, Boolean>();
    }
    public void add(TextField textField, int desiredLength){
        add(textField,desiredLength,false);
    }
    public void add(TextField textField, int desiredLength,boolean canBeEmpty){
        numberToLength.put(textField,desiredLength);
        textFieldEmpty.put(textField,canBeEmpty);
    }

    /***
     *
     * @return se i numeri sono corretti e sono della dimensione desiderata true, false altrimenti. Se length = -1, la lunghezza non viene controllata
     */
    @Override
    public boolean validate() {
        boolean isValid = true;
        for (Map.Entry<TextField, Integer> entry : numberToLength.entrySet()) {
            TextField tel = entry.getKey();
            boolean flag = textFieldEmpty.get(tel);
            int length = entry.getValue();
            if (!flag) {
                if (tel.getText().trim().isBlank() || tel.getText().trim().isEmpty()) {
                    isValid = false;
                    tel.setStyle(cssRedBorder);
                } else {
                    if (length != -1 && tel.getText().length() != length) {
                        tel.setStyle(cssRedBorder);
                        isValid = false;
                    } else {
                        tel.setStyle("-fx-border-color: transparent transparent  #c9d1de transparent; -fx-background-color: transparent;");
                    }
                }
            }
            /*
            if(!flag || (flag && !tel.getText().trim().isEmpty() && !tel.getText().trim().isBlank())) {

                if (length!=-1 && tel.getText().length() != length) {
                    tel.setStyle(cssRedBorder);
                    isValid = false;
                } else {
                    tel.setStyle("-fx-border-color: transparent transparent  #c9d1de transparent; -fx-background-color: transparent;");
                }
            }
        }
        return isValid;
        */
        }
        return  isValid;
    }
}
