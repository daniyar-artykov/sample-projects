package com.upwork.service;

import com.upwork.model.Tour;
import java.util.List;

public interface TourService {

    List<Tour> retrieveTours(String filter);

    void refreshTours(List<Tour> tours);
}
