package com.personal.stockcontroltest;

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
public class PlaceTypeIntegrationTest {

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
        PlaceType placeType = new PlaceType();
        placeType.setName("getAllTest");

        placeTypeRepository.save(placeType);

        this.mockMvc
                .perform(get("/place_type")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andReturn();
    }

    @Test
    public void getByIdTest() throws Exception {
        PlaceType placeType = new PlaceType();
        placeType.setName("getByIdTest");

        PlaceType savedPlaceType = placeTypeRepository.save(placeType);

        this.mockMvc
                .perform(get("/place_type/" + savedPlaceType.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("getByIdTest"))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andReturn();
    }

    @Test
    public void createTest() throws Exception {
        PlaceType placeType = new PlaceType();
        placeType.setName("createTest");

        this.mockMvc
                .perform(post("/place_type")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(placeType))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("createTest"))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andReturn();
    }

    @Test
    public void updateTest() throws Exception {
        PlaceType placeType = new PlaceType();
        placeType.setName("updateTest");

        PlaceType savedPlaceType = placeTypeRepository.save(placeType);

        PlaceType updatedPlaceType = new PlaceType();
        updatedPlaceType.setName("updateTestModified");

        this.mockMvc
                .perform(put("/place_type/" + savedPlaceType.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPlaceType))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("updateTestModified"))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andReturn();
    }

    @Test
    public void deleteTest() throws Exception {
        PlaceType placeType = new PlaceType();
        placeType.setName("deleteTest");

        placeTypeRepository.save(placeType);

        String tempId = String.valueOf(placeTypeRepository.findAll().get(0).getId());

        this.mockMvc
                .perform(delete("/place_type/" + tempId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
    }
}
