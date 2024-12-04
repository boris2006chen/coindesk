package com.example.coindesk.controller;

import com.example.coindesk.entity.Currency;
import com.example.coindesk.repository.CurrencyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(MockitoJUnitRunner.class)
public class CurrencyControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CurrencyRepository currencyRepository;

    @InjectMocks
    private CurrencyController currencyController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(currencyController).build();
    }

    @Test
    public void testGetAllCurrencies() throws Exception {
        Currency currency1 = new Currency("USD", "美元");
        Currency currency2 = new Currency("GBP", "英镑");

        Mockito.when(currencyRepository.findAll()).thenReturn(Arrays.asList(currency1, currency2));

        mockMvc.perform(get("/currencies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}
