package com.matoking.dungeoncrawler.state;

import com.matoking.dungeoncrawler.state.entities.Key;
import java.util.ArrayList;

public class GameState {
    private GameMap gameMap;
    private GameLog gameLog;
    
    private Player player;
    
    public GameState() {
        this.gameMap = new GameMap(this);
        this.gameLog = new GameLog(this);
        
        this.player = new Player(this.gameMap, 5, 5);
        
        // Test entities and stuff here
        this.gameMap.addEntity(new Key(this, 7, 7));
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
}
