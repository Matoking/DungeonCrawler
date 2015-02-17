/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.matoking.dungeoncrawler.generator;

import com.matoking.dungeoncrawler.state.Coordinate;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author matoking
 */
public class CorridorTest {
    
    public CorridorTest() {
    }
    
    @Before
    public void setUp() {
    }

    @Test
    public void testCorridorsHaveCorrectWidthAndHeight() {
        Corridor corridorA = new Corridor(new Coordinate(10, 5), new Coordinate(15, 5));
        
        assertEquals(corridorA.getWidth(), 6);
        assertEquals(corridorA.getHeight(), 1);
        
        Corridor corridorB = new Corridor(new Coordinate(10, 5), new Coordinate(10, 10));
        
        assertEquals(corridorB.getWidth(), 1);
        assertEquals(corridorB.getHeight(), 6);
    }
    
}
