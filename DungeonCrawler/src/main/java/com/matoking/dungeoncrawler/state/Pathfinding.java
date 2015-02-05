package com.matoking.dungeoncrawler.state;

import com.matoking.dungeoncrawler.state.entities.Key;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Class responsible for doing pathfinding. Uses Dijkstra's algorithm,
 * though A* could be more ideal
 * 
 * @author matoking
 */
public class Pathfinding {
    private GameState gameState;
    
    private ArrayList<Integer>[] graph;
    private int[] previous;
    
    private int[] distance;
    private int[][] length;
    
    private boolean[] scanned;
    
    public PriorityQueue<Coordinate> queue;
    
    public Pathfinding(GameState gameState) {
        this.gameState = gameState;
    }
    
    /**
     * Calculates the shortest path to the goal and then returns the Coordinate to the next step
     * leading to the goal
     * 
     * @param start Coordinate of the starting position
     * @param goal Coordinate of the goal
     * @return Direction to move to in order to follow the shortest path to the goal
     */
    public Coordinate getNextStepTo(Coordinate start, Coordinate goal) {
        this.calculateShortestPath(start, goal);
        
        int target = convertToInt(goal.getX(), goal.getY());
        
        // We are only interested in the very first step leading to the goal, so get that
        int stepsToGoal = 0;
        ArrayDeque<Coordinate> nodesToTarget = new ArrayDeque<Coordinate>();
        while (this.previous[target] != 0) {
            nodesToTarget.add(convertFromInt(target));
            target = this.previous[target];
            stepsToGoal++;
        }
        
        Coordinate coordinate = null;
        while (!nodesToTarget.isEmpty()) {
            coordinate = nodesToTarget.poll();
        }
        
        if (coordinate == null) {
            return null;
        } else {
            return coordinate;
        }
    }
    
    /**
     * Calculate the shortest path to a given coordinate. After this is done,
     * the path to the goal can be retrieved.
     * 
     * @param start
     * @param goal 
     */
    public void calculateShortestPath(Coordinate start, Coordinate goal) {
        GameMap gameMap = this.gameState.getGameMap();
        
        int nodeCount = gameMap.getWidth() * gameMap.getHeight();
        
        int startNode = convertToInt(start);
        int goalNode = convertToInt(goal);
        
        this.graph = new ArrayList[nodeCount];
        
        for (int i=0; i < nodeCount; i++) {
            this.graph[i] = new ArrayList<Integer>();
        }
        
        this.previous = new int[nodeCount];
        
        this.distance = new int[nodeCount];
        this.length = new int[nodeCount][nodeCount];
        this.scanned = new boolean[nodeCount];
        
        this.queue = new PriorityQueue<Coordinate>();
        
        // Add all of the tiles in the map
        for (int x=0; x < gameMap.getWidth(); x++) {
            for (int y=0; y < gameMap.getHeight(); y++) {
                // Skip the tile entirely if it is blocked
                // Also assume that the start and goal are non-solid, even if they aren't
                Coordinate coordinate = new Coordinate(x, y);
                
                if (gameMap.isTileBlocked(x, y) && !coordinate.equals(start) &&
                                                   !coordinate.equals(goal)) {
                    continue;
                }
                
                // Add neighboring tiles if they are not blocked either
                for (Direction direction : Direction.values()) {
                    Coordinate neighbor = Coordinate.getNewCoordinates(direction, x, y);
                    
                    if (!gameMap.isTileBlocked(neighbor) || neighbor.equals(start) ||
                                                            neighbor.equals(goal)) {
                        int from = convertToInt(coordinate);
                        int to = convertToInt(neighbor);
                        
                        this.graph[from].add(to);
                        this.graph[to].add(from);
                        
                        this.length[from][to] = 1;
                        this.length[to][from] = 1;
                    }
                }
            }
        }
        
        this.distance[startNode] = 0;
        this.previous[startNode] = 0;
        
        for (int i=0; i < this.graph.length; i++) {
            if (this.graph[i].isEmpty()) {
                continue;
            }
            
            if (i != startNode) {
                this.distance[i] = Integer.MAX_VALUE;
                this.previous[i] = 0;
            }
            
            Coordinate coordinate = convertFromInt(i);
            coordinate.setWeight(this.distance[i]);
            this.queue.add(coordinate);
        }
        
        int steps = 0;
        
        while (!this.queue.isEmpty()) {
            Coordinate smallest = this.queue.poll();
            
            int node = convertToInt(smallest);
            
            if (node == goalNode) {
                break;
            }
            
            for (int neighbor : this.graph[node]) {
                int alt = this.distance[node] + this.length[node][neighbor];
                
                if (alt < this.distance[neighbor]) {
                    this.distance[neighbor] = alt;
                    this.previous[neighbor] = node;
                    
                    Coordinate newCoordinate = convertFromInt(neighbor);
                    newCoordinate.setWeight(this.distance[neighbor]);
                    this.queue.add(newCoordinate);
                }
            }
        }
    }
    
    /**
     * Convert coordinate to an integer
     * 
     * @param x X-coordinate
     * @param y Y-coordinate
     * @return int representing the coordinate
     */
    public int convertToInt(int x, int y) {
        return (x * this.gameState.getGameMap().getHeight()) + y;
    }
    
    public int convertToInt(Coordinate coordinate) {
        return this.convertToInt(coordinate.getX(), coordinate.getY());
    }
    
    /**
     * Convert coordinate from an integer to a Coordinate
     * 
     * @param i Coordinate represented as a single integer
     * @return Coordinate
     */
    public Coordinate convertFromInt(int i) {
        return new Coordinate(i / this.gameState.getGameMap().getHeight(), 
                              i % this.gameState.getGameMap().getHeight());
    }
}
