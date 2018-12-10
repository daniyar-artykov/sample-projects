package com.upwork;

import com.upwork.dto.ToursRefreshRequestDto;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author daniyar.artykov
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {TestApplication.class, TestSpringSecurityConfig.class})
public class IntegrationTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void testToursUnauthorized() throws Exception {

        ResponseEntity<String> response = restTemplate.getForEntity("/tours", String.class);
        ResponseEntity<String> response1 = restTemplate.withBasicAuth("user1", "password1")
                .getForEntity("/tours", String.class);
        // without authentication credentials
        Assert.assertEquals(401, response.getStatusCodeValue());
        // with wrong authentication credentials
        Assert.assertEquals(401, response1.getStatusCodeValue());
    }

    @Test
    public void testToursForbidden() throws Exception {
        ResponseEntity<String> response = restTemplate.withBasicAuth("admin", "admin12")
                .getForEntity("/tours", String.class);
        // forbidden for ADMIN and ANONYMOUS roles
        Assert.assertEquals(403, response.getStatusCodeValue());
    }

    @Test
    public void testToursOk() throws Exception {
        ResponseEntity<String> response = restTemplate.withBasicAuth("john", "john12")
                .getForEntity("/tours", String.class);
        // status code should be 200
        Assert.assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testToursRefreshUnauthorized() throws Exception {
        String url = "/tours/refresh";
        ToursRefreshRequestDto dto = null;
        HttpEntity<ToursRefreshRequestDto> request = new HttpEntity<>(dto);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        ResponseEntity<String> response1 = restTemplate.withBasicAuth("user1", "password1")
                .postForEntity(url, request, String.class);
        // without authentication credentials
        Assert.assertEquals(401, response.getStatusCodeValue());
        // with wrong authentication credentials
        Assert.assertEquals(401, response1.getStatusCodeValue());
    }

    @Test
    public void testToursRefreshForbidden() throws Exception {
        ToursRefreshRequestDto dto = null;
        HttpEntity<ToursRefreshRequestDto> request = new HttpEntity<>(dto);
        ResponseEntity<String> response = restTemplate.withBasicAuth("john", "john12")
                .postForEntity("/tours/refresh", request, String.class);
        // forbidden for USER and ANONYMOUS roles
        Assert.assertEquals(403, response.getStatusCodeValue());
    }

    @Test
    public void testToursRefreshOk() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ToursRefreshRequestDto dto = null;
        HttpEntity<ToursRefreshRequestDto> request = new HttpEntity<>(dto, headers);
        ResponseEntity<String> response = restTemplate.withBasicAuth("admin", "admin12")
                .postForEntity("/tours/refresh", request, String.class);
        // status code should be 200
        Assert.assertEquals(200, response.getStatusCodeValue());
        // response body should be "" 
        Assert.assertNull(response.getBody());

        Map<String, String> params = new HashMap<>();
        params.put("filter", "Afternoon Tea in London");
        response = restTemplate.withBasicAuth("john", "john12")
                .getForEntity("/tours?filter={filter}", String.class, params);
        // status code should be 200
        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals("[\"Afternoon Tea in London\"]", response.getBody());
    }
    
    @Test
    public void testToursRefresh_withFilter() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ToursRefreshRequestDto dto = new ToursRefreshRequestDto();
        dto.setFilter("london"); // save to db only tours with word london
        HttpEntity<ToursRefreshRequestDto> request = new HttpEntity<>(dto, headers);
        ResponseEntity<String> response = restTemplate.withBasicAuth("admin", "admin12")
                .postForEntity("/tours/refresh", request, String.class);
        // status code should be 200
        Assert.assertEquals(200, response.getStatusCodeValue());
        // response body should be "" 
        Assert.assertNull(response.getBody());
        
        response = restTemplate.withBasicAuth("john", "john12")
                .getForEntity("/tours", String.class);
        // status code should be 200
        Assert.assertEquals(200, response.getStatusCodeValue());
    }

}
