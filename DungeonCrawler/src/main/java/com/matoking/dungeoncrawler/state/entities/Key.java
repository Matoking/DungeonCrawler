package com.matoking.dungeoncrawler.state.entities;

import com.matoking.dungeoncrawler.state.Entity;
import com.matoking.dungeoncrawler.state.GameMessages;
import com.matoking.dungeoncrawler.state.GameState;

/**
 * Key is an item the player can pick up. Once player has collected all the keys,
 * the game is won
 * 
 * @author matoking
 */
public class Key implements Entity {
    private int x;
    private int y;
    
    private GameState gameState;
    
    public Key(GameState gameState, int x, int y) {
        this.gameState = gameState;
        
        this.x = x;
        this.y = y;
    }

    // The key won't block anything on the map
    @Override
    public boolean isObstacle() { 
        return false; 
    }

    // The key won't move once placed
    @Override
    public void step() {}

    // When player touches the button, remove the key entity and increase player's key count
    @Override
    public void onPlayerTouch() {
        this.gameState.getPlayer().decreaseKeys(1);
        this.gameState.getGameMap().removeEntity(this);
        this.gameState.getGameLog().addMessage(GameMessages.getKeyPickupMessage());
        
        if (this.gameState.getPlayer().getRemainingKeys() != 0) {
            this.gameState.getGameLog().addMessage(GameMessages.getKeysRemainingMessage(this.gameState.getPlayer().getRemainingKeys()));
        }
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public String getImageName() {
        return "key";
    }
    
}
