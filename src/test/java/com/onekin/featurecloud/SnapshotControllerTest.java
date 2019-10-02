package com.onekin.featurecloud;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = FeatureCloudApplication.class)
@AutoConfigureMockMvc
public class SnapshotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getFeatureTagCloudTest() throws Exception {
        mockMvc.perform(get("/release/features/")
                .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
    }

    @Test
    public void getFeatureVariationPointsTest() throws Exception {
        mockMvc.perform(get("/release/features/nozzle_park_feature/")
                .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
    }

    @Test
    public void getVariationPointContentTest() throws Exception {
        mockMvc.perform(get("/features/nozzle_park_feature/asset/151/")
                .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
    }

    @Test
    public void  getFeaturesFilteredByProductTest()  throws Exception{

        this.mockMvc.perform(get("/release/features/filtered").param("product","creality")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect( jsonPath("$.maxModifiedLines").isNumber()).andExpect(jsonPath("$.newickString").isString());
    }


    @Test
    public void  getFeaturesFilteredByPackageTest()  throws Exception{

        this.mockMvc.perform(get("/release/features/filtered").param("packageId","1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect( jsonPath("$.maxModifiedLines").isNumber()).andExpect(jsonPath("$.newickString").isString());
    }

    @Test
    public void  getFeaturesFilteredTest()  throws Exception{

        this.mockMvc.perform(get("/release/features/filtered").param("packageId","1").param("product","creality")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect( jsonPath("$.maxModifiedLines").isNumber()).andExpect(jsonPath("$.newickString").isString());
    }


    @Test
    public void  getFeaturesFilteredNullTest()  throws Exception{

        this.mockMvc.perform(get("/release/features/filtered")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect( jsonPath("$.maxModifiedLines").isNumber()).andExpect(jsonPath("$.newickString").isString());
    }
}
