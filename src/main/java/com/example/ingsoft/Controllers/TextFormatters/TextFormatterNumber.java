package com.example.ingsoft.Controllers.TextFormatters;

import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;

class TextFormatterNumber {
    public TextFormatter OttieniTextFormatter(){
        return new TextFormatter(soloNumeri);
    }
    private UnaryOperator<TextFormatter.Change> soloNumeri = change -> {
        String text = change.getText();

        if (text.matches("[0-9]*")) {
            return change;
        }

        return null;
    };
}
