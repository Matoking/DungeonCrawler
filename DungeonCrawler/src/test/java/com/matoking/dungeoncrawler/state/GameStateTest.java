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
 * @author jannepul
 */
public class GameStateTest {
    
    private GameState gameState;
    
    public GameStateTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.gameState = new GameState();
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testPlayerCreatedWithinMapBoundaries() {
        assertEquals(true, this.gameState.getPlayer().getX() >= 0 && 
                           this.gameState.getPlayer().getX() < this.gameState.getGameMap().getWidth());
        
        assertEquals(true, this.gameState.getPlayer().getY() >= 0 && 
                           this.gameState.getPlayer().getY() < this.gameState.getGameMap().getHeight());
    }
    
}
