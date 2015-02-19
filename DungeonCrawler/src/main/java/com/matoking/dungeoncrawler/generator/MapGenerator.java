package com.matoking.dungeoncrawler.generator;

import com.matoking.dungeoncrawler.state.Coordinate;
import com.matoking.dungeoncrawler.state.Direction;
import com.matoking.dungeoncrawler.state.GameMap;
import com.matoking.dungeoncrawler.state.GameState;
import com.matoking.dungeoncrawler.state.Tile;
import com.matoking.dungeoncrawler.state.TileType;
import com.matoking.dungeoncrawler.state.entities.Key;
import com.matoking.dungeoncrawler.state.entities.Skeleton;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

/**
 * Generates a random map
 *
 * @author matoking
 */
public class MapGenerator {
    // Default values
    private final static int DEFAULT_KEY_COUNT = 5;
    private final static int DEFAULT_SKELETON_COUNT = 5;
    
    private final static int DEFAULT_ROOM_COUNT = 20;

    // Constants for map generation 
    private final static int ROOM_MIN_WIDTH = 6;
    private final static int ROOM_MAX_WIDTH = 12;
    
    private final static int ROOM_MIN_HEIGHT = 6;
    private final static int ROOM_MAX_HEIGHT = 12;
    
    private GameState gameState;
    
    private int width;
    private int height;

    private int skeletonsToSpawn;
    private int keysToSpawn;
    private int roomsToGenerate;
    
    private ArrayList<Room> rooms;
    private ArrayList<Integer>[] roomConnections;
    
    private ArrayList<Corridor> corridors;
    
    private ArrayList<Coordinate> skeletons;
    private ArrayList<Coordinate> keys;
    
    private Random random;

    public MapGenerator(GameState gameState) {
        this.gameState = gameState;
        
        this.skeletonsToSpawn = DEFAULT_SKELETON_COUNT;
        this.keysToSpawn = DEFAULT_KEY_COUNT;
        this.roomsToGenerate = DEFAULT_ROOM_COUNT;
        
        this.rooms = new ArrayList<Room>();
        this.corridors = new ArrayList<Corridor>();
        
        this.skeletons = new ArrayList<Coordinate>();
        this.keys = new ArrayList<Coordinate>();
        
        this.random = new Random();
    }

    /**
     * Generate and load a map into the game with the provided width and height
     * 
     * @param width Width of the game map
     * @param height Height of the game map
     */
    public void generateMap(int width, int height) {
        this.width = width;
        this.height = height;
        
        GameMap gameMap = this.gameState.getGameMap();
        gameMap.createMap(width, height);
        
        this.keys.clear();
        this.skeletons.clear();
        
        this.rooms.clear();
        this.corridors.clear();
        
        this.generateRooms();
        this.generateRoomConnections();
        this.generateCorridors();
        
        this.addSkeletons();
        this.addKeys();
        
        this.loadMap();
        this.placePlayer();
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
     * Generate connections between rooms, making sure all rooms become reachable
     */
    public void generateRoomConnections() {
        int roomCount = this.getRooms().size();
        
        while (true) {
            this.roomConnections = new ArrayList[roomCount];
            
            for (int i=0; i < roomCount; i++) {
                this.roomConnections[i] = new ArrayList<Integer>();
            }
            
            // The amount of connections between rooms is about 1.5 * roomCount
            int totalConnections = (int)((float)roomCount * 1.5);

            // Generate room connections at random
            int connectionsCreated = 0;
            while (connectionsCreated < totalConnections) {
                this.roomConnections[this.random.nextInt(roomCount)].add(this.random.nextInt(roomCount));
                connectionsCreated++;
            }
            
            // Check if room connections connect all rooms
            // If not, just regenerate
            if (isEntireMapConnected()) {
                break;
            }
        }
    }
    
    /**
     * Generate the corridors connecting the rooms based on the existing room connections
     */
    public void generateCorridors() {
        int roomCount = this.getRooms().size();
        
        ArrayList<Integer>[] roomConnections = this.getRoomConnections();
        
        for (int room=0; room < roomCount; room++) {
            for (int neighbor : roomConnections[room]) {
                Room roomA = this.getRooms().get(room);
                Room roomB = this.getRooms().get(neighbor);
                
                Coordinate coordinateA = roomA.getCenterCoordinate();
                Coordinate coordinateB = roomB.getCenterCoordinate();
                
                int randInt = this.random.nextInt(2);
                
                // Coordinate to connect the two different rooms
                Coordinate middleCoordinate;
                if (randInt == 0) {
                    middleCoordinate = new Coordinate(coordinateA.getX(), coordinateB.getY());
                } else {
                    middleCoordinate = new Coordinate(coordinateB.getX(), coordinateA.getY());
                }
                
                // Select the floor type for the connecting corridors
                randInt = this.random.nextInt(2);
                
                TileType floorType;
                if (randInt == 0) {
                    floorType = TileType.STONE_CORRIDOR_FLOOR;
                } else {
                    floorType = TileType.WOODEN_CORRIDOR_FLOOR;
                }
                
                // Add the two corridors that connect the rooms together
                this.corridors.add(new Corridor(coordinateA, middleCoordinate, floorType));
                this.corridors.add(new Corridor(coordinateB, middleCoordinate, floorType));
            }
        }
    }
    
    /**
     * Are all of the rooms in the map reachable. Used by generateRoomConnections() to ensure
     * player can reach all rooms
     * 
     * @return true if all rooms can be reached, false if not
     */
    public boolean isEntireMapConnected() {
        int roomCount = this.getRooms().size();
        
        HashSet<Integer> reachable = new HashSet<Integer>();
        
        // Start with room number 0
        reachable.add(0);
        
        while (true) {
            int newConnections = 0;
            
            for (int i=0; i < roomCount; i++) {
                ArrayList<Integer> connections = this.roomConnections[i];
                
                for (int neighbor : connections) {
                    if (reachable.contains(i) && !reachable.contains(neighbor)) {
                        reachable.add(neighbor);
                        newConnections++;
                    }
                }
            }
            
            // When we have found all possible connections, stop and return the result
            if (newConnections == 0) {
                return reachable.size() == roomCount;
            }
        }
    }
    
    /**
     * Add skeleton enemies to random rooms
     */
    public void addSkeletons() {
        for (int i=0; i < this.getSkeletonsToSpawn(); i++) {
            while (true) {
                Coordinate coordinate = this.getRandomOpenCoordinate();
                
                if (!this.getSkeletons().contains(coordinate)) {
                    this.getSkeletons().add(coordinate);
                    break;
                }
            }
        }
    }
    
    /**
     * Add keys for player to collect into rooms
     */
    public void addKeys() {
        for (int i=0; i < this.getKeysToSpawn(); i++) {
            while (true) {
                Coordinate coordinate = this.getRandomOpenCoordinate();

                if (!this.getKeys().contains(coordinate) && !this.getSkeletons().contains(coordinate)) {
                    this.getKeys().add(coordinate);
                    break;
                }
            }
        }
    }
    
    /**
     * Load the generated map in to the game
     */
    public void loadMap() {
        this.loadRooms();
        this.loadCorridors();
        this.loadWalls();
        this.loadSkeletons();
        this.loadKeys();
    }
    
    /**
     * Load generated rooms into the game
     */
    public void loadRooms() {
        GameMap gameMap = this.gameState.getGameMap();
        
        // First pass: add the rooms
        for (Room room : this.getRooms()) {
            TileType floor = room.getFloorType();
            for (int x=room.getX(); x < room.getX() + room.getWidth(); x++) {
                for (int y=room.getY(); y < room.getY() + room.getHeight(); y++) {
                    gameMap.setTile(x, y, floor);
                }
            }
        }
    }
    
    /**
     * Load generated corridors into the game
     */
    public void loadCorridors() {
        GameMap gameMap = this.gameState.getGameMap();
        
        for (Corridor corridor : this.getCorridors()) {
            for (int x=corridor.getX(); x < corridor.getX() + corridor.getWidth(); x++) {
                for (int y=corridor.getY(); y < corridor.getY() + corridor.getHeight(); y++) {
                    if (gameMap.getTileType(x, y) == TileType.EMPTY) {
                        gameMap.setTile(x, y, corridor.getFloorType());
                    }
                }
            }
        }
    }
    
    /**
     * Add walls to the loaded map layout
     */
    public void loadWalls() {
        GameMap gameMap = this.gameState.getGameMap();
        
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
    }
    
    /**
     * Add generated skeletons into the game
     */
    public void loadSkeletons() {
        GameMap gameMap = this.gameState.getGameMap();
        
        for (Coordinate skeleton : this.getSkeletons()) {
            gameMap.addEntity(new Skeleton(this.gameState, skeleton.getX(), skeleton.getY()));
        }
    }
    
    /**
     * Add generated keys into the game
     */
    public void loadKeys() {
        GameMap gameMap = this.gameState.getGameMap();
        
        for (Coordinate key : this.getKeys()) {
            gameMap.addEntity(new Key(this.gameState, key.getX(), key.getY()));
        }
    }
    
    /**
     * Place the player inside one of the rooms
     */
    public void placePlayer() {
        this.gameState.getPlayer().setRemainingKeys(this.getKeysToSpawn());
        
        while (true) {
            Coordinate coordinate = this.getRandomOpenCoordinate();

            if (!this.getKeys().contains(coordinate) && !this.getSkeletons().contains(coordinate)) {
                this.gameState.getPlayer().setCoordinate(coordinate);
                break;
            }
        }
    }
    
    /**
     * Get random coordinate that's inside one of the rooms and isn't occupied
     * by any other entity
     * 
     * @return Return random Coordinate that's inside one of the rooms
     */
    public Coordinate getRandomOpenCoordinate() {
        while (true) {
            // Pick a random room
            Room room = this.getRooms().get(this.random.nextInt(this.getRooms().size()));

            // Pick a random coordinate inside the room
            int x = this.random.nextInt(room.getWidth()) + room.getX();
            int y = this.random.nextInt(room.getHeight()) + room.getY();

            Coordinate coordinate = new Coordinate(x, y);

            if (!this.getKeys().contains(coordinate) && !this.getSkeletons().contains(coordinate)) {
                return new Coordinate(x, y);
            }
        }
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
     * @return true if room is within bounds, false if it isn't
     */
    private boolean isRoomWithinValidBounds(Room room) {
        if (room.getX() < 1 || (room.getX() + room.getWidth()) >= this.getWidth()-1 ||
            room.getY() < 1 || (room.getY() + room.getHeight()) >= this.getHeight()-1) {
            return false;
        } else {
            return true;
        }
    }

    public int getSkeletonsToSpawn() {
        return skeletonsToSpawn;
    }

    public void setSkeletonsToSpawn(int skeletonsToSpawn) {
        this.skeletonsToSpawn = skeletonsToSpawn;
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

    public ArrayList<Corridor> getCorridors() {
        return corridors;
    }

    public ArrayList<Integer>[] getRoomConnections() {
        return roomConnections;
    }

    public ArrayList<Coordinate> getSkeletons() {
        return skeletons;
    }

    public ArrayList<Coordinate> getKeys() {
        return keys;
    }
}
