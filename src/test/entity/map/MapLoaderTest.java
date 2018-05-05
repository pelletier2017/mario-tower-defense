package test.entity.map;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.entity.map.Map;

class MapLoaderTest {

    @Test
    public void testMapCreation() {
        Map map = new Map("map3.txt");
    }
    
    @Test
    public void testInvalidMaps() {
        String path = "testing_maps/";
        
        try {
            Map map1 = new Map(path + "empty_map.txt");
            fail("");
        } catch (IllegalArgumentException e) {
        }
        
        try {
            Map map2 = new Map(path + "illegal_char.txt");
            fail("");
        } catch (IllegalArgumentException e) {
        }
        
        try {
            Map map3 = new Map(path + "invalid_rows.txt");
            fail("");
        } catch (IllegalArgumentException e) {
        }
        
        try {
            Map map4 = new Map(path + "no_starting_zone.txt");
            fail("");
        } catch (IllegalArgumentException e) {
        }
        
    }
    
}
