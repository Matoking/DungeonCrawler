package com.matoking.dungeoncrawler.state;

import com.matoking.dungeoncrawler.generator.MapGenerator;
import java.util.ArrayList;

/**
 * The main class containing the game's state: GameMap, GameLog, Player
 */
public class GameState {
    private GameMap gameMap;
    private Pathfinding pathfinding;
    private MapGenerator mapGenerator;

    private GameLog gameLog;
    
    private Player player;
    
    public GameState() {
        this.gameMap = new GameMap(this);
        this.pathfinding = new Pathfinding(this);
        this.mapGenerator = new MapGenerator(this);
        
        this.gameLog = new GameLog(this);
        
        this.player = new Player(this.gameMap, 5, 5);
    }
    
    /**
     * Starts a new game
     */
    public void startGame() {
        this.gameMap = null;
        this.gameMap = new GameMap(this);
        
        this.mapGenerator.generateMap(GameMap.MAP_DEFAULT_WIDTH, GameMap.MAP_DEFAULT_HEIGHT);
        
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
        if (this.isGameWon() || this.isGameLost()) {
            return;
        }
        
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
        
        if (this.isGameLost()) {
            this.getGameLog().addMessage(GameMessages.getGameLostMessage());
        } else if (this.isGameWon()) {
            this.getGameLog().addMessage(GameMessages.getGameWonMessage());
        }
    }
    
    /**
     * Check if the player has won the game. In case he has, display a victory message
     * and prevent the player from moving
     */
    public boolean isGameWon() {
        if (this.getPlayer().getRemainingKeys() == 0) {
            return true;
        }
        
        return false;
    }
    
    /**
     * Check if the player has lost the game. In case he has, display a game over message
     * and prevent the player from moving
     */
    public boolean isGameLost() {
        if (this.getPlayer().getHealth() == 0) {
            return true;
        }
        
        return false;
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
    
    public MapGenerator getMapGenerator() {
        return mapGenerator;
    }
}
