package com.finguard.finguard.transaction;

public class InvalidTransactionException extends Exception {
    public InvalidTransactionException(String message){
        super(message);
    }
}
