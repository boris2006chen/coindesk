package com.example.coindesk.service;

import com.example.coindesk.entity.Currency;
import com.example.coindesk.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CoindeskService {

    @Autowired
    private CurrencyRepository currencyRepository;
 //   private final RestTemplate restTemplate = new RestTemplate();

    public Map<String, Object> getTransformedData() {
        String url = "https://api.coindesk.com/v1/bpi/currentprice.json";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        // Extract and transform data
        Map<String, Object> time = (Map<String, Object>) response.get("time");
        String updatedISO = (String) time.get("updatedISO");
        String formattedTime = formatTime(updatedISO);

        Map<String, Object> bpi = (Map<String, Object>) response.get("bpi");

        List<Map<String, Object>> currencyList = new ArrayList<>();

        for (Map.Entry<String, Object> entry : bpi.entrySet()) {
            String code = entry.getKey();
            Map<String, Object> currencyData = (Map<String, Object>) entry.getValue();

            Currency currency = currencyRepository.findById(code).orElse(new Currency(code, "未知"));

            Map<String, Object> currencyInfo = new HashMap<>();
            currencyInfo.put("code", code);
            currencyInfo.put("chineseName", currency.getChineseName());
            currencyInfo.put("rate", currencyData.get("rate"));

            currencyList.add(currencyInfo);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("updatedTime", formattedTime);
        result.put("currencies", currencyList);

        return result;
    }

    private String formatTime(String timeStr) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
            Date date = inputFormat.parse(timeStr);
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            return outputFormat.format(date);
        } catch (Exception e) {
            return timeStr;
        }
    }
    
    public String getCoindeskData() {
        String url = "https://api.coindesk.com/v1/bpi/currentprice.json";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class); // 呼叫 API 並返回 JSON 字串
    }
}
