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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
webEnvironment=SpringBootTest.WebEnvironment.MOCK,
        classes = FeatureCloudApplication.class)
@AutoConfigureMockMvc(printOnlyOnFailure = true)
public class ReleaseDeltaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getFeatureTagCloudTest() throws Exception{
        mockMvc.perform(get("/features/")
                .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
    }

    @Test
    public void getFeatureVariationPointsTest() throws Exception{
        mockMvc.perform(get("/features/nozzle_park_feature/")
                .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
    }

    @Test
    public void getCoreAssetContentTest() throws Exception{

        this.mockMvc.perform(get("/features/nozzle_park_feature/asset/151/").contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
    }

    @Test
    public void getFeaturesFilteredTest() throws Exception{
        this.mockMvc.perform(get("/features/filtered").param("packageId","1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect( jsonPath("$.maxModifiedLines").isNumber()).andExpect(jsonPath("$.newickString").isString());
    }

    @Test
    public void getNewickStringFilteredTest() throws Exception{
        this.mockMvc.perform(post("/newick/filtered")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new String[]{"NOZZLE_PARK_FEATURE, AUTOB_BED_LEBELING,PIDTEMP,X_BED_SIZE,Y_BED_SIZE,NEWPANEL"}))).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("NOZZLE_PARK_FEATURE")));
    }
}
