package com.finguard.finguard.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private String txnId;
    private String userId;
    private BigDecimal amount;
    private LocalDateTime timestamp;
    private String locationCity;
    private TransactionType type;

    public Transaction(String txnId, String userId, BigDecimal amount, LocalDateTime timestamp, String locationCity,
            TransactionType type) {

                if(txnId==null){
                    throw new IllegalArgumentException("Transaction ID cannot be null");
                }
                if(amount==null){
                    throw new IllegalArgumentException("Amount cannot be null");
                }
                this.txnId = txnId;
                this.userId = userId;
                this.amount = amount;
                this.timestamp = timestamp;
                this.locationCity = locationCity;
                this.type = type;
    }

    public String getTxnId() {
        return txnId;
    }

    public String getUserId() {
        return userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getLocationCity() {
        return locationCity;
    }

    public TransactionType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Transaction [txnId=" + txnId + ", amount=" + amount + ", type=" + type + "]";
    }

    
}
