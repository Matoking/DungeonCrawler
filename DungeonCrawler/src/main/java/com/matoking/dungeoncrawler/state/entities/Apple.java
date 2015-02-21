package com.matoking.dungeoncrawler.state.entities;

import com.matoking.dungeoncrawler.state.Entity;
import com.matoking.dungeoncrawler.state.GameMessages;
import com.matoking.dungeoncrawler.state.GameState;

/**
 * Apple is an item that restores player's health
 * 
 * @author matoking
 */
public class Apple implements Entity {
   private final static int HEALTH_AMOUNT = 25;
    
   private int x;
   private int y;
   
   private GameState gameState;
   
   public Apple(GameState gameState, int x, int y) {
       this.gameState = gameState;
       
       this.x = x;
       this.y = y;
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
    public boolean isObstacle() { 
        return false; 
    }

    @Override
    public void step() {}

    // When player picks up the apple, restore health
    @Override
    public void onPlayerTouch() {
        this.gameState.getPlayer().increaseHealth(HEALTH_AMOUNT);
        this.gameState.getGameMap().removeEntity(this);
        this.gameState.getGameLog().addMessage(GameMessages.getApplePickupMessage(HEALTH_AMOUNT));
    }
    

    @Override
    public String getImageName() {
        return "apple";
    }
}
