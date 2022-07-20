package com.example.ingsoft.Controllers.Validation;

import javafx.scene.control.TextField;

import java.util.*;

public class NumberTFValidator implements  Validate{
    private final HashMap<TextField, Integer> numberToLength;
    private final HashMap<TextField,Boolean> textFieldEmpty;
    private final List<Boolean> flags;
    private TextField textField;

    public NumberTFValidator(){
        numberToLength = new HashMap<TextField,Integer>();
        textFieldEmpty = new HashMap<TextField, Boolean>();
        flags = new ArrayList<Boolean>();
    }
    public void add(TextField textField, int desiredLength){
        add(textField,desiredLength,false);
    }
    public void add(TextField textField, int desiredLength,boolean canBeEmpty){
        numberToLength.put(textField,desiredLength);
        textFieldEmpty.put(textField,canBeEmpty);
    }

    /***
     * Diamo per scontato che i campi esistano e non siano vuoti
     * @return se i numeri sono corretti e sono della dimensione desiderata true, false altrimenti
     */
    @Override
    public boolean validate() {
        boolean isValid = true;
        for(Map.Entry<TextField, Integer> entry : numberToLength.entrySet()){
            TextField tel = entry.getKey();
            boolean flag = textFieldEmpty.get(tel);
            int length = entry.getValue();
            if(!flag || (flag && !tel.getText().isEmpty())) {
                if (!tel.getText().chars().allMatch(Character::isDigit) || tel.getText().length() != length) {
                    tel.setStyle(cssRedBorder);
                    isValid = false;
                } else {
                    tel.setStyle("");
                }
            }
        }
        return isValid;
    }
    public void remove(TextField textField){
        numberToLength.remove(textField);
    }
}
