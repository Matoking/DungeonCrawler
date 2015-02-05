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
public class PathfindingTest {
    public Pathfinding pathfinding;
    
    public PathfindingTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.pathfinding = new Pathfinding(new GameState());
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testCoordinateConversionToInt() {
        for (int x = 0; x < 64; x++) {
            for (int y = 0; y < 64; y++) {
                int converted = this.pathfinding.convertToInt(x, y);
                Coordinate coordinate = this.pathfinding.convertFromInt(converted);
                
                assertEquals(coordinate.getX(), x);
                assertEquals(coordinate.getY(), y);
            }
        }
    }
    
}
