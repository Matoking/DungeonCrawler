/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.matoking.dungeoncrawler.state.entities;

import com.matoking.dungeoncrawler.state.Direction;
import com.matoking.dungeoncrawler.state.GameMap;
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
public class SkeletonTest {
    private GameState gameState;
    
    public SkeletonTest() {
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
    public void testSkeletonWillAggroIfPlayerWithinRange() {
        GameMap gameMap = this.gameState.getGameMap();
        this.gameState.getPlayer().setX(5);
        this.gameState.getPlayer().setY(5);
        
        gameMap.addEntity(new Skeleton(this.gameState, 6, 5));
                
        assertEquals("skeleton", gameMap.getEntitiesAt(6, 5).get(0).getImageName());
        
        // Skeleton will notice player after a turn has passed and the player is within range
        this.gameState.performMove(Direction.RIGHT);
        
        assertEquals("skeleton_aggro", gameMap.getEntitiesAt(6, 5).get(0).getImageName());
    }
    
    @Test
    public void testPlayerCanKillSkeleton() {
        GameMap gameMap = this.gameState.getGameMap();
        this.gameState.getPlayer().setX(5);
        this.gameState.getPlayer().setY(5);
        
        gameMap.addEntity(new Skeleton(this.gameState, 6, 5));
        
        assertEquals(gameMap.getEntitiesAt(6, 5).size(), 1);
        
        // Skeleton should die after player has hit it for ten turns straight
        for (int i=0; i < 10; i++) {
            this.gameState.performMove(Direction.RIGHT);
        }
        
        assertEquals(0, gameMap.getEntitiesAt(6, 5).size());
    }
    
    @Test
    public void testPlayerWillBeKilledWhenFacedWithAnUnsurmountableArmyOfSkeletons() {
        // Basically, spawn a bunch of skeletons within player's range
        // Common sense states player will be killed within ten turns
        // It's also way more amusing than creating a test that only tests whether
        // player can be damaged by a skeleton
        GameMap gameMap = this.gameState.getGameMap();
        this.gameState.getPlayer().setX(5);
        this.gameState.getPlayer().setY(5);
        
        gameMap.addEntity(new Skeleton(this.gameState, 6, 5));
        gameMap.addEntity(new Skeleton(this.gameState, 7, 5));
        gameMap.addEntity(new Skeleton(this.gameState, 8, 5));
        gameMap.addEntity(new Skeleton(this.gameState, 5, 4));
        gameMap.addEntity(new Skeleton(this.gameState, 6, 6));
        gameMap.addEntity(new Skeleton(this.gameState, 6, 6));
        gameMap.addEntity(new Skeleton(this.gameState, 4, 4));
        
        assertEquals(50, this.gameState.getPlayer().getHealth());
        
        for (int i=0; i < 10; i++) {
            this.gameState.performMove(Direction.RIGHT);
        }
        
        assertEquals(0, this.gameState.getPlayer().getHealth());        
    }
    
}
