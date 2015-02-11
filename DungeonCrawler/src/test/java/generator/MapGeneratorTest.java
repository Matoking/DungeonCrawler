package generator;

import com.matoking.dungeoncrawler.generator.MapGenerator;
import com.matoking.dungeoncrawler.state.GameState;
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
        this.mapGenerator = new MapGenerator(new GameState());
    }
    
    @After
    public void tearDown() {
    }

    @Test(timeout=2000)
    public void testMaximumRoomsGenerated() {
        // In a very large map the map generator should easily generate a
        // maximum of 20 rooms
        this.mapGenerator.generateMap(2000, 2000);
        
        for (int i=0; i < 1000; i++) {
            assertEquals(this.mapGenerator.getRooms().size(), this.mapGenerator.getRoomsToGenerate());
        }
    }
    
    @Test(timeout=2000)
    public void testOnlyOneRoomGeneratedInTinyMap() {
        // In a small map the map generator can fit only one room
        this.mapGenerator.generateMap(7, 7);
        
        assertEquals(this.mapGenerator.getRooms().size(), 1);
    }
    
}
