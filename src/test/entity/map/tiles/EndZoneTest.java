package test.entity.map.tiles;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.entity.map.tiles.EndTile;
import model.entity.map.tiles.TileType;


class EndZoneTest {
    
    @Test
    void testToString() {
        EndTile endZone = new EndTile();
        assertEquals('E', endZone.getTileType().getValue());
        assertEquals(TileType.END, endZone.getTileType());
    }

}
