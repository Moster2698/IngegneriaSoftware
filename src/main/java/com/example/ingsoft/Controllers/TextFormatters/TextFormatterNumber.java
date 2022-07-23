package com.example.ingsoft.Controllers.TextFormatters;

import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;

class TextFormatterNumber implements  Formatter{
    public TextFormatter ottieniTextFormatter(){
        return new TextFormatter(soloNumeri);
    }

    /***
     * Controlla che i caratteri inseriti sono solo numeri
     */
    private UnaryOperator<TextFormatter.Change> soloNumeri = change -> {
        String text = change.getText();

        if (text.matches("[0-9]*")) {
            return change;
        }

        return null;
    };
}
