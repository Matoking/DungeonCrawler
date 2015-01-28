package com.matoking.dungeoncrawler.state;

import java.util.ArrayList;
import java.util.Random;

/**
 * Contains the map of the game as well as any entities inside, apart from the player
 * 
 * @author matoking
 */
public class GameMap {
    public static int MAP_DEFAULT_WIDTH = 64;
    public static int MAP_DEFAULT_HEIGHT = 64;
    
    private int width;
    private int height;
    
    private Tile[][] tiles;
    private ArrayList<Entity> entities;
    
    public GameMap(int width, int height) {
        this.width = width;
        this.height = height;
        
        this.tiles = new Tile[width][height];
        this.entities = new ArrayList<Entity>();
        
        for (int x=0; x < width; x++) {
            for (int y=0; y < height; y++) {
                this.tiles[x][y] = new Tile(x, y, TileType.EMPTY);
            }
        }
    }
    
    public GameMap() {
        this(MAP_DEFAULT_WIDTH, MAP_DEFAULT_HEIGHT);
    }
    
    /**
     * Generate the map
     * This will probably be offloaded to a different class but for now do some
     * placeholder testing
     * 
     */
    public void generateMap() {
        Random random = new Random();
        
        int length = random.nextInt(25) + 30;
        for (int x=2; x < 2 + length; x++) {
            for (int y=2; y < 2 + length; y++) {
                this.setTile(x, y, TileType.WALL);
            }
        }
        
        for (int x=3; x < 1 + length; x++) {
            for (int y=3; y < 1 + length; y++) {
                this.setTile(x, y, random.nextInt(100) < 85 ? TileType.FLOOR : TileType.WALL);
            }
        }
        
        this.setTile(5, 5, TileType.FLOOR);
    }
    
    /**
     * Set selected tile to a new type
     * 
     * @param x     X-coordinate of the tile
     * @param y     Y-coordinate of the tile
     * @param type  New coordinate of the tile
     */
    public void setTile(int x, int y, TileType type) {
        this.tiles[x][y].setType(type);
    }
    
    public Tile getTile(int x, int y) {
        if (x < 0 || x > this.getWidth()-1 ||
            y < 0 || y > this.getHeight()-1) {
            return null;
        }
        
        return this.tiles[x][y];
    }   
    
    /**
     * Is the tile blocked by an immovable object such as a wall
     * 
     * @return true if tile is blocked, false otherwise
     */
    public boolean isTileBlocked(int x, int y) {
        if (x < 0 || x > this.getWidth()-1 ||
            y < 0 || y > this.getHeight()-1) {
            return true;
        }
        
        Tile tile = this.tiles[x][y];
        
        return tile.getType() == TileType.WALL;
    }
    
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public void addEntity(Entity entity) {
        this.entities.add(entity);
    }
    
    public void removeEntity(Entity entity) {
        this.entities.remove(entity);
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }
}
