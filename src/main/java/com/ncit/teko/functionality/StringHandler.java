package com.ncit.teko.functionality;

public class StringHandler {

    public static String converToTitleCase(String word){
        char c = word.toUpperCase().charAt(0);
        return c+word.toLowerCase().substring(1);
    }

    public static String arrayToCsv(String[] arr){
        return String.join(",", arr);
    }
    
}
