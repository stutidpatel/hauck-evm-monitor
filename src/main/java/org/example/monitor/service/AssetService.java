package org.example.monitor.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.example.monitor.model.AssetData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class AssetService {

    @Value("${etherscan.api.key}")
    private String ETHERSCAN_API_KEY;

    @Value("${etherscan.assets.api}")
    private String ETHERSCAN_ASSET_URL;

    private final ObjectMapper objectMapper = new ObjectMapper();
    RestTemplate restTemplate = new RestTemplate();


    public List<AssetData> getAllAssets(String address) {
        List<AssetData> assets = new ArrayList<>();

        List<AssetData> tokenAssets = getTokenBalances(address);
        assets.addAll(tokenAssets);


        return assets;
    }

    private List<AssetData> getTokenBalances(String address) {
        List<AssetData> assets = new ArrayList<>();

        try {
            // Get list of token transactions to find which tokens the address holds
            String url = String.format(
                    ETHERSCAN_ASSET_URL,
                    address, ETHERSCAN_API_KEY);

            String response = restTemplate.getForObject(url, String.class);
            System.out.println(url);
            JsonNode resultArray = objectMapper.readTree(response).path("result");
            for (JsonNode tx : resultArray) {
                System.out.println(tx);
                BigDecimal tokenPrice = getTokenPrice(tx.get("contractAddress").asText());
                BigDecimal tokenValue = new BigDecimal(tx.get("value").asText()).divide(BigDecimal.TEN.pow(18));
                BigDecimal usdValue = tokenValue.multiply(tokenPrice);
                assets.add(new AssetData(
                        tx.get("tokenName").asText(),
                        tx.get("tokenSymbol").asText(),
                        tx.get("tokenDecimal").asInt(),
                        tx.get("contractAddress").asText(),
                        tokenValue,
                        usdValue
                ));
                break;
            }
        } catch (Exception e) {
            System.out.println("Error fetching token balances");
        }

        return assets;
    }

    private BigDecimal getTokenPrice(String contractAddress) {
        try {
            // First try to get price from CoinGecko by contract address
            String url = String.format(
                    "https://api.coingecko.com/api/v3/simple/token_price/ethereum?contract_addresses=%s&vs_currencies=usd",
                    contractAddress);
            System.out.println(url);

            String response = restTemplate.getForObject(url, String.class);
            JsonNode rootNode = objectMapper.readTree(response);
            System.out.println(rootNode);
            if (rootNode.has(contractAddress.toLowerCase())) {
                System.out.println(rootNode.get(contractAddress.toLowerCase()).get("usd").decimalValue());
                return rootNode.get(contractAddress.toLowerCase()).get("usd").decimalValue();
            }
        } catch (Exception e) {
            System.out.println("Error fetching token price for contract");
        }

        return BigDecimal.ZERO;
    }
}
