package com.example.ingsoft.Controllers.TextFormatters;

import javafx.scene.control.TextFormatter;

public class TextFormatterFactory {
    private TextFormatterString textFormatterString;
    private TextFormatterNumber textFormatterNumber;
    public TextFormatterFactory(){
        textFormatterString = new TextFormatterString();
        textFormatterNumber = new TextFormatterNumber();
    }
    public TextFormatter OttieniTextFormatter(String formatter){
        if(formatter.equals("string"))
            return textFormatterString.OttieniTextFormatter();
        if(formatter.equals("numero"))
            return textFormatterNumber.OttieniTextFormatter();
            return null;
    }
}
