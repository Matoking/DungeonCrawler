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
    
    // Amount of keys the player still has to collect
    private int remainingKeys;
    
    public Player(GameMap gameMap, int x, int y) {
        this.gameMap = gameMap;
        
        this.x = x;
        this.y = y;
        
        this.remainingKeys = 0;
        
        this.health = PLAYER_HEALTH;
    }
    
    /**
     * Move in a direction if the direction isn't blocked by a wall
     * 
     * @param direction 
     * @return True if player moved, false if the player was blocked by something
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
    
    /**
     * Returns player's position as a Coordinate
     * 
     * @return Coordinate with player's position
     */
    public Coordinate getCoordinate() {
        return new Coordinate(this.getX(), this.getY());
    }
    
    public void setRemainingKeys(int amount) {
        this.remainingKeys = amount;
    }

    public int getRemainingKeys() {
        return remainingKeys;
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
    
    public void decreaseHealth(int amount) {
        this.health -= amount;
        
        if (this.health < 0) {
            this.health = 0;
        }
    }
    
    public void decreaseKeys(int amount) {
        this.remainingKeys -= amount;
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
