package com.matoking.dungeoncrawler.generator;

import com.matoking.dungeoncrawler.state.Coordinate;
import com.matoking.dungeoncrawler.state.Direction;
import com.matoking.dungeoncrawler.state.GameMap;
import com.matoking.dungeoncrawler.state.GameState;
import com.matoking.dungeoncrawler.state.Tile;
import com.matoking.dungeoncrawler.state.TileType;
import java.util.ArrayList;
import java.util.Random;

/**
 * Generates a random map
 *
 * @author matoking
 */
public class MapGenerator {
    // Default values
    private final static int DEFAULT_KEY_COUNT = 5;
    private final static int DEFAULT_ROOM_COUNT = 20;

    // Constants for map generation
    private final static int ROOM_MIN_WIDTH = 4;
    private final static int ROOM_MAX_WIDTH = 10;
    
    private final static int ROOM_MIN_HEIGHT = 4;
    private final static int ROOM_MAX_HEIGHT = 10;
    
    private GameState gameState;
    
    private int width;
    private int height;

    private int keysToSpawn;
    private int roomsToGenerate;
    
    private ArrayList<Room> rooms;
    
    private Random random;

    public MapGenerator(GameState gameState) {
        this.gameState = gameState;
        
        this.keysToSpawn = DEFAULT_KEY_COUNT;
        this.roomsToGenerate = DEFAULT_ROOM_COUNT;
        
        this.rooms = new ArrayList<Room>();
        
        this.random = new Random();
    }

    public void generateMap(int width, int height) {
        this.width = width;
        this.height = height;
        
        GameMap gameMap = this.gameState.getGameMap();
        gameMap.createMap(width, height);
        
        this.rooms.clear();
        
        this.generateRooms();
        this.loadMap();
    }
    
    /**
     * Generate specified amount of rooms
     * If specified amount of rooms can't be generated, 
     */
    public void generateRooms() {
        // When generating rooms, there is a chance that the area is filled
        // before the specified amount of rooms is generated
        // If we pass 10000 iterations without being able to fit a room,
        // stop trying to generate any more rooms
        int failedIterations = 0;
        
        for (int i=0; i < this.getRoomsToGenerate(); i++) {
            int x = 0;
            int y = 0;
            int width = 0;
            int height = 0;
            
            // Random.nextInt() starts complaining if the provided value isn't positive (instead of just returning),
            // which is why we have to use this unsightly exception handling mess
            try { x = this.random.nextInt(this.getWidth() - ROOM_MAX_WIDTH - 2); } catch (Exception e) { x = 1; }
            try { y = this.random.nextInt(this.getHeight() - ROOM_MAX_HEIGHT - 2); } catch (Exception e) { y = 1; }
            try { width = this.random.nextInt(ROOM_MAX_WIDTH - ROOM_MIN_WIDTH) + ROOM_MIN_WIDTH; } catch (Exception e) { width = ROOM_MIN_WIDTH; }
            try { height = this.random.nextInt(ROOM_MAX_HEIGHT - ROOM_MIN_HEIGHT) + ROOM_MIN_HEIGHT; }  catch (Exception e) { height = ROOM_MIN_HEIGHT; }
            
            Room room = new Room(x, y, width, height);
            
            // Choose random floor type
            int floor = random.nextInt(2);
            
            if (floor == 1) {
                room.setFloorType(TileType.WOODEN_FLOOR);
            } else {
                room.setFloorType(TileType.STONE_FLOOR);
            }
            
            if (this.roomIntersectsWithOtherRooms(room) || !this.isRoomWithinValidBounds(room)) {
                failedIterations++;
                
                // If we have passed 10000 iterations without being able to generate a room,
                // stop and use the existing rooms we already generated
                if (failedIterations > 10000) {
                    break;
                }
            } else {
                failedIterations = 0;
                this.rooms.add(room);
            }
        }
    }
    
    /**
     * Load the generated map in to the game
     */
    public void loadMap() {
        GameMap gameMap = this.gameState.getGameMap();
        
        // First pass: add the rooms
        for (Room room : this.rooms) {
            TileType floor = room.getFloorType();
            for (int x=room.getX(); x < room.getX() + room.getWidth(); x++) {
                for (int y=room.getY(); y < room.getY() + room.getHeight(); y++) {
                    gameMap.setTile(x, y, floor);
                }
            }
        }
        
        // Second pass: add the walls
        for (int x=0; x < gameMap.getWidth(); x++) {
            for (int y=0; y < gameMap.getHeight(); y++) {
                if (gameMap.getTileType(x, y) != TileType.EMPTY) {
                    continue;
                }
                
                boolean isFloor = false;
                
                for (Direction direction : Direction.values()) {
                    Coordinate coordinate = Coordinate.getNewCoordinates(direction, x, y);
                    
                    Tile tile = gameMap.getTile(coordinate);
                    
                    if (tile == null) {
                        continue;
                    } else {
                        if (tile.isFloor()) {
                            isFloor = true;
                            break;
                        }
                    }
                }
                
                if (isFloor) {
                    gameMap.setTile(x, y, TileType.WALL);
                }
            }
        }
        
        this.gameState.getPlayer().setRemainingKeys(this.getKeysToSpawn());
    }
    
    /**
     * Check if room intersects with any other rooms that have already been generated
     * 
     * @param room Room as a rectangle
     * @return True if room intersects with some of the rooms, false if not
     */
    private boolean roomIntersectsWithOtherRooms(Room room) {
        for (Room existingRoom : this.rooms) {
            if (existingRoom.intersects(room)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Check if the room is within valid bounds
     * 
     * @return 
     */
    private boolean isRoomWithinValidBounds(Room room) {
        if (room.getX() < 0 || (room.getX() + room.getWidth()) >= this.getWidth() ||
            room.getY() < 0 || (room.getY() + room.getHeight()) >= this.getHeight()) {
            return false;
        } else {
            return true;
        }
    }
    
    public int getKeysToSpawn() {
        return keysToSpawn;
    }

    public void setKeysToSpawn(int keysToSpawn) {
        this.keysToSpawn = keysToSpawn;
    }
    
    public int getRoomsToGenerate() {
        return roomsToGenerate;
    }

    public void setRoomsToGenerate(int roomsToGenerate) {
        this.roomsToGenerate = roomsToGenerate;
    }
    
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
    public ArrayList<Room> getRooms() {
        return rooms;
    }
}
