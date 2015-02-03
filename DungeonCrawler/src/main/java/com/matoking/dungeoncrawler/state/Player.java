package com.matoking.dungeoncrawler.state;

import java.util.Random;

/**
 * The main Player class
 * 
 * @author matoking
 */
public class Player {
    private static final int PLAYER_HEALTH = 50;
    
    private GameMap gameMap;
    
    private int x;
    private int y;
    
    // Player's health, starts at
    private int health;
    
    // Amount of keys the player has
    private int keys;
    
    public Player(GameMap gameMap, int x, int y) {
        this.gameMap = gameMap;
        
        this.x = x;
        this.y = y;
        
        this.keys = 0;
        
        this.health = PLAYER_HEALTH;
    }
    
    /**
     * Move in a direction if the direction isn't blocked by a wall
     * 
     * @param direction 
     */
    public boolean move(Direction direction) {
        Coordinate newCoordinates = Coordinate.getNewCoordinates(direction, x, y);
        
        int newX = newCoordinates.getX();
        int newY = newCoordinates.getY();
        
        if (this.gameMap.isTileBlocked(newX, newY)) {
            return false;
        }
        
        this.x = newX;
        this.y = newY;
        
        return true;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getKeys() {
        return keys;
    }

    /**
     * Get player's health
     * 
     * @return player's health
     */
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
    
    public void increaseKeys(int amount) {
        this.keys += amount;
    }
    
    /**
     * Get the attack power of the player
     * 
     * @return Amount of damage the player should deal to the enemy
     */
    public int getAttackPower() {
        Random random = new Random();
        
        return random.nextInt(5) + 7;
    }
}
