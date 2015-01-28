package com.matoking.dungeoncrawler.state.entities;

import com.matoking.dungeoncrawler.state.Entity;

/**
 *
 * @author matoking
 */
public class Key implements Entity {
    private int x;
    private int y;
    
    public Key(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // The key won't block anything on the map
    @Override
    public boolean isObstacle() { return false; }

    // The key won't move once placed
    @Override
    public void step() {}

    // When player touches the button, remove the key entity and increase player's key count
    @Override
    public void onPlayerTouch() {
        
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
