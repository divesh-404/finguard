package com.finguard.finguard.transaction;

import org.junit.jupiter.api.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    @Test
    @DisplayName("Valid transaction creation succeeds")
    void testValidTransactionCreation() {
        BigDecimal amount = new BigDecimal("5000.00");
        LocalDateTime now = LocalDateTime.now();
        Transaction tx = new Transaction("TXN001", "USER123", amount, now, "Mumbai", TransactionType.DEBIT);
        
        assertNotNull(tx);
        assertEquals("TXN001", tx.getTxnId());
        assertEquals(amount, tx.getAmount());
    }

    @Test
    @DisplayName("Null txnId throws IllegalArgumentException")
    void testNullTxnIdThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Transaction(null, "USER123", new BigDecimal("1000"), 
                           LocalDateTime.now(), "Delhi", TransactionType.CREDIT);
        });
    }

    @Test
    @DisplayName("Negative amount triggers InvalidTransactionException")
    void testNegativeAmountThrowsInvalidTransactionException() {
        Transaction tx = new Transaction("TXN002", "USER456", new BigDecimal("-100"), 
                                         LocalDateTime.now(), "Bangalore", TransactionType.TRANSFER);
        assertThrows(InvalidTransactionException.class, () -> {
            TransactionValidator.validate(tx);
        });
    }

    @Test
    @DisplayName("FraudScorer filters high-value transactions")
    void testFraudScorerFiltersHighValueTransactions() {
        FraudScorer scorer = new FraudScorer();
        BigDecimal threshold = new BigDecimal("10000");
        
        List<Transaction> transactions = List.of(
            new Transaction("1", "U1", new BigDecimal("5000"), LocalDateTime.now(), "A", TransactionType.DEBIT),
            new Transaction("2", "U2", new BigDecimal("15000"), LocalDateTime.now(), "B", TransactionType.CREDIT),
            new Transaction("3", "U3", new BigDecimal("20000"), LocalDateTime.now(), "C", TransactionType.DEBIT),
            new Transaction("4", "U4", new BigDecimal("8000"), LocalDateTime.now(), "D", TransactionType.TRANSFER),
            new Transaction("5", "U5", new BigDecimal("10000"), LocalDateTime.now(), "E", TransactionType.CREDIT),
            new Transaction("6", "U6", new BigDecimal("30000"), LocalDateTime.now(), "F", TransactionType.DEBIT),
            new Transaction("7", "U7", new BigDecimal("4000"), LocalDateTime.now(), "G", TransactionType.DEBIT),
            new Transaction("8", "U8", new BigDecimal("12000"), LocalDateTime.now(), "H", TransactionType.CREDIT),
            new Transaction("9", "U9", new BigDecimal("9500"), LocalDateTime.now(), "I", TransactionType.DEBIT),
            new Transaction("10","U10", new BigDecimal("11000"), LocalDateTime.now(), "J", TransactionType.TRANSFER)
        );
        
        List<Transaction> highValue = scorer.filterHighValueTransactions(transactions, threshold);
        assertEquals(5, highValue.size());
        for (Transaction tx : highValue) {
            assertTrue(tx.getAmount().compareTo(threshold) > 0);
        }
    }

    @Test
    @DisplayName("FraudScorer returns empty when none exceed threshold")
    void testFraudScorerReturnsEmptyWhenNoneExceed() {
        FraudScorer scorer = new FraudScorer();
        BigDecimal threshold = new BigDecimal("10000");
        
        List<Transaction> transactions = List.of(
            new Transaction("1", "U1", new BigDecimal("5000"), LocalDateTime.now(), "A", TransactionType.DEBIT),
            new Transaction("2", "U2", new BigDecimal("8000"), LocalDateTime.now(), "B", TransactionType.CREDIT)
        );
        
        List<Transaction> result = scorer.filterHighValueTransactions(transactions, threshold);
        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("FraudScorer has default threshold of 10000")
    void testDefaultThresholdValue() {
        assertEquals(new BigDecimal("10000"), FraudScorer.DEFAULT_THRESHOLD);
    }
}
