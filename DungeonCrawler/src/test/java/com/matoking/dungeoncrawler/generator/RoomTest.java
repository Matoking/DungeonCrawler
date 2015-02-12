/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.matoking.dungeoncrawler.generator;

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
public class RoomTest {
    
    public RoomTest() {
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
    public void testRoomsIntersectCorrectly() {
        Room roomA = new Room(5, 5, 10, 10);
        Room roomB = new Room(9, 8, 5, 5);
        Room roomC = new Room(16, 16, 10, 10);
        
        assertEquals(roomA.intersects(roomB), true);
        assertEquals(roomB.intersects(roomC), false);
    }
    
}
