package test.entity.map.tiles;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.entity.map.tiles.StartTile;
import model.entity.map.tiles.TileType;


class StartZoneTest {
    
    @Test
    void testToString() {
        StartTile startZone = new StartTile();
        assertEquals('S', startZone.getTileType().getValue());
        assertEquals(TileType.START, startZone.getTileType());
    }

}
