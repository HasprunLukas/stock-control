package com.personal.stockcontroltest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.stockcontroltest.model.StockType;
import com.personal.stockcontroltest.repository.StockTypeRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StockTypeControllerTest {
    private static final String stockTypeTestNaming = "TestStockType";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private StockTypeRepository stockTypeRepository;

    @After
    public void tearDown() {
        stockTypeRepository.deleteAll();
    }

    @Test
    public void getAllTest() throws Exception {
        String testType = "getAll";
        createStockType(testType, true);
        createStockType(testType + 2, true);

        this.mockMvc
                .perform(get("/stock_type")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andReturn();
    }

    @Test
    public void getByIdTest() throws Exception {
        String testType = "getById";
        StockType stockType = createStockType(testType, true);

        this.mockMvc
                .perform(get("/stock_type/" + stockType.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value(testType + stockTypeTestNaming))
                .andReturn();
    }

    @Test
    public void createTest() throws Exception {
        String testType = "create";
        StockType stockType = createStockType(testType, false);

        this.mockMvc
                .perform(post("/stock_type")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(stockType))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value(testType + stockTypeTestNaming))
                .andReturn();
    }

    @Test
    public void updateTest() throws Exception {
        String testType = "update";
        StockType stockType = createStockType(testType, true);

        StockType updatedStockType = new StockType();
        updatedStockType.setName("updateTestStockTypeModified");

        this.mockMvc
                .perform(put("/stock_type/" + stockType.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedStockType))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("updateTestStockTypeModified"))
                .andReturn();
    }

    @Test
    public void deleteTest() throws Exception {
        String testType = "delete";
        createStockType(testType, true);

        String tempId = String.valueOf(stockTypeRepository.findAll().get(0).getId());

        this.mockMvc
                .perform(delete("/stock_type/" + tempId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
    }

    private StockType createStockType(String testType, Boolean saveStockType) {
        StockType stockType = new StockType();
        stockType.setName(testType + stockTypeTestNaming);
        if(saveStockType) stockTypeRepository.save(stockType);

        return stockType;
    }
}
