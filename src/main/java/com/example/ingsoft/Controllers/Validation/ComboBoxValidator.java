package com.example.ingsoft.Controllers.Validation;

import javafx.scene.control.ComboBox;

import java.util.ArrayList;
import java.util.List;

public class ComboBoxValidator implements Validate {
    private List<ComboBox> comboBoxList;
    public ComboBoxValidator(){
        comboBoxList = new ArrayList<>();
    }
    public void add(ComboBox comboBox){
        comboBoxList.add(comboBox);
    }

    @Override
    public boolean validate() {
        boolean isValid = true;
        for(ComboBox comboBox : comboBoxList){
            String comboText = comboBox.getEditor().getText().trim();
            if(comboText.isEmpty() || comboText.isBlank() || comboText.chars().anyMatch(Character::isDigit) || comboBox.getSelectionModel().getSelectedItem()==null)
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
