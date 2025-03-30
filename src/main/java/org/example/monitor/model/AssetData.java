package org.example.monitor.model;

import lombok.*;

import java.math.BigDecimal;


@Data
public class AssetData {
    private String name;
    private String symbol;
    private int decimals;
    private String contractAddress;
    private BigDecimal balance;
    private BigDecimal usdValue;

    public AssetData(String name, String symbol, int decimals, String contractAddress, BigDecimal balance, BigDecimal usdValue) {
        this.name = name;
        this.symbol = symbol;
        this.decimals = decimals;
        this.contractAddress = contractAddress;
        this.balance = balance;
        this.usdValue = usdValue;
    }
}
