package test.entity.map;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import model.entity.map.Map;
import model.entity.map.MapLoader;
import model.entity.map.path.Path;
import model.entity.map.tiles.MapTile;
import model.entity.map.tiles.TileType;


class MapTest {

    private static final String TEST_MAP_FILE_1 = "map1.txt";
    private static final String TEST_MAP_FILE_2 = "map2.txt";
    
    private static final Map map1 = new Map(TEST_MAP_FILE_1);
    private static final Map map2 = new Map(TEST_MAP_FILE_1);
    
    @Test
    void testFileReading() {
        
        // tests the toString matches file
        map1.toString();
        
        String fileData = null;
        try {
            fileData = new String(Files.readAllBytes(Paths.get(MapLoader.PATH_TO_MAPS, TEST_MAP_FILE_1)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // normalize invisible line endings from dif operating systems
        fileData = fileData.replaceAll("\\r\\n", "\n");
        fileData = fileData.replaceAll("\\r", "\n");
        
        assertEquals(fileData, map1.toString());
    }
    
    @Test
    void testGetTilesNotNull() {
        for (MapTile[] row : map1.getTiles()) {
            for (MapTile tile : row) {
                assertNotEquals(tile, null);
            }
        }
    }
    
    @Test
    void testCenterXandY() {
        assertEquals(20, map2.getTiles().length);
        double x = 0.0;
        double y = 0.55;
        assertEquals(0.025, map2.centerXFromX(0.0), 0.000001);
        assertEquals(0.575, map2.centerYFromY(y), 0.000001);
    }
    
    @Test
    void testSpawn() {
        assertEquals(0.275, map1.getSpawnX(), 0.000001);
        assertEquals(0.275, map1.getSpawnY(), 0.000001);
        assertTrue(map1.getPath() != null);
        
        assertTrue(map1.isWall(0, 0));
        assertFalse(map1.isWall(0.55, 0.55));
    }
    
}
