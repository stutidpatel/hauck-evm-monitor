package org.example.monitor.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.math.BigDecimal;

@Service
public class BalanceService {

    private static final String ETHERSCAN_BALANCE_API =
            "https://api.etherscan.io/api?module=account&action=balance&address=%s&apikey=%s";

    @Value("${etherscan.api.key}")
    private String ETHERSCAN_API_KEY;


    private final ObjectMapper objectMapper = new ObjectMapper();


    public BigDecimal getBalance(String address) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = String.format(ETHERSCAN_BALANCE_API, address, ETHERSCAN_API_KEY);
            String response = restTemplate.getForObject(url, String.class);

            JsonNode jsonResponse = objectMapper.readTree(response);
            String balance = jsonResponse.get("result").asText();
            return new BigDecimal(balance).divide(BigDecimal.TEN.pow(18)); // Convert Wei to ETH
        } catch (Exception e) {
            e.printStackTrace();
            return BigDecimal.ZERO;
        }
    }
}
