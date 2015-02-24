package com.matoking.dungeoncrawler.state.entities;

import com.matoking.dungeoncrawler.state.Coordinate;
import com.matoking.dungeoncrawler.state.Direction;
import com.matoking.dungeoncrawler.state.Entity;
import com.matoking.dungeoncrawler.state.GameMap;
import com.matoking.dungeoncrawler.state.GameMessages;
import com.matoking.dungeoncrawler.state.GameState;
import com.matoking.dungeoncrawler.state.Player;
import java.util.ArrayList;
import java.util.Random;

/**
 * Skeleton is a simple-ish enemy character that wanders around when not chasing the player.
 * 
 * When the player comes into skeleton's view, the skeleton starts chasing the player around
 * until the player has left the skeleton's view, after which the skeleton chases the player
 * only for the defined amount of steps. After that, skeleton continues wandering.
 * 
 * @author matoking
 */
public class Skeleton implements Entity {
    private final static int DEFAULT_HEALTH = 50;
    private final static int DEFAULT_CHASE_STEP_COUNT = 10;
    
    private final static int MIN_DAMAGE_TO_PLAYER = 2;
    private final static int MAX_DAMAGE_TO_PLAYER = 5;
    
    private final static int ENEMY_AREA_RADIUS = 8;
    
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
            // Enemy isn't chasing the player, see if the player is visible to the enemy
            if (this.isPlayerVisible()) {
                // Start chasing the player
                this.chaseSteps = DEFAULT_CHASE_STEP_COUNT;
                this.gameState.getGameLog().addMessage(GameMessages.getSkeletonStartsChase());
                return;
            }
            
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
        } else {
            // Enemy is chasing the player
            Coordinate nextStep = this.getNextStepTowardsPlayer();
            Coordinate playerCoordinate = this.gameState.getPlayer().getCoordinate();
            
            // If we can't find a path to player, just stand still
            if (nextStep == null) {
                return;
            } else if (nextStep.equals(playerCoordinate)) {
                // Player is right next to the enemy, attack
                int damageToPlayer = this.getAttackPower();
                
                this.gameState.getPlayer().decreaseHealth(damageToPlayer);
                this.gameState.getGameLog().addMessage(GameMessages.getSkeletonHitsPlayer(damageToPlayer));
            } else {
                // Move towards the player
                this.setX(nextStep.getX());
                this.setY(nextStep.getY());
            }
            
            // If player is out of the enemy's view, 
            // start substracting the enemy's chase steps.
            // When it reaches zero, the enemy will stop chasing the player
            if (!this.isPlayerVisible()) {
                chaseSteps--;
            }
            
            if (chaseSteps == 0) {
                this.gameState.getGameLog().addMessage(GameMessages.getSkeletonEndsChase());
            }
        }
    }
    
    /**
     * Get enemy's attack power
     * 
     * @return Enemy's attack power as an integer
     */
    private int getAttackPower() {
        Random random = new Random();
        
        return random.nextInt(MAX_DAMAGE_TO_PLAYER - MIN_DAMAGE_TO_PLAYER) + MIN_DAMAGE_TO_PLAYER;
    }
    
    /**
     * Is the player visible to the enemy
     * 
     * @return true if player is visible, false if not
     */
    private boolean isPlayerVisible() {
        int playerX = this.gameState.getPlayer().getX();
        int playerY = this.gameState.getPlayer().getY();
        
        // First, check if player's coordinates are outside the enemy's possible area of view
        // If so, return false immediately
        if (playerX < this.getX() - (ENEMY_AREA_RADIUS / 2) || playerX > this.getX() + (ENEMY_AREA_RADIUS / 2) ||
            playerY < this.getY() - (ENEMY_AREA_RADIUS / 2) || playerY > this.getY() + (ENEMY_AREA_RADIUS / 2)) {
            return false;
        }
        
        // Check if the enemy can reach the player in five or less steps
        // If so, consider player visible
        this.gameState.getPathfinding().calculateShortestPath(new Coordinate(this.getX(), this.getY()), this.gameState.getPlayer().getCoordinate());
        
        return this.gameState.getPathfinding().getStepsToGoal() <= 5;
    }
    
    /**
     * Get the next towards player as a Coordinate
     * 
     * @return Coordinate for the next step if a path is found, null otherwise
     */
    private Coordinate getNextStepTowardsPlayer() {
        Player player = this.gameState.getPlayer();
        
        Coordinate playerCoordinate = new Coordinate(player.getX(), player.getY());
        Coordinate enemyCoordinate = new Coordinate(this.getX(), this.getY());
        
        this.gameState.getPathfinding().calculateShortestPath(enemyCoordinate, playerCoordinate);
        return this.gameState.getPathfinding().getNextStepToGoal();
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
        if (this.chaseSteps == 0) {
            return "skeleton";
        } else {
            return "skeleton_aggro";
        }
    }
    
}
