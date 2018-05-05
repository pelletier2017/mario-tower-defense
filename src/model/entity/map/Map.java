package model.entity.map;

import java.io.Serializable;

import model.entity.map.path.Path;
import model.entity.map.tiles.MapTile;
import model.entity.map.tiles.TileType;

public class Map implements Serializable {

    private MapTile tiles[][];

    // (row, col) of StartTile that will spawn enemies
    private int spawnRow;
    private int spawnCol;

    // path connecting StartTile to EndTile
    private Path path;

    public Map(String fileName) {
        this.tiles = MapLoader.readFromFile(fileName);

        int[] spawnPoint = MapLoader.getSpawnPoint(tiles);
        spawnRow = spawnPoint[0];
        spawnCol = spawnPoint[1];

        this.path = MapLoader.calculatePath(spawnRow, spawnCol, tiles);
    }

    // Finds x position of the center of a col
    private double centerXFromCol(int col) {
        return (col + 0.5) * (1.0 / tiles[0].length);
    }

    // Finds y position of the center of a row
    private double centerYFromRow(int row) {
        return (row + 0.5) * (1.0 / tiles.length);
    }

    // determines col of Tile[][] based on x (0-1.0)
    private int colFromX(double x) {
        return (int) (x * tiles.length);
    }

    // determines row of Tile[][] based on y (0-1.0)
    private int rowFromY(double y) {
        return (int) (y * tiles[0].length);
    }

    /**
     * Centers an X position to to be centered within a tile in the map. It
     * finds which tile it is in, then centers to the middle point of that tile.
     * 
     * @param x
     *            position between 0-1.0 that does not have to be centered
     * @return x Position between 0-1.0 that is the center of a tile
     */
    public double centerXFromX(double x) {
        int col = colFromX(x);
        return centerXFromCol(col);
    }

    /**
     * Centers a Y position to to be centered within a tile in the map. It
     * finds which tile it is in, then centers to the middle point of that tile.
     * 
     * @param y
     *            position between 0-1.0 that does not have to be centered
     * @return y Position between 0-1.0 that is the center of a tile
     */
    public double centerYFromY(double y) {
        int row = rowFromY(y);
        return centerYFromRow(row);
    }

    /**
     * Returns the background tiles of the map used for drawing.
     * 
     * @return MapTile[][] filled with Tiles representing regions of the map.
     */
    public MapTile[][] getTiles() {
        return tiles;
    }

    /**
     * Returns the Path linked list that goes from spawn point to end zone.
     * 
     * @return head of Path linked list
     */
    public Path getPath() {
        return path;
    }

    /**
     * Gets the centered X position of the spawn tile.
     * 
     * @return double between 0-1.0 representing X value in center of tile
     */
    public double getSpawnX() {
        return centerXFromCol(spawnCol);
    }

    /**
     * Gets the centered Y position of the spawn tile.
     * 
     * @return double between 0-1.0 representing Y value in center of tile
     */
    public double getSpawnY() {
        return centerYFromRow(spawnRow);
    }

    /**
     * Gets the MapTile at a given x, y coordinate. Keeping x and y between 0
     * and 1 allows us to resize canvas easily as they are a percent of the
     * canvas.
     * 
     * @param x
     *            Percent in x direction, number between 0-1
     * @param y
     *            Percent in y direction, number between 0-1
     * @return MapTile at row, col corresponding to x, y coordinates
     */
    private MapTile getTileFromPoint(double x, double y) {
        int row = rowFromY(y);
        int col = colFromX(x);
        return tiles[row][col];
    }
    
    /**
     * Returns true if the x, y position is within a Wall
     * @param x non-centered X position
     * @param y non-centered Y position
     * @return true if within a wall
     */
    public boolean isWall(double x, double y) {
        return getTileFromPoint(x, y).getTileType() == TileType.WALL;
    }
    
    /**
     * Returns string representation of the map that should look the same as the
     * map in a text file.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (MapTile[] row : tiles) {
            for (MapTile tile : row) {
                builder.append(tile.getTileType().getValue());
            }
            builder.append("\n");
        }
        return builder.toString();
    }

}