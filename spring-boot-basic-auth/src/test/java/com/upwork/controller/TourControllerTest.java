package com.upwork.controller;

import com.upwork.TestApplication;
import com.upwork.TestSpringSecurityConfig;
import com.upwork.model.Tour;
import com.upwork.service.TourService;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.Base64Utils;

/**
 *
 * @author daniyar.artykov
 */
@RunWith(SpringRunner.class)
@WebMvcTest(TourController.class)
@ContextConfiguration(classes = {TestApplication.class, TestSpringSecurityConfig.class})
public class TourControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TourService tourService;

    @MockBean
    private EntityManagerFactory entityManager;

    @Test
    public void getTours_ShouldReturnTours() throws Exception {
        
        mockMvc.perform(MockMvcRequestBuilders.get("/tours").header(HttpHeaders.AUTHORIZATION,
                "Basic " + Base64Utils.encodeToString("john:john12".getBytes())))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test
    public void refreshTours_Success200() throws Exception {
        
        mockMvc.perform(MockMvcRequestBuilders.post("/tours/refresh").header(HttpHeaders.AUTHORIZATION,
                "Basic " + Base64Utils.encodeToString("admin:admin12".getBytes())).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
