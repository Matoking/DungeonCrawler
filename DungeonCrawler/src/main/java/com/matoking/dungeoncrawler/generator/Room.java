package com.matoking.dungeoncrawler.generator;

import com.matoking.dungeoncrawler.state.Coordinate;
import com.matoking.dungeoncrawler.state.TileType;
import java.awt.Rectangle;

/**
 * Represents a room for use in map generation
 * 
 * @author matoking
 */
public class Room {
    Rectangle rectangle;
    
    private TileType floorType;
    
    public Room (int x, int y, int width, int height) {
        this.rectangle = new Rectangle(x, y, width, height);
    }
    
    /**
     * Does this room intersect with the other room
     * 
     * @param room The room to compare to
     * @return True if the rooms intersect, false if they don't
     */
    public boolean intersects(Room room) {
        return this.rectangle.intersects(room.getRectangle());
    }
    
    /**
     * Get a coordinate representing the center of this room
     * 
     * @return Center of the room as Coordinate
     */
    public Coordinate getCenterCoordinate() {
        Coordinate coordinate = new Coordinate(this.getX() + (this.getWidth() / 2),
                                               this.getY() + (this.getHeight() / 2));
        
        return coordinate;
    }
    
    public void setFloorType(TileType floorType) {
        this.floorType = floorType;
    }
    
    public TileType getFloorType() {
        return this.floorType;
    }
    
    public Rectangle getRectangle() {
        return this.rectangle;
    }
    
    public int getX() {
        return (int)this.rectangle.getX();
    }
    
    public int getY() {
        return (int)this.rectangle.getY();
    }
    
    public int getWidth() {
        return (int)this.rectangle.getWidth();
    }
    
    public int getHeight() {
        return (int)this.rectangle.getHeight();
    }
    
}
