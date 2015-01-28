package com.matoking.dungeoncrawler.state;

/**
 * Entity is an object that can be seen on the map and can be interacted with
 * 
 * @author matoking
 */
public interface Entity {
    public int getX();
    public int getY();
    
    /**
     * Can other entities/player move on top of this entity?
     * 
     * @return Is this entity an obstacle
     */
    public boolean isObstacle();
    
    /**
     * Called when the player has moved
     */
    public void step();
    
    /**
     * Called when the player touches the entity, either by moving on top of it or
     * trying to move on top of it
     */
    public void onPlayerTouch();
    
    /**
     * Get the image name used to render this image
     * 
     * @return Image name as a string
     */
    public String getImageName();
}
