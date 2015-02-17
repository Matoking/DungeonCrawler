package com.matoking.dungeoncrawler.generator;

import com.matoking.dungeoncrawler.state.Coordinate;
import com.matoking.dungeoncrawler.state.Direction;
import com.matoking.dungeoncrawler.state.GameMap;
import com.matoking.dungeoncrawler.state.GameState;
import com.matoking.dungeoncrawler.state.TileType;
import java.util.ArrayDeque;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author matoking
 */
public class MapGeneratorTest {
    private GameState gameState;
    private MapGenerator mapGenerator;
    
    public MapGeneratorTest() {
    }
    
    @Before
    public void setUp() {
        this.gameState = new GameState();
        this.mapGenerator = new MapGenerator(this.gameState);
    }

    public void testMaximumRoomsGenerated() {
        // In a very large map the map generator should easily generate a
        // maximum of 20 rooms
        this.mapGenerator.generateMap(2000, 2000);
        
        for (int i=0; i < 1000; i++) {
            assertEquals(this.mapGenerator.getRooms().size(), this.mapGenerator.getRoomsToGenerate());
        }
    }
    
    public void testOnlyOneRoomGeneratedInTinyMap() {
        // In a small map the map generator can fit only one room
        this.mapGenerator.generateMap(7, 7);
        
        assertEquals(this.mapGenerator.getRooms().size(), 1);
    }
    
    public void testRoomsLoadedIntoGameMapContainCorrectAmountOfFloorTiles() {
        this.mapGenerator.generateMap(2000,2000);
        
        ArrayList<Room> rooms = this.mapGenerator.getRooms();
        
        // How many floor tiles there should be
        int floorTiles = 0;
        
        for (Room room : rooms) {
            floorTiles += room.getWidth() * room.getHeight();
        }
        
        int actualFloorTiles = 0;
        
        GameMap gameMap = this.gameState.getGameMap();
        
        for (int x=0; x < gameMap.getWidth(); x++) {
            for (int y=0; y < gameMap.getHeight(); y++) {
                TileType tileType = gameMap.getTileType(x, y);
                
                if (tileType == TileType.STONE_FLOOR || tileType == TileType.WOODEN_FLOOR) {
                    actualFloorTiles++;
                }
            }
        }
        
        assertEquals(floorTiles, actualFloorTiles);
    }
    
    @Test
    public void testMapGeneratedWithCorrectSize() {
        this.mapGenerator.generateMap(512, 1024);
        
        assertEquals(this.mapGenerator.getWidth(), 512);
        assertEquals(this.mapGenerator.getHeight(), 1024);
        
        assertEquals(this.gameState.getGameMap().getWidth(), 512);
        assertEquals(this.gameState.getGameMap().getHeight(), 1024);
    }
    
    /**
     * Tests that all entities generated on the map can be reached
     */
    @Test
    public void testMapEntitiesReachable() {
        for (int i=0; i < 10; i++) {
            this.mapGenerator.generateMap(48, 48);

            GameMap gameMap = this.gameState.getGameMap();

            ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
            ArrayDeque<Coordinate> queue = new ArrayDeque<Coordinate>();

            Coordinate first = this.mapGenerator.getRooms().get(0).getCenterCoordinate();

            queue.push(first);

            int skeletonCount = 0;
            int keyCount = 0;

            while (true) {
                if (queue.isEmpty()) {
                    break;
                }

                Coordinate coordinate = queue.pop(); 

                if (coordinates.contains(coordinate)) {
                    continue;
                }

                coordinates.add(coordinate);

                if (this.mapGenerator.getKeys().contains(coordinate)) {
                    keyCount++;
                }

                if (this.mapGenerator.getSkeletons().contains(coordinate)) {
                    skeletonCount++;
                }

                for (Direction direction : Direction.values()) {
                    Coordinate newCoordinate = Coordinate.getNewCoordinates(direction, coordinate.getX(),
                                                                                       coordinate.getY());

                    if (gameMap.getTileType(newCoordinate.getX(), newCoordinate.getY()) != TileType.WALL && !coordinates.contains(newCoordinate)) {
                        queue.push(newCoordinate);
                    }
                }
            }

            assertEquals(this.mapGenerator.getKeys().size(), keyCount);
            assertEquals(this.mapGenerator.getSkeletons().size(), skeletonCount);
        }
    }
    
    @Test
    public void testRandomOpenCoordinateIsAFloorTile() {
        this.mapGenerator.generateMap(64, 64);
        
        Coordinate coordinate = this.mapGenerator.getRandomOpenCoordinate();
        TileType tileType = this.gameState.getGameMap().getTileType(coordinate);
        
        assertEquals(tileType == TileType.STONE_FLOOR || tileType == TileType.WOODEN_FLOOR, true);
    }
    
}
