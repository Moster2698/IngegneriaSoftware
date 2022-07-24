package com.example.ingsoft.Controllers.Validation;

import javafx.scene.control.ComboBox;

import java.util.ArrayList;
import java.util.List;

class ComboBoxValidator implements Validate {
    private List<ComboBox> comboBoxList;
    public ComboBoxValidator(){
        comboBoxList = new ArrayList<>();
    }
    public void add(ComboBox comboBox){
        comboBoxList.add(comboBox);
    }

    /***
     * Controlla che il valore selezionato nella combobox non sia vuoto
     * @return Se la ComboBox è valida
     */
    @Override
    public boolean validate() {
        boolean isValid = true;
        for(ComboBox comboBox : comboBoxList){
            if(comboBox.getValue()==null)
            {
                isValid = false;
                comboBox.setStyle(cssRedBorder);
            }
            else{
                comboBox.setStyle("-fx-border-color: transparent transparent  #c9d1de transparent; -fx-background-color: transparent;");
            }
        }
        return isValid;
    }
}
