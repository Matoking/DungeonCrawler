/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.matoking.dungeoncrawler.state.entities;

import com.matoking.dungeoncrawler.state.Direction;
import com.matoking.dungeoncrawler.state.GameState;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author matoking
 */
public class KeyTest {
    public GameState gameState;
    
    public KeyTest() {
        this.gameState = new GameState();
    }
    
    @Before
    public void setUp() {
    }
    
    @Test
    public void testPlayerPicksUpKey() {
        this.gameState.getPlayer().setX(5);
        this.gameState.getPlayer().setY(5);
        this.gameState.getPlayer().setRemainingKeys(2);
        
        this.gameState.getGameMap().addEntity(new Key(this.gameState, 5, 6));
        
        assertEquals(this.gameState.getPlayer().getRemainingKeys(), 2);

        // When player moves down, he should pick up the key and remove
        // the entity representing the key
        this.gameState.performMove(Direction.DOWN);
        
        assertEquals(this.gameState.getGameMap().getEntities().size(), 0);
        assertEquals(this.gameState.getPlayer().getRemainingKeys(), 1);
    }
    
    @Test
    public void testPickingUpKeyDisplaysMessages() {
        this.gameState.getPlayer().setX(5);
        this.gameState.getPlayer().setY(5);
        
        this.gameState.getGameMap().addEntity(new Key(this.gameState, 6, 5));
        
        assertEquals(0, this.gameState.getGameLog().getMessages().size());
        
        this.gameState.performMove(Direction.RIGHT);
        
        assertEquals(2, this.gameState.getGameLog().getMessages().size());
    }
}
