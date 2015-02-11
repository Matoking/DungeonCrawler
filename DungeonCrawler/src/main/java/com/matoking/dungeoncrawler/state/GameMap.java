package com.matoking.dungeoncrawler.state;

import com.matoking.dungeoncrawler.state.entities.Key;
import com.matoking.dungeoncrawler.state.entities.Skeleton;
import java.util.ArrayList;
import java.util.Random;

/**
 * Contains the map of the game as well as any entities inside EXCEPT for the player
 * 
 * @author matoking
 */
public class GameMap {
    public static int MAP_DEFAULT_WIDTH = 64;
    public static int MAP_DEFAULT_HEIGHT = 64;
    
    private GameState gameState;
    
    private int width;
    private int height;
    
    private Tile[][] tiles;
    private ArrayList<Entity> entities;
    
    public GameMap(GameState gameState, int width, int height) {
        this.width = width;
        this.height = height;
        
        this.gameState = gameState;
        
        this.tiles = new Tile[width][height];
        this.entities = new ArrayList<Entity>();
        
        this.createMap(width, height);
    }
    
    public GameMap(GameState gameState) {
        this(gameState, MAP_DEFAULT_WIDTH, MAP_DEFAULT_HEIGHT);
    }
    
    /**
     * Create an empty map with defined width and height
     * @param width The width of map
     * @param height The height of map
     */
    public void createMap(int width, int height) {
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
    
    public void setTile(Coordinate coordinate, TileType type) {
        this.setTile(coordinate.getX(), coordinate.getY(), type);
    }
    
    public Tile getTile(int x, int y) {
        if (x < 0 || x > this.getWidth()-1 ||
            y < 0 || y > this.getHeight()-1) {
            return null;
        }
        
        return this.tiles[x][y];
    }
    
    public Tile getTile(Coordinate coordinate) {
        return this.getTile(coordinate.getX(), coordinate.getY());
    }
    
    public TileType getTileType(int x, int y) {
        Tile tile = this.getTile(x, y);
        
        if (tile != null) {
            return tile.getType();
        } else {
            return TileType.EMPTY;
        }
    }
    
    /**
     * Is the tile blocked by an immovable object such as a wall
     * 
     * @return true if tile is blocked, false otherwise
     */
    public boolean isTileBlocked(int x, int y) {
        // The tile player is standing on is also considered to be blocked
        if (this.gameState.getPlayer().getX() == x &&
            this.gameState.getPlayer().getY() == y) {
            return true;
        }
        
        if (x < 0 || x > this.getWidth()-1 ||
            y < 0 || y > this.getHeight()-1) {
            return true;
        }
        
        Tile tile = this.tiles[x][y];
        
        if (tile.getType() == TileType.WALL) {
            return true;
        }
        
        ArrayList<Entity> entities = this.getEntitiesAt(x, y);
        
        for (Entity entity : entities) {
            if (entity.isObstacle()) {
                return true;
            }
        }
        
        return false;
    }
    
    public boolean isTileBlocked(Coordinate coordinate) {
        return this.isTileBlocked(coordinate.getX(), coordinate.getY());
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
    
    /**
     * Get all entities that occupy the selected coordinate
     * 
     * @param x X-coordinate
     * @param y Y-coordinate
     * @return ArrayList<Entity> with matching entities
     */
    public ArrayList<Entity> getEntitiesAt(int x, int y) {
        ArrayList<Entity> entities = new ArrayList<Entity>();
        
        for (Entity entity : this.entities) {
            if (entity.getX() == x && entity.getY() == y) {
                entities.add(entity);
            }
        }
        
        return entities;
    }
}
