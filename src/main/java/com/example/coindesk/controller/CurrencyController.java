package com.example.coindesk.controller;

import com.example.coindesk.entity.Currency;
import com.example.coindesk.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/currencies")
public class CurrencyController {

    @Autowired
    private CurrencyRepository currencyRepository;

    // 查詢所有幣別
    @GetMapping
    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAll();
    }

    // 新增幣別
    @PostMapping
    public Currency createCurrency(@RequestBody Currency currency) {
        return currencyRepository.save(currency);
    }

    // 更新幣別
    @PutMapping("/{code}")
    public Currency updateCurrency(@PathVariable String code, @RequestBody Currency currencyDetails) {
        Currency currency = currencyRepository.findById(code)
                            .orElseThrow(() -> new RuntimeException("Currency not found with code: " + code));
        currency.setChineseName(currencyDetails.getChineseName());
        return currencyRepository.save(currency);
    }


    // 删除幣別
    @DeleteMapping("/{code}")
    public void deleteCurrency(@PathVariable String code) {
        currencyRepository.deleteById(code);
    }
}
