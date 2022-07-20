package com.example.ingsoft.Controllers.Validation;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.util.List;

public class Validator implements  Validate {
    private NumberTFValidator numberTFValidator;
    private StringTFValidator stringTFValidator;
    private DateDTPValidator dateDTPValidator;
    public Validator(){
        numberTFValidator = new NumberTFValidator();
        stringTFValidator = new StringTFValidator();
        dateDTPValidator = new DateDTPValidator();
    }
    public void add(List<TextField> textFields){
        stringTFValidator.add(textFields);
    }
    public void add(TextField textField, int length){
        numberTFValidator.add(textField,length,false);
    }
    public void add(TextField textField, int length,boolean canBeEmpty){
        numberTFValidator.add(textField,length,canBeEmpty);
    }
    public void add(DatePicker dtpIn){
        dateDTPValidator.add(dtpIn);
    }
    public void add(DatePicker dtpPre,DatePicker dtpPost){
        dateDTPValidator.add(dtpPre,dtpPost);
    }
    @Override
    public boolean validate() {
        boolean n1 = numberTFValidator.validate();
        boolean n2 = stringTFValidator.validate();
        boolean n3 = dateDTPValidator.validate();
        return n1 && n2 && n3;
    }
}
