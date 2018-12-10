package com.upwork.dao;

import com.upwork.model.Tour;
import java.util.List;

public interface TourDao {

    List<Tour> retrieveAll();
    
    List<Tour> findByName(String name);

    void merge(Tour tour);

    int deleteAll();
}
