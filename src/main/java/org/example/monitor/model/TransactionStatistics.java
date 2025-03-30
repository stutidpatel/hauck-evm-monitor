package org.example.monitor.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;


@Data
public class TransactionStatistics {
    private long totalSwaps;
    private BigDecimal totalTransactionVolumeInUSD;
    private BigDecimal totalEthereumVolume;
    private List<TransactionData> transactionDataList;

    public TransactionStatistics(long totalSwaps, BigDecimal totalTransactionVolumeInUSD, BigDecimal totalEthereumVolume, List<TransactionData> transactionDataList) {
        this.totalSwaps = totalSwaps;
        this.totalTransactionVolumeInUSD = totalTransactionVolumeInUSD;
        this.totalEthereumVolume = totalEthereumVolume;
        this.transactionDataList = transactionDataList;
    }
}
