package com.personal.stockcontroltest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.stockcontroltest.model.PlaceType;
import com.personal.stockcontroltest.model.Stock;
import com.personal.stockcontroltest.repository.PlaceTypeRepository;
import com.personal.stockcontroltest.repository.StockRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StockControllerTest {
    private static final String placeTypeTestNaming = "TestPlaceType";
    private static final String stockTypeTestNaming = "TestStock";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private PlaceTypeRepository placeTypeRepository;

    @After
    public void tearDown() {
        stockRepository.deleteAll();
    }

    @Test
    public void getAllTest() throws Exception {
        String testType = "getAll";
        createStock(testType, true);
        createStock(testType + 2, true);

        this.mockMvc
                .perform(get("/stock")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andReturn();
    }

    @Test
    public void getByIdTest() throws Exception {
        String testType = "getById";
        Stock stock = createStock(testType, true);

        this.mockMvc
                .perform(get("/stock/" + stock.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value(testType + stockTypeTestNaming))
                .andExpect(jsonPath("$.checkup_date").value(stock.getCheckup_date().toString()))
                .andExpect(jsonPath("$.placeType.name").value(testType + placeTypeTestNaming))
                .andExpect(jsonPath("$.placeType.id").isNotEmpty())
                .andReturn();
    }

    @Test
    public void createTest() throws Exception {
        String testType = "create";
        Stock stock = createStock(testType, false);

        this.mockMvc
                .perform(post("/stock")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(stock))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value(testType + stockTypeTestNaming))
                .andExpect(jsonPath("$.checkup_date").value(stock.getCheckup_date().toString()))
                .andExpect(jsonPath("$.placeType.id").isNotEmpty())
                .andReturn();
    }

    @Test
    public void updateTest() throws Exception {
        String testType = "update";
        Stock stock = createStock(testType, true);

        Stock updatedStock = new Stock();
        updatedStock.setName("updateTestStockModified");
        updatedStock.setCheckup_date(stock.getCheckup_date());
        updatedStock.setPlaceType(stock.getPlaceType());

        this.mockMvc
                .perform(put("/stock/" + stock.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedStock))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("updateTestStockModified"))
                .andExpect(jsonPath("$.checkup_date").value(stock.getCheckup_date().toString()))
                .andExpect(jsonPath("$.placeType.id").isNotEmpty())
                .andReturn();
    }

    @Test
    public void deleteTest() throws Exception {
        String testType = "delete";
        createStock(testType, true);
        String tempId = String.valueOf(stockRepository.findAll().get(0).getId());

        this.mockMvc
                .perform(delete("/stock/" + tempId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
    }

    private Stock createStock(String testType, Boolean saveStock) {
        PlaceType placeType = new PlaceType();
        placeType.setName(testType + placeTypeTestNaming);
        placeTypeRepository.save(placeType);

        Stock stock = new Stock();
        stock.setName(testType + stockTypeTestNaming);
        Date date = new Date(System.currentTimeMillis());
        stock.setCheckup_date(date);
        PlaceType placeTypeNew = new PlaceType();
        placeTypeNew.setId(placeType.getId());
        stock.setPlaceType(placeTypeNew);
        if(saveStock) stockRepository.save(stock);

        return stock;
    }
}
