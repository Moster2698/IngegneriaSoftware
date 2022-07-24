package com.example.ingsoft.Controllers.TextFormatters;

import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;

class TextFormatterString {
    public TextFormatter ottieniTextFormatter(){
        return new TextFormatter(soloCaratteri);
    }

    /***
     * Controlal che i dati inseriti siano solo Stringhes
     */
    private UnaryOperator<TextFormatter.Change> soloCaratteri = change -> {
        String text = change.getText();

        if (text.matches("[a-zA-Z\s]*")) {
            return change;
        }

        return null;
    };

}
