package com.example.marlonmania.exceptions;

public class graphExceptions extends Throwable {


    public static class GraphException extends Exception {
        public GraphException(String message) {
            super(message);
        }
    }


}
