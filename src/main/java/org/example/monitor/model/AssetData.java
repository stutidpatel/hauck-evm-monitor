package org.example.monitor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetData {
    private String name;
    private String symbol;
    private int decimals;
    private String contractAddress;
    private BigDecimal balance;
    private BigDecimal usdValue;
}
