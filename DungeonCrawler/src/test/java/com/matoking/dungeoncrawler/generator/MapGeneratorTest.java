package com.matoking.dungeoncrawler.generator;

import com.matoking.dungeoncrawler.generator.MapGenerator;
import com.matoking.dungeoncrawler.generator.Room;
import com.matoking.dungeoncrawler.state.GameMap;
import com.matoking.dungeoncrawler.state.GameState;
import com.matoking.dungeoncrawler.state.TileType;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.gameState = new GameState();
        this.mapGenerator = new MapGenerator(this.gameState);
    }
    
    @After
    public void tearDown() {
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
    
}
