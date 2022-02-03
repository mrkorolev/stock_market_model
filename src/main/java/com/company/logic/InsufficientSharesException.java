package com.company.logic;

public class InsufficientSharesException extends Exception{
    public InsufficientSharesException(String msg){
        super(msg);
    }
}
