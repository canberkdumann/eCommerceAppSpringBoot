package com.example.eCommerceApp.exception;

public class EmailAlreadyPresentException extends Exception{

    public EmailAlreadyPresentException(String message){
        super(message);
    }
}
