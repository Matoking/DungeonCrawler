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
public class PlayerTest {
    
    public GameMap gameMap;
    public Player player;
    
    public PlayerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.gameMap = new GameMap();
        
        this.player = new Player(this.gameMap, 5, 5);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testPlayerCantMoveIfBlocked() {
        this.player.move(Direction.UP);
        
        assertEquals(5, this.player.getX());
        assertEquals(4, this.player.getY());
        
        this.gameMap.setTile(5, 3, TileType.WALL);
        
        // Player should stay still after this
        this.player.move(Direction.UP);
        
        assertEquals(5, this.player.getX());
        assertEquals(4, this.player.getY());
    }
    
    @Test
    public void testPlayerCantMoveOverBoundaries() {
        this.player.setX(0);
        this.player.setY(0);
        
        this.player.move(Direction.LEFT);
        
        assertEquals(0, this.player.getX());
        assertEquals(0, this.player.getY());
        
        this.player.move(Direction.UP);
        
        assertEquals(0, this.player.getX());
        assertEquals(0, this.player.getY());
        
        this.player.setX(this.gameMap.getWidth()-1);
        this.player.setY(this.gameMap.getHeight()-1);
        
        this.player.move(Direction.RIGHT);
        
        assertEquals(this.gameMap.getWidth()-1, this.player.getX());
        assertEquals(this.gameMap.getHeight()-1, this.player.getY());
        
        this.player.move(Direction.DOWN);

        assertEquals(this.gameMap.getWidth()-1, this.player.getX());
        assertEquals(this.gameMap.getHeight()-1, this.player.getY());
    }
}
