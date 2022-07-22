package com.example.ingsoft.Controllers.TextFormatters;

import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;

public class TextFormatterString {
    public TextFormatter OttieniTextFormatter(){
        return new TextFormatter(soloNumeri);
    }
    private UnaryOperator<TextFormatter.Change> soloNumeri = change -> {
        String text = change.getText();

        if (text.matches("[a-zA-Z]*")) {
            return change;
        }

        return null;
    };
}
