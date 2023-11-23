package com.example.marlonmania.exceptions;

public class LoopsNotAllowedException extends Exception {
    public LoopsNotAllowedException(String message) {
        super(message);
    }
}