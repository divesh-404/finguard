package com.finguard.finguard.transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class FraudScorer {
    public static final BigDecimal DEFAULT_THRESHOLD = new BigDecimal("10000");
    public List<Transaction> filterHighValueTransactions(List<Transaction> transactions, BigDecimal threshold){
        if(transactions==null || threshold==null){
            throw new IllegalArgumentException("Transactions list cannot be nuu");
        }
        return transactions.stream()
                            .filter(txn->txn.getAmount().compareTo(threshold)>0)
                            .collect(Collectors.toList());
        
    }
}
