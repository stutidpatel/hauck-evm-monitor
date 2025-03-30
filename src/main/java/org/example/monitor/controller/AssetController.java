package org.example.monitor.controller;

import org.example.monitor.model.AssetData;
import org.example.monitor.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/assets")
public class AssetController {
    private final AssetService assetService;

    @Autowired
    public AssetController(AssetService assetService) {
        this.assetService= assetService;
    }

    @GetMapping("/{address}")
    public List<AssetData> getAssets(@PathVariable String address)  {
        return assetService.getAllAssets(address);
    }

}