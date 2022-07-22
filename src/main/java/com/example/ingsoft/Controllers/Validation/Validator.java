package com.example.ingsoft.Controllers.Validation;

import com.example.ingsoft.Model.guiData.Lingua;
import com.example.ingsoft.Model.guiData.Mansione;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.util.List;

public class Validator {
    private NumberTFValidator numberTFValidator;
    private StringTFValidator stringTFValidator;
    private DateDTPValidator dateDTPValidator;
    private ComboBoxValidator comboBoxValidator;
    public Validator(){
        numberTFValidator = new NumberTFValidator();
        stringTFValidator = new StringTFValidator();
        dateDTPValidator = new DateDTPValidator();
        comboBoxValidator = new ComboBoxValidator();
    }
    public void addStringTextField(List<TextField> textFields){
        stringTFValidator.add(textFields);
    }
    public void addStringTextField(TextField textField){
        stringTFValidator.add(textField);
    }
    public void addNumberTextField(TextField textField, int length){
        numberTFValidator.add(textField,length,false);
    }
    public void addNumberTextField(TextField textField, int length,boolean canBeEmpty){
        numberTFValidator.add(textField,length,canBeEmpty);
    }
    public void addSingleDatePicker(DatePicker dtpIn){
        dateDTPValidator.add(dtpIn);
    }
    public void addDoubleDatePicker(DatePicker dtpPre,DatePicker dtpPost){
        dateDTPValidator.add(dtpPre,dtpPost);
    }
    public boolean validate() {
        boolean n1 = numberTFValidator.validate();
        boolean n2 = stringTFValidator.validate();
        boolean n3 = dateDTPValidator.validate();
        boolean n4 = comboBoxValidator.validate();
        return n1 && n2 && n3 && n4;
    }

    public void addComboBox(ComboBox comboBox){
        comboBoxValidator.add(comboBox);
    }
}
