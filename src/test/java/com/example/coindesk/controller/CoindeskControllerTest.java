package com.example.coindesk.controller;

import com.example.coindesk.service.CoindeskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class CoindeskControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CoindeskService coindeskService;

    @InjectMocks
    private CoindeskController coindeskController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(coindeskController).build();
    }

    @Test
    public void testGetTransformedData() throws Exception {
        Map<String, Object> mockData = new HashMap<>();
        Mockito.when(coindeskService.getTransformedData()).thenReturn(mockData);

        mockMvc.perform(get("/coindesk/transformed"))
                .andExpect(status().isOk());
    }
}
