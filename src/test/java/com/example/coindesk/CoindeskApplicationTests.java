package com.example.coindesk;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.rsocket.context.LocalRSocketServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.example.coindesk.entity.Currency;
import com.example.coindesk.repository.CurrencyRepository;
import com.example.coindesk.service.CoindeskService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CoindeskApplicationTests {

    @LocalRSocketServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private CoindeskService coindeskService;
  //  private CoindeskService coindeskService;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/currencies";
    }

    @Test
    public void testGetCurrencies() {
        // 插入測試資料
        currencyRepository.save(new Currency("USD", "美元"));
        currencyRepository.save(new Currency("EUR", "歐元"));

        // 測試查詢 API
        ResponseEntity<Currency[]> response = restTemplate.getForEntity(getBaseUrl(), Currency[].class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    public void testAddCurrency() {
        // 建立新增資料的請求
        Currency currency = new Currency("JPY", "日圓");
        ResponseEntity<Currency> response = restTemplate.postForEntity(getBaseUrl(), currency, Currency.class);

        // 驗證新增結果
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCode()).isEqualTo("JPY");
        assertThat(response.getBody().getChineseName()).isEqualTo("日圓");
    }

    @Test
    public void testUpdateCurrency() {
        // 插入測試資料
        currencyRepository.save(new Currency("GBP", "英鎊"));

        // 更新資料
        Currency updatedCurrency = new Currency("GBP", "英鎊（更新）");
        HttpEntity<Currency> request = new HttpEntity<>(updatedCurrency);
        ResponseEntity<Currency> response = restTemplate.exchange(getBaseUrl() + "/GBP", HttpMethod.PUT, request, Currency.class);

        // 驗證更新結果
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getChineseName()).isEqualTo("英鎊（更新）");
    }

    @Test
    public void testDeleteCurrency() {
        // 插入測試資料
        currencyRepository.save(new Currency("CNY", "人民幣"));

        // 刪除資料
        restTemplate.delete(getBaseUrl() + "/CNY");

        // 驗證刪除結果
        assertThat(currencyRepository.findById("CNY")).isEmpty();
    }

    @Test
    public void testCallCoindeskApi() {
        // 呼叫 Coindesk API
        String coindeskResponse = coindeskService.getCoindeskData();

        // 驗證結果
        assertThat(coindeskResponse).isNotNull();
        assertThat(coindeskResponse).contains("bpi");
    }

    @Test
    public void testTransformCoindeskData() {
        // 呼叫資料轉換 API
        ResponseEntity<String> response = restTemplate.getForEntity(getBaseUrl() + "/coindesk/transform", String.class);

        // 驗證結果
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).contains("更新時間");
        assertThat(response.getBody()).contains("幣別");
        assertThat(response.getBody()).contains("匯率");
    }
}
