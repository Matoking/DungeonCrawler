/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.matoking.dungeoncrawler.state;

import com.matoking.dungeoncrawler.state.entities.Key;
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
    public GameState gameState;
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
        this.gameState = new GameState();
        this.gameMap = this.gameState.getGameMap();
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testEveryTileCreatedIsEmpty() {
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
                // Player is placed on 5x5
                if (x != 5 && y != 5) {
                    assertEquals(false, this.gameMap.isTileBlocked(x, y));
                }
            }
        }
    }
    
    @Test
    public void testWallTilesBlock() {
        this.gameMap.setTile(5, 5, TileType.WALL);
        
        assertEquals(true, this.gameMap.isTileBlocked(5, 5));
        
        this.gameMap.setTile(7, 7, TileType.EMPTY);
        
        assertEquals(false, this.gameMap.isTileBlocked(7, 7));
    }
    
    @Test
    public void testWallTileImageNames() {
        this.gameMap.setTile(5, 5, TileType.EMPTY);
        assertEquals("empty", this.gameMap.getTile(5, 5).getImageName());
        
        this.gameMap.setTile(5, 5, TileType.WALL);
        assertEquals("wall", this.gameMap.getTile(5, 5).getImageName());
        
        this.gameMap.setTile(5, 5, TileType.FLOOR);
        assertEquals("floor", this.gameMap.getTile(5, 5).getImageName());
    }
    
    @Test
    public void testNullReturnedOnInvalidTile() {
        assertEquals(null, this.gameMap.getTile(-1,-1));
        
        assertEquals(null, this.gameMap.getTile(this.gameMap.getWidth(), this.gameMap.getHeight()));
    }
    
    @Test
    public void testGetEntitiesWorksCorrectly() {
        this.gameMap.addEntity(new Key(this.gameState, 0, 5));
        this.gameMap.addEntity(new Key(this.gameState, 2, 5));
        this.gameMap.addEntity(new Key(this.gameState, 4, 6));
        
        assertEquals(this.gameMap.getEntities().size(), 3);
    }
    
    @Test
    public void testGetEntitiesAtReturnsCorrectEntities() {
        this.gameMap.addEntity(new Key(this.gameState, 10, 10));
        this.gameMap.addEntity(new Key(this.gameState, 10, 10));
        
        this.gameMap.addEntity(new Key(this.gameState, 20, 25));
        this.gameMap.addEntity(new Key(this.gameState, 20, 25));
        this.gameMap.addEntity(new Key(this.gameState, 20, 25));
        this.gameMap.addEntity(new Key(this.gameState, 20, 25));
        this.gameMap.addEntity(new Key(this.gameState, 20, 25));
        
        
        assertEquals(this.gameMap.getEntitiesAt(10, 10).size(), 2);
        assertEquals(this.gameMap.getEntitiesAt(20, 25).size(), 5);
    }
    
    @Test
    public void testRemoveEntityWorksCorrectly() {
        Key keyOne = new Key(this.gameState, 5, 5);
        Key keyTwo = new Key(this.gameState, 5, 5);
        
        this.gameMap.addEntity(keyOne);
        this.gameMap.addEntity(keyTwo);
        
        assertEquals(true, this.gameMap.getEntities().contains(keyOne));
        assertEquals(true, this.gameMap.getEntities().contains(keyTwo));
        
        this.gameMap.removeEntity(keyOne);
        
        assertEquals(false, this.gameMap.getEntities().contains(keyOne));
        assertEquals(true, this.gameMap.getEntities().contains(keyTwo));
    }
}
