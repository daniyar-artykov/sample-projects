package com.upwork.dto;

import com.upwork.model.Tour;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 *
 * @author daniyar.artykov
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RemoteJsonObject {

    private List<Tour> tours;

    public List<Tour> getTours() {
        return tours;
    }

    public void setTours(List<Tour> tours) {
        this.tours = tours;
    }

}
