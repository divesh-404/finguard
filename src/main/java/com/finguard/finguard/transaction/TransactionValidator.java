package com.finguard.finguard.transaction;

import java.math.BigDecimal;

public class TransactionValidator {
    public static void validate(Transaction transaction) throws InvalidTransactionException{
        if(transaction==null){
            throw new InvalidTransactionException("Transaction cannot be null");
        }
        if(transaction.getAmount().compareTo(BigDecimal.ZERO)< 0){
            throw new InvalidTransactionException("Transaction ammont cannot be negative");
        }
    }
}
