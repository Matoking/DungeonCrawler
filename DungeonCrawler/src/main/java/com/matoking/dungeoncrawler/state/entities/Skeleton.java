package com.matoking.dungeoncrawler.state.entities;

import com.matoking.dungeoncrawler.state.Coordinate;
import com.matoking.dungeoncrawler.state.Direction;
import com.matoking.dungeoncrawler.state.Entity;
import com.matoking.dungeoncrawler.state.GameMap;
import com.matoking.dungeoncrawler.state.GameMessages;
import com.matoking.dungeoncrawler.state.GameState;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author matoking
 */
public class Skeleton implements Entity {
    private final static int DEFAULT_HEALTH = 50;
    
    private GameState gameState;
    
    private int x;
    private int y;
    
    private int health;
    
    // For how many steps should this enemy chase the player
    // before giving up
    private int chaseSteps;
    
    public Skeleton(GameState gameState, int x, int y) {
        this.gameState = gameState;
        
        this.x = x;
        this.y = y;
        
        this.health = DEFAULT_HEALTH;
        
        this.chaseSteps = 0;
    }
    
    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }
    
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean isObstacle() {
        return true;
    }
    
    /**
     * Decrease the enemy's health by the provided amount
     * 
     * @param amount How much damage should the enemy lose
     */
    public void decreaseHealth(int amount) {
        this.health -= amount;
        
        if (this.health < 0) {
            this.health = 0;
        }
    }

    @Override
    public void step() {
        GameMap gameMap = this.gameState.getGameMap();
        
        // If the enemy isn't chasing the player, just wander around aimlessly
        if (this.chaseSteps == 0) {
            ArrayList<Direction> availableDirections = new ArrayList<Direction>();
            
            // Add all directions which aren't blocked
            for (Direction direction : Direction.values()) {
                if (!gameMap.isTileBlocked(Coordinate.getNewCoordinates(direction, this.getX(), this.getY()))) {
                    availableDirections.add(direction);
                }
            }
            
            // If there is at least one direction the enemy can move into, pick a random
            // direction and move there
            if (availableDirections.size() > 0) {
                Random random = new Random();
                
                Direction randomDirection = availableDirections.size() > 1 ? availableDirections.get(random.nextInt(availableDirections.size())) : availableDirections.get(0);
                
                this.setX(Coordinate.getNewX(randomDirection, x));
                this.setY(Coordinate.getNewY(randomDirection, y));
            }
        }
    }

    /**
     * If this enemy is touched by the player, it means the player is attacking the enemy
     */
    @Override
    public void onPlayerTouch() {
        int attackPower = this.gameState.getPlayer().getAttackPower();
        
        this.decreaseHealth(attackPower);
        
        if (this.health > 0) {
            this.gameState.getGameLog().addMessage(GameMessages.getPlayerHitsSkeleton(attackPower));
        } else {
            this.gameState.getGameLog().addMessage(GameMessages.getPlayerKillsSkeleton(attackPower));
            this.gameState.getGameMap().removeEntity(this);
        }
    }

    @Override
    public String getImageName() {
        return "skeleton";
    }
    
}
