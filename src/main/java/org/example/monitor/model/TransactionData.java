package org.example.monitor.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data  // Generates getters, setters, toString(), equals(), and hashCode()
@AllArgsConstructor  // Generates a constructor with all fields
@NoArgsConstructor   // Generates a no-args constructor (needed for serialization)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionData {
    private String hash;
    private String from;
    private String to;
    private BigDecimal value;
    private boolean isSwap;
}

