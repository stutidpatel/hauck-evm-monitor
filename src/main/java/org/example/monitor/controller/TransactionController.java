package org.example.monitor.controller;
import org.example.monitor.model.TransactionStatistics;
import org.example.monitor.service.EtherscanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final EtherscanService etherscanService;

    @Autowired
    public TransactionController(EtherscanService etherscanService) {
        this.etherscanService= etherscanService;
    }

    @GetMapping("/{address}")
    public TransactionStatistics getTransactions(@PathVariable String address)  throws Exception {
        return etherscanService.getTransactions(address);
    }

}
