package test.entity.map.tiles;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.entity.map.tiles.TileType;
import model.entity.map.tiles.WallTile;


class WallTest {
    
    @Test
    void testToString() {
        WallTile wall = new WallTile();
        assertEquals('.', wall.getTileType().getValue());
        assertEquals(TileType.WALL, wall.getTileType());
        
        wall.getTileType().getFileName();
        wall.getTileType().getValue();
    }

}
