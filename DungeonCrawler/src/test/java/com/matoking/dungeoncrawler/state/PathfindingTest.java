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
public class PathfindingTest {
    public GameState gameState;
    
    public Pathfinding pathfinding;
    public GameMap gameMap;
    
    public PathfindingTest() {
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
        
        this.pathfinding = new Pathfinding(this.gameState);
        this.gameMap = this.gameState.getGameMap();
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testCoordinateConversionToInt() {
        for (int x = 0; x < 64; x++) {
            for (int y = 0; y < 64; y++) {
                int converted = this.pathfinding.convertToInt(x, y);
                Coordinate coordinate = this.pathfinding.convertFromInt(converted);
                
                assertEquals(coordinate.getX(), x);
                assertEquals(coordinate.getY(), y);
            }
        }
    }
    
    @Test
    public void testPathfinding() {
        int[][] map = new int[6][10];
        
        map[0] = new int[] {1,1,1,1,1,1,1,1,1,1};
        map[1] = new int[] {1,0,0,1,1,1,0,0,0,1};
        map[2] = new int[] {1,1,0,1,1,1,0,0,0,1};
        map[3] = new int[] {1,1,0,1,1,1,1,1,0,1};
        map[4] = new int[] {1,1,0,0,0,0,0,0,0,1};
        map[5] = new int[] {1,1,1,1,1,1,1,1,1,1};
        
        for (int x=0; x < 6; x++) {
            for (int y=0; y < 10; y++) {
                this.gameMap.setTile(x, y, map[x][y] == 0 ? TileType.WOODEN_FLOOR : TileType.WALL);
            }
        }
        
        Coordinate firstStep = this.pathfinding.getNextStepTo(new Coordinate(1,1), new Coordinate(1, 6));
        
        // First step is on the right
        assertEquals(firstStep, new Coordinate(1, 2));
        
        // 15 steps to reach the goal
        assertEquals(this.pathfinding.getStepsToGoal(), 15);
    }
    
}
