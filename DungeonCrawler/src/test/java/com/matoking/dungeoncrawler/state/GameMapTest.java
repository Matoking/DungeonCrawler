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
public class GameMapTest {
    
    public GameMap gameMap;
    
    public GameMapTest() {
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
        
        this.gameMap.isTileBlocked(0, 0);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testEveryTileCreatedForMap() {
        for (int x=0; x < this.gameMap.getWidth(); x++) {
            for (int y=0; y < this.gameMap.getHeight(); y++) {
                assertEquals(x, this.gameMap.getTile(x, y).getX());
                assertEquals(y, this.gameMap.getTile(x, y).getY());
                assertEquals(TileType.EMPTY, this.gameMap.getTile(x, y).getType());
            }
        }
    }

    @Test
    public void testNoTilesBlockedOnEmptyMap() {
        for (int x=0; x < this.gameMap.getWidth(); x++) {
            for (int y=0; y < this.gameMap.getHeight(); y++) {
                assertEquals(false, this.gameMap.isTileBlocked(x, y));
            }
        }
    }
    
    @Test
    public void testWallTilesBlock() {
        this.gameMap.setTile(5, 5, TileType.WALL);
        
        assertEquals(true, this.gameMap.isTileBlocked(5, 5));
        
        this.gameMap.setTile(5, 5, TileType.EMPTY);
        
        assertEquals(false, this.gameMap.isTileBlocked(5, 5));
    }
}
