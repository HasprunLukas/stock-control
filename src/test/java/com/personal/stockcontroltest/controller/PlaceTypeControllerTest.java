package com.personal.stockcontroltest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.stockcontroltest.model.PlaceType;
import com.personal.stockcontroltest.repository.PlaceTypeRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PlaceTypeControllerTest {
    private static final String placeTypeTestNaming = "TestPlaceType";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PlaceTypeRepository placeTypeRepository;

    @After
    public void tearDown() {
        placeTypeRepository.deleteAll();
    }

    @Test
    public void getAllTest() throws Exception {
        String testType = "getAll";
        createPlaceType(testType, true);
        createPlaceType(testType + 2, true);

        this.mockMvc
                .perform(get("/place_type")
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
        PlaceType placeType = createPlaceType(testType, true);

        this.mockMvc
                .perform(get("/place_type/" + placeType.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value(testType + placeTypeTestNaming))
                .andReturn();
    }

    @Test
    public void createTest() throws Exception {
        String testType = "create";
        PlaceType placeType = createPlaceType(testType, false);

        this.mockMvc
                .perform(post("/place_type")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(placeType))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value(testType + placeTypeTestNaming))
                .andReturn();
    }

    @Test
    public void updateTest() throws Exception {
        String testType = "update";
        PlaceType placeType = createPlaceType(testType, true);

        PlaceType updatedPlaceType = new PlaceType();
        updatedPlaceType.setName("updateTestStockModified");

        this.mockMvc
                .perform(put("/place_type/" + placeType.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPlaceType))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("updateTestStockModified"))
                .andReturn();
    }

    @Test
    public void deleteTest() throws Exception {
        String testType = "delete";
        createPlaceType(testType, true);

        String tempId = String.valueOf(placeTypeRepository.findAll().get(0).getId());

        this.mockMvc
                .perform(delete("/place_type/" + tempId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
    }

    private PlaceType createPlaceType(String testType, Boolean savePlaceType) {
        PlaceType placeType = new PlaceType();
        placeType.setName(testType + placeTypeTestNaming);
        if(savePlaceType) placeTypeRepository.save(placeType);

        return placeType;
    }
}
