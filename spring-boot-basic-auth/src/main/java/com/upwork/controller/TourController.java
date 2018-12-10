package com.upwork.controller;

import com.upwork.dto.RemoteJsonObject;
import com.upwork.dto.ToursRefreshRequestDto;
import com.upwork.model.Tour;
import com.upwork.service.TourService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

@Controller
public class TourController {

    // get remove json endpoint url from application.properties file
    @Value("${remote.json.endpoint}")
    private String remoteJsonEndpoint;

    @Autowired
    private TourService tourService;

    @RequestMapping(value = "/tours", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getTours(String filter) {
        List<Tour> tours = tourService.retrieveTours(filter);
        List<String> toursName = new ArrayList<>();
        if (tours != null && !tours.isEmpty()) {
            toursName = tours.stream().map(a -> a.getName()).collect(Collectors.toList());
        }
        return new ResponseEntity<>(toursName, HttpStatus.OK);
    }

    @RequestMapping(value = "/tours/refresh", method = RequestMethod.POST,
            consumes = "application/json")
    public ResponseEntity<String> refreshTours(@RequestBody(required = false) ToursRefreshRequestDto request) {

        RestTemplate restTemplate = new RestTemplate();
        // to support gzip encoding
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        ResponseEntity<RemoteJsonObject> response
                = restTemplate.getForEntity(remoteJsonEndpoint, RemoteJsonObject.class);
        if (response != null && response.getBody() != null
                && response.getBody().getTours() != null
                && !response.getBody().getTours().isEmpty()) {
            List<Tour> tours = response.getBody().getTours();
            if (request != null && request.getFilter() != null) {
                // filter the tours list
                tours = tours.stream()
                        .filter(t -> t.getName() != null)
                        .filter(t -> t.getName().toLowerCase().contains(request.getFilter().toLowerCase()))
                        .collect(Collectors.toList());
            }

            tourService.refreshTours(tours);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok().build();
    }
}
