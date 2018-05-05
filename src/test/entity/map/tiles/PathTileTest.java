package test.entity.map.tiles;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.entity.map.tiles.PathTile;
import model.entity.map.tiles.TileType;


class PathTileTest {
    
    @Test
    void testToString() {
        PathTile path = new PathTile();
        assertEquals('1', path.getTileType().getValue());
        assertEquals(TileType.PATH, path.getTileType());
    }

}
