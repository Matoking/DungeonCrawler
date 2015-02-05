package com.matoking.dungeoncrawler.state;

import java.util.ArrayList;
import java.util.Random;

/**
 * The main class containing the game's state: GameMap, GameLog, Player
 */
public class GameState {
    private GameMap gameMap;
    private Pathfinding pathfinding;
    
    private GameLog gameLog;
    
    private Player player;
    
    public GameState() {
        this.gameMap = new GameMap(this);
        
        this.gameLog = new GameLog(this);
        
        this.player = new Player(this.gameMap, 5, 5);
        this.pathfinding = new Pathfinding(this);
        
        this.startGame();
    }
    
    /**
     * Starts a new game
     */
    public void startGame() {
        this.gameMap = null;
        this.gameMap = new GameMap(this);
        
        this.player = null;
        this.player = new Player(this.gameMap, 5, 5);
        
        this.gameLog.clearMessages();
        
        this.gameLog.addMessage(GameMessages.getGameStartMessage());
    }
    
    /**
     * Move the player and interact with the entities on top of the player,
     * or if the player is blocked by an entity, interact with the blocking entities
     * 
     * @param direction Direction to perform a move to
     */
    public void performMove(Direction direction) {
        ArrayList<Entity> entities = null;
        if (!this.player.move(direction)) {
            entities = this.gameMap.getEntitiesAt(Coordinate.getNewX(direction, this.player.getX()), 
                                                  Coordinate.getNewY(direction, this.player.getY()));
        } else {
            entities = this.gameMap.getEntitiesAt(this.player.getX(), this.player.getY());
        }
        
        // First, make player interact with any possible entities he's touching
        for (Entity entity : entities) {
            entity.onPlayerTouch();
        }
        
        // Secondly, run step on each entity
        for (Entity entity : this.getGameMap().getEntities()) {
            entity.step();
        }
    }

    public GameLog getGameLog() {
        return gameLog;
    }

    public GameMap getGameMap() {
        return this.gameMap;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Pathfinding getPathfinding() {
        return pathfinding;
    }
}
