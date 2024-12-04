package com.example.coindesk.controller;

import com.example.coindesk.service.CoindeskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/coindesk")
public class CoindeskController {

    @Autowired
    private CoindeskService coindeskService;

    // 獲取原始 Coindesk 數據
    @GetMapping("/original")
    public Map<String, Object> getOriginalData() {
        return coindeskService.getTransformedData();
    }

    // 獲取轉換後的數據
    @GetMapping("/transformed")
    public Map<String, Object> getTransformedData() {
        return coindeskService.getTransformedData();
    }
}
