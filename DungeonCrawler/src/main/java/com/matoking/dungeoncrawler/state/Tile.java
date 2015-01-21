package com.matoking.dungeoncrawler.state;

/**
 * Tile that is placed on every spot on the map
 * 
 * @author matoking
 */
public class Tile {
    private int x;
    private int y;
    
    private TileType type;
    
    /**
     * Creates a Tile.
     * 
     * @param x     X-coordinate of the tile
     * @param y     Y-coordinate of the tile
     * @param type  Type of the tile (eg. EMPTY, WALL)
     */
    public Tile(int x, int y, TileType type) {
        this.x = x;
        this.y = y;
        
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public TileType getType() {
        return type;
    }
    
    public String getImageName() {
        switch (this.getType()) {
            case WALL:
                return "wall";
                
            case FLOOR:
                return "floor";
                
            default:
                return "n/a";
        }
    }

    public void setType(TileType type) {
        this.type = type;
    }
}
