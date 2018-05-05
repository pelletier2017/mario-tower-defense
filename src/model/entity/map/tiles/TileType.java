package model.entity.map.tiles;

/**
 * This Enum is used to determine the Type of a Map tile. Used when reading a
 * Map from txt file and when determining the type of a Tile.
 * 
 * @author Andrew Pelletier
 *
 */
public enum TileType {

    // impossible to get code coverage for the rest of this file
    
    START('S', "start.png"),
    END('E', "end.png"),
    WALL('.', "wall.png"),
    PATH('1', "path.png"),
    BRANCH('B', "branch.png"),
    JOIN('J', "join.png");

    private char type;
    private String fileName;

    TileType(char type, String fileName) {
        this.type = type;
        this.fileName = fileName;
    }

    public char getValue() {
        return type;
    }
    
    public String getFileName() {
        return fileName;
    }
}
