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
    
    @Test
    public void testStartGameClearsLog() {
        this.gameState.startGame();
        
        this.gameState.getGameLog().addMessage("aaa");
        this.gameState.getGameLog().addMessage("aaa");
        this.gameState.getGameLog().addMessage("aaa");
        
        assertEquals(this.gameState.getGameLog().getMessages().size(), 4);
        
        this.gameState.startGame();
        
        // When game is started, game log should have only one message
        assertEquals(this.gameState.getGameLog().getMessages().size(), 1);
    }
    
    @Test
    public void playerInteractsWithNonSolidEntity() {
        this.gameState.getPlayer().setX(5);
        this.gameState.getPlayer().setY(5);
        
        this.gameState.getGameMap().addEntity(new Key(this.gameState, 5, 6));
        
        assertEquals(this.gameState.getPlayer().getKeys(), 0);

        // When player moves down, he should pick up the key and remove
        // the entity representing the key
        this.gameState.performMove(Direction.DOWN);
        
        assertEquals(this.gameState.getGameMap().getEntities().size(), 0);
        assertEquals(this.gameState.getPlayer().getKeys(), 1);
    }
    
}
