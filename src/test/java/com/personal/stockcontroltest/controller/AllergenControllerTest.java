package com.personal.stockcontroltest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.stockcontroltest.model.Allergen;
import com.personal.stockcontroltest.repository.AllergenRepository;
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
public class AllergenControllerTest {
    private static final String ALLERGEN_TEST_NAMING = "AllergenType";
    private static final String URL = "/allergen/";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AllergenRepository allergenRepository;

    @After
    public void tearDown() {
        allergenRepository.deleteAll();
    }

    @Test
    public void getAllTest() throws Exception {
        String testType = "getAll";
        createAllergen(testType, true);
        createAllergen(testType + 2, true);

        this.mockMvc
                .perform(get(URL)
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
        Allergen allergen = createAllergen(testType, true);

        this.mockMvc
                .perform(get(URL + allergen.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value(testType + ALLERGEN_TEST_NAMING))
                .andReturn();
    }

    @Test
    public void createTest() throws Exception {
        String testType = "create";
        Allergen allergen = createAllergen(testType, false);

        this.mockMvc
                .perform(post(URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(allergen))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value(testType + ALLERGEN_TEST_NAMING))
                .andReturn();
    }

    @Test
    public void updateTest() throws Exception {
        String testType = "update";
        Allergen allergen = createAllergen(testType, true);

        Allergen updatedAllergen = new Allergen();
        updatedAllergen.setName("updateTestAllergenModified");

        this.mockMvc
                .perform(put(URL + allergen.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedAllergen))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("updateTestAllergenModified"))
                .andReturn();
    }

    @Test
    public void deleteTest() throws Exception {
        String testType = "delete";
        createAllergen(testType, true);

        String tempId = String.valueOf(allergenRepository.findAll().get(0).getId());

        this.mockMvc
                .perform(delete(URL + tempId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
    }

    private Allergen createAllergen(String testType, Boolean saveAllergen) {
        Allergen allergen = new Allergen();
        allergen.setName(testType + ALLERGEN_TEST_NAMING);
        if(saveAllergen) allergenRepository.save(allergen);

        return allergen;
    }
}
