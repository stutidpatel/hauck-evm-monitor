package org.example.monitor.service;

import org.example.monitor.model.TransactionData;
import org.example.monitor.model.TransactionStatistics;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class EtherscanService {
    @Value("${etherscan.api.key}")
    private String ETHERSCAN_API_KEY;

    @Value("${etherscan.tx.api}")
    private String ETHERSCAN_TX_API;

    private static final String COINGECKO_API_URL =
            "https://api.coingecko.com/api/v3/simple/price?ids=ethereum&vs_currencies=usd";

    private final ObjectMapper objectMapper = new ObjectMapper();

    public TransactionStatistics getTransactions(String address) {
        List<TransactionData> transactions = new ArrayList<>();
        int swapCount = 0;
        BigDecimal totalTransactionVolume = BigDecimal.ZERO;

        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = String.format(ETHERSCAN_TX_API, address, ETHERSCAN_API_KEY);
            System.out.println(url);
            String response = restTemplate.getForObject(url, String.class);

            JsonNode resultArray = objectMapper.readTree(response).path("result");
            System.out.println(resultArray.size());
            for (JsonNode tx : resultArray) {
                boolean isSwap = detectSwap(tx.get("to").asText(),tx.get("from").asText()) ;// Check if it's a swap
                BigDecimal ethValue = new BigDecimal(tx.get("value").asText()).divide(BigDecimal.TEN.pow(18));

                transactions.add(new TransactionData(
                        tx.get("hash").asText(),
                        tx.get("from").asText(),
                        tx.get("to").asText(),
                        ethValue,
                        isSwap
                ));
                if (isSwap) swapCount++;
                totalTransactionVolume = totalTransactionVolume.add(ethValue);
            }
            System.out.println(transactions.size());


            BigDecimal ethPrice = getEthPrice();
            BigDecimal totalVolumeUSD = totalTransactionVolume.multiply(ethPrice);


            TransactionStatistics transactionStatistics = new TransactionStatistics(swapCount,totalVolumeUSD,totalTransactionVolume,transactions);

            System.out.println("Total Swaps: " + swapCount);
            System.out.println("Total Transaction Volume in ETH: " + totalTransactionVolume);
            System.out.println("Total Transaction Volume in USD: " + totalVolumeUSD);
            return transactionStatistics;
        } catch (Exception e) {
            System.out.println("Error ");
            e.printStackTrace();
            return null;
        }
    }

    public BigDecimal getEthPrice() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(COINGECKO_API_URL, String.class);
            JsonNode jsonResponse = objectMapper.readTree(response);
            return new BigDecimal(jsonResponse.get("ethereum").get("usd").asText());
        } catch (Exception e) {
            e.printStackTrace();
            return BigDecimal.ZERO;
        }
    }

    private boolean detectSwap(String toAddress, String fromAddress) {
        // List of known DEX contract addresses (Uniswap, Sushiswap, etc.)
        System.out.println("detectSwap " + toAddress + "-" + fromAddress);
        List<String> dexContracts = List.of(
                "0x5c69bee701ef814a2b6a3edd4b1652cb9cc5aa6f", // Uniswap Factory
                "0xc0a47dfe034b400b47bdad5fecda2621de6c4d95"  // Sushiswap Router
        );
        System.out.println("dexContracts " + dexContracts);
        return dexContracts.contains(toAddress.toLowerCase()) || dexContracts.contains(fromAddress.toUpperCase());
    }
}
