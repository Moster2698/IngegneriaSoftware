package com.example.ingsoft.Controllers.TextFormatters;

import javafx.scene.control.TextFormatter;

public class TextFormatterFactory {
    private TextFormatterString textFormatterString;
    private TextFormatterNumber textFormatterNumber;
    public TextFormatterFactory(){
        textFormatterString = new TextFormatterString();
        textFormatterNumber = new TextFormatterNumber();
    }

    /***
     * Crea un TextFormatter in base al parametro dato
     * @param formatter Stringa che identifica un TextFormatter, "string" rappresenta un TextFormatter che accetta
     *                  solo caratteri alfanumerici, "numero" solo numeri.
     * @return TextFormatter
     */
    public TextFormatter OttieniTextFormatter(String formatter){
        if(formatter.equals("string"))
            return textFormatterString.ottieniTextFormatter();
        if(formatter.equals("numero"))
            return textFormatterNumber.ottieniTextFormatter();
            return null;
    }
}
