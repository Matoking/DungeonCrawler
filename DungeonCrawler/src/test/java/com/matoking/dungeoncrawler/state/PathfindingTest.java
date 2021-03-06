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
    
    @Before
    public void setUp() {
        this.gameState = new GameState();
        
        this.pathfinding = this.gameState.getPathfinding();
        this.gameMap = this.gameState.getGameMap();
    }
    
    @Test
    public void testCoordinateConversionToInt() {
        for (int x = 0; x < this.gameMap.getWidth(); x++) {
            for (int y = 0; y < this.gameMap.getHeight(); y++) {
                int converted = this.pathfinding.convertToInt(x, y);
                Coordinate coordinate = this.pathfinding.convertFromInt(converted);
                
                assertEquals(coordinate.getX(), x);
                assertEquals(coordinate.getY(), y);
            }
        }
    }
    
    @Test
    public void testPathfindingCorrectPathOnMaze() {
        int[][] map = new int[6][10];
        
        map[0] = new int[] {1,1,1,1,1,1,1,1,1,1};
        map[1] = new int[] {1,0,0,1,1,1,0,0,0,1};
        map[2] = new int[] {1,1,0,1,1,1,0,0,0,1};
        map[3] = new int[] {1,1,0,1,1,1,1,1,0,1};
        map[4] = new int[] {1,1,0,0,0,0,0,0,0,1};
        map[5] = new int[] {1,1,1,1,1,1,1,1,1,1};
        
        for (int x=0; x < 6; x++) {
            for (int y=0; y < 10; y++) {
                this.gameMap.setTile(y, x, map[x][y] == 0 ? TileType.WOODEN_FLOOR : TileType.WALL);
            }
        }
        
        this.pathfinding.calculateShortestPath(new Coordinate(1, 1), new Coordinate(6, 1));
        Coordinate firstStep = this.pathfinding.getNextStepToGoal();
        
        // First step is on the right
        assertEquals(new Coordinate(2, 1), firstStep);
    }
    
    @Test
    public void testPathfindingCorrectStepCountOnMaze() {
        int[][] map = new int[6][10];
        
        map[0] = new int[] {1,1,1,1,1,1,1,1,1,1};
        map[1] = new int[] {1,0,0,1,1,1,0,0,0,1};
        map[2] = new int[] {1,1,0,1,1,1,0,0,0,1};
        map[3] = new int[] {1,1,0,1,1,1,1,1,0,1};
        map[4] = new int[] {1,1,0,0,0,0,0,0,0,1};
        map[5] = new int[] {1,1,1,1,1,1,1,1,1,1};
        
        for (int x=0; x < 6; x++) {
            for (int y=0; y < 10; y++) {
                this.gameMap.setTile(y, x, map[x][y] == 0 ? TileType.WOODEN_FLOOR : TileType.WALL);
            }
        }
        
        this.pathfinding.calculateShortestPath(new Coordinate(1, 1), new Coordinate(6, 1));
        
        // 15 steps to reach the goal
        assertEquals(15, this.pathfinding.getStepsToGoal());
    }
    
}
