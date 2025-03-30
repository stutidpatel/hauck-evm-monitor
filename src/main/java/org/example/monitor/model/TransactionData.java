package org.example.monitor.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.math.BigDecimal;

@Data
public class TransactionData {
    private String hash;
    private String from;
    private String to;
    private BigDecimal value;
    private boolean isSwap;

    public TransactionData(String hash, String from, String to, BigDecimal value, boolean isSwap) {
        this.hash = hash;
        this.from = from;
        this.to = to;
        this.value = value;
        this.isSwap = isSwap;
    }
}

