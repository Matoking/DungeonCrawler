/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.matoking.dungeoncrawler.state;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author matoking
 */
public class CoordinateTest {
    
    public CoordinateTest() {
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
    
    @Test
    public void testCoordinateConversion() {
        // 0,0 to right
        assertEquals(1, Coordinate.getNewX(Direction.RIGHT, 0));
        assertEquals(0, Coordinate.getNewY(Direction.RIGHT, 0));
        
        // 0,0 down
        assertEquals(0, Coordinate.getNewX(Direction.DOWN, 0));
        assertEquals(1, Coordinate.getNewY(Direction.DOWN, 0));
        
        // 0,0 left
        assertEquals(-1, Coordinate.getNewX(Direction.LEFT, 0));
        assertEquals(0, Coordinate.getNewY(Direction.LEFT, 0));
        
        // 0,0 up
        assertEquals(0, Coordinate.getNewX(Direction.UP, 0));
        assertEquals(-1, Coordinate.getNewY(Direction.UP, 0));
    }
    
}
