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
    
    public boolean isFloor() {
        if (this.getType() == TileType.STONE_FLOOR || this.getType() == TileType.WOODEN_FLOOR ||
            this.getType() == TileType.STONE_CORRIDOR_FLOOR || this.getType() == TileType.WOODEN_CORRIDOR_FLOOR) {
            return true;
        } else {
            return false;
        }
    }
    
    public String getImageName() {
        switch (this.getType()) {
            case WALL:
                return "wall";
                
            case WOODEN_FLOOR:
                return "wooden_floor";
                
            case STONE_FLOOR:
                return "stone_floor";
                
            case WOODEN_CORRIDOR_FLOOR:
                return "wooden_corridor_floor";
                
            case STONE_CORRIDOR_FLOOR:
                return "stone_corridor_floor";
                
            default:
                return "empty";
        }
    }

    public void setType(TileType type) {
        this.type = type;
    }
}
