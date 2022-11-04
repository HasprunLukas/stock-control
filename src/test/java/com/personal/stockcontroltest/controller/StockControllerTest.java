package com.personal.stockcontroltest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.stockcontroltest.model.PlaceType;
import com.personal.stockcontroltest.model.Stock;
import com.personal.stockcontroltest.model.StockType;
import com.personal.stockcontroltest.repository.PlaceTypeRepository;
import com.personal.stockcontroltest.repository.StockRepository;
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

import java.sql.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StockControllerTest {
    private static final String PLACE_TYPE_TEST_NAMING = "TestPlaceType";
    private static final String STOCK_TYPE_TEST_NAMING = "TestStockType";
    private static final String STOCK_TEST_NAMING = "TestStock";
    private static final String URL = "/stock/";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private PlaceTypeRepository placeTypeRepository;
    @Autowired
    private StockTypeRepository stockTypeRepository;

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
                .perform(get(URL)
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
                .perform(get(URL + stock.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value(testType + STOCK_TEST_NAMING))
                .andExpect(jsonPath("$.checkup_date").value(stock.getCheckup_date().toString()))
                .andExpect(jsonPath("$.placeType.name").value(testType + PLACE_TYPE_TEST_NAMING))
                .andExpect(jsonPath("$.stockType.name").value(testType + STOCK_TYPE_TEST_NAMING))
                .andExpect(jsonPath("$.placeType.id").isNotEmpty())
                .andReturn();
    }

    @Test
    public void createTest() throws Exception {
        String testType = "create";
        Stock stock = createStock(testType, false);

        this.mockMvc
                .perform(post(URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(stock))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value(testType + STOCK_TEST_NAMING))
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
        updatedStock.setStockType(stock.getStockType());

        this.mockMvc
                .perform(put(URL + stock.getId())
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
                .perform(delete(URL + tempId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
    }

    private Stock createStock(String testType, Boolean saveStock) {
        PlaceType placeType = new PlaceType();
        placeType.setName(testType + PLACE_TYPE_TEST_NAMING);
        placeTypeRepository.save(placeType);

        StockType stockType = new StockType();
        stockType.setName(testType + STOCK_TYPE_TEST_NAMING);
        stockTypeRepository.save(stockType);

        Stock stock = new Stock();
        stock.setName(testType + STOCK_TEST_NAMING);
        Date date = new Date(System.currentTimeMillis());
        stock.setCheckup_date(date);

        PlaceType placeTypeNew = new PlaceType();
        placeTypeNew.setId(placeType.getId());

        StockType stockTypeNew = new StockType();
        stockTypeNew.setId(stockType.getId());

        stock.setPlaceType(placeTypeNew);
        stock.setStockType(stockTypeNew);
        if(saveStock) stockRepository.save(stock);

        return stock;
    }
}
