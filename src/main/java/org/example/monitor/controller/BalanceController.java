package org.example.monitor.controller;

import org.example.monitor.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/balance")
public class BalanceController {
    private final BalanceService balanceService;

    @Autowired
    public BalanceController(BalanceService balanceService) {
        this.balanceService= balanceService;
    }

    @GetMapping("/{address}")
    public BigDecimal getBalance(@PathVariable String address) throws Exception {
        return balanceService.getBalance(address);
    }
}
