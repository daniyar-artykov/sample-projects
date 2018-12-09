/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.upwork.service.impl;

import com.upwork.service.impl.TourServiceImpl;
import com.upwork.model.Tour;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author daniyar.artykov
 */
public class TourServiceImplTest {
    
    public TourServiceImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of findByName method, of class TourServiceImpl.
     */
    @Test
    public void testFindByName() {
        System.out.println("findByName");
        String name = "";
        TourServiceImpl instance = new TourServiceImpl();
        List<Tour> expResult = null;
        List<Tour> result = instance.retrieveTours(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of refreshTours method, of class TourServiceImpl.
     */
    @Test
    public void testRefreshTours() {
        System.out.println("refreshTours");
        List<Tour> tours = null;
        TourServiceImpl instance = new TourServiceImpl();
        instance.refreshTours(tours);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
