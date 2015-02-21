/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.matoking.dungeoncrawler.state.entities;

import com.matoking.dungeoncrawler.state.Direction;
import com.matoking.dungeoncrawler.state.GameState;
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
public class AppleTest {
    public GameState gameState;
    
    public AppleTest() {
    }
    
    @Before
    public void setUp() {
        this.gameState = new GameState();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testPlayerRestoresHealthOnApplePickup() {
        this.gameState.getPlayer().setX(5);
        this.gameState.getPlayer().setY(5);
        
        this.gameState.getGameMap().addEntity(new Apple(this.gameState, 5, 6));
        
        assertEquals(50, this.gameState.getPlayer().getHealth());
        
        this.gameState.performMove(Direction.DOWN);
        
        // Apple restores 25 health
        assertEquals(75, this.gameState.getPlayer().getHealth());
    }
    
    @Test
    public void testPickingUpAppleDisplaysMessage() {
        this.gameState.getPlayer().setX(5);
        this.gameState.getPlayer().setY(5);
        
        this.gameState.getGameMap().addEntity(new Apple(this.gameState, 5, 6));
        
        assertEquals(0, this.gameState.getGameLog().getMessages().size());
        
        this.gameState.performMove(Direction.DOWN);
        
        assertEquals(1, this.gameState.getGameLog().getMessages().size());
    }
    
}
