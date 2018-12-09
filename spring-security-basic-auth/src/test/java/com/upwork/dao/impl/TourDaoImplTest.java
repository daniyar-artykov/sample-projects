/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.upwork.dao.impl;

import com.upwork.dao.impl.TourDaoImpl;
import com.upwork.dao.TourDao;
import com.upwork.model.Tour;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author daniyar.artykov
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TourDaoImplTest {
    
    @Autowired
    private TourDao tourDao;
    
    public TourDaoImplTest() {
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
     * Test of findByName method, of class TourDaoImpl.
     */
    @Test
    public void testFindByName() {
        System.out.println("findByName");
        String name = "";
        TourDaoImpl instance = new TourDaoImpl();
        List<Tour> expResult = null;
        List<Tour> result = instance.findByName(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteAll method, of class TourDaoImpl.
     */
    @Test
    public void testDeleteAll() {
        System.out.println("deleteAll");
        TourDaoImpl instance = new TourDaoImpl();
        int expResult = 0;
        int result = instance.deleteAll();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of save method, of class TourDaoImpl.
     */
    @Test
    public void testSave() {
        Tour t = new Tour();
        t.setName("London");
        t.setLongDesc("UK London");
        tourDao.merge(t);
    }
    
}
