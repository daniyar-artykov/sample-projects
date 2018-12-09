package com.upwork.service.impl;

import com.upwork.dao.TourDao;
import com.upwork.model.Tour;
import com.upwork.service.TourService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TourServiceImpl implements TourService {

    @Autowired
    private TourDao tourDao;

    @Override
    public List<Tour> retrieveTours(String name) {
        if (name == null) { // if name is null retrieve all records
            return tourDao.retrieveAll();
        } else {
            return tourDao.findByName(name);
        }
    }

    @Override
    public void refreshTours(List<Tour> tours) {

        tourDao.deleteAll();
        // calling merge not persist, because we are going to save 
        // Tour object with id also which comes from the remote json endpoint
        tours.forEach(tour -> tourDao.merge(tour));
    }

}
