package model.entity.map;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import model.entity.map.path.BranchingPath;
import model.entity.map.path.Path;
import model.entity.map.tiles.BranchingTile;
import model.entity.map.tiles.EndTile;
import model.entity.map.tiles.JoiningTile;
import model.entity.map.tiles.MapTile;
import model.entity.map.tiles.PathTile;
import model.entity.map.tiles.StartTile;
import model.entity.map.tiles.TileType;
import model.entity.map.tiles.WallTile;

/**
 * This static class is used for loading a Map from a file and finding its path
 * based on that Tile[][].
 * 
 * @author Andrew Pelletier
 *
 */
public class MapLoader {

    private MapLoader() {
        // private constructor for static class. There won't be a need to
        // construct a MapLoader object.
    }

    public static final String PATH_TO_MAPS = "src/resources/maps/";

    /**
     * Converts a txt file into a Tile[][].
     * 
     * @param fileName
     *            Name of file found in maps folder
     * @return Tile[][] filled with correct tiles
     */
    public static MapTile[][] readFromFile(String fileName) {

        String filePath = PATH_TO_MAPS + fileName;

        // valid characters from file
        HashMap<Character, String> tileMap = new HashMap<>();
        tileMap.put('S', "start");
        tileMap.put('E', "end");
        tileMap.put('.', "wall");
        tileMap.put('1', "path");
        tileMap.put('B', "branch");
        tileMap.put('J', "join");

        // read file
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // checks file and throws exceptions if it is invalid
        checkValidMapFile(lines, tileMap.keySet());

        int rows = lines.size();
        int cols = lines.get(0).length();
        MapTile[][] newTiles = new MapTile[rows][cols];

        for (int i = 0; i < rows; i++) {
            newTiles[i] = new MapTile[cols];
        }

        // build tiles, setup start and end zones
        for (int row = 0; row < lines.size(); row++) {
            for (int col = 0; col < lines.get(row).length(); col++) {
                char c = lines.get(row).charAt(col);
                String type = tileMap.get(c);

                if (type.equals("start")) {
                    StartTile startZone = new StartTile();
                    newTiles[row][col] = startZone;

                } else if (type.equals("end")) {
                    EndTile endZone = new EndTile();
                    newTiles[row][col] = endZone;

                } else if (type.equals("wall")) {
                    newTiles[row][col] = new WallTile();

                } else if (type.equals("branch")) {
                    newTiles[row][col] = new BranchingTile();

                } else if (type.equals("join")) {
                    newTiles[row][col] = new JoiningTile();

                } else {
                    newTiles[row][col] = new PathTile();
                }
            }
        }

        return newTiles;

    }

    /**
     * Throws an IllegalStateException if this map file is invalid in any way.
     * 
     * @param filePath
     *            Relative path to the map file
     * @param charSet
     *            Set of characters that can be used in the map
     */
    private static void checkValidMapFile(List<String> lines,
            Set<Character> charSet) {
        
        // empty file
        if (lines.size() == 0) {
            throw new IllegalArgumentException("File is empty");
        }

        // rows don't match
        int rowLength = lines.get(0).length();
        for (String line : lines) {
            if (line.length() != rowLength) {
                throw new IllegalArgumentException("Rows are not same length");
            }
        }

        // character doesn't match
        for (int row = 0; row < lines.size(); row++) {
            for (int col = 0; col < lines.get(row).length(); col++) {

                char c = lines.get(row).charAt(col);
                if (!charSet.contains(c)) {
                    throw new IllegalArgumentException(String.format(
                            "Invalid character at index row: %d, col: %d", row,
                            col));
                }
            }
        }

    }

    /**
     * Finds the first starting zone in a tile[][] and returns an int[] of the
     * row and column.
     * 
     * @param tiles
     *            MapTile[][] representing the map. Size of array doesnt matter.
     * @return int[] {row, col}
     */
    public static int[] getSpawnPoint(MapTile[][] tiles) {
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[row].length; col++) {
                if (tiles[row][col].getTileType() == TileType.START) {
                    return new int[] { row, col };
                }
            }
        }
        throw new IllegalArgumentException("Map has no start zone");
    }

    /**
     * Creates a linked list of Path and BranchingPath that connects the start
     * zone to the end zone. If there are more than one path from a point, it
     * will use BranchingPath.
     * 
     * @return Beginning of Path linked list
     */
    public static Path calculatePath(int spawnRow, int spawnCol,
            MapTile[][] tiles) {

        // initialize seen array to track paths already covered
        Path[][] seenPaths = new Path[tiles.length][tiles[0].length];
        for (int i = 0; i < seenPaths.length; i++) {
            seenPaths[i] = new Path[tiles[0].length];
        }

        return calculatePath(spawnRow, spawnCol, -1, -1, seenPaths, tiles);
    }

    private static Path calculatePath(int row, int col, int prevRow,
            int prevCol, Path[][] seenPaths, MapTile[][] tiles) {

        if (!withinBounds(row, col, tiles)
                || tiles[row][col].getTileType() == TileType.WALL) {
            return null;
        }

        TileType type = tiles[row][col].getTileType();

        // dont add paths already seen before
        if (seenPaths[row][col] != null && type != TileType.JOIN) {
            return null;
        }

        double[] centerOfSquare = centerPoint(row, col, tiles);
        double x = centerOfSquare[0];
        double y = centerOfSquare[1];

        Path path = null;
        if (type == TileType.BRANCH) {
            path = new BranchingPath(x, y);
        } else {
            path = new Path(x, y);
        }

        // first time visiting a Join Tile return null and store Path node
        if (type == TileType.JOIN) {
            if (seenPaths[row][col] == null) {
                seenPaths[row][col] = path;
                return path;
            } else {
                path = seenPaths[row][col];
            }
        }
        seenPaths[row][col] = path;

        // iterate over possible directions to move
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {

                // ignore checking itself
                if (i == 0 && j == 0) {
                    continue;
                }

                // ignore diagonals
                if (i != 0 & j != 0) {
                    continue;
                }

                int newRow = row + i;
                int newCol = col + j;

                // ignore where you came from to prevent circular list
                if (newRow == prevRow && newCol == prevCol) {
                    continue;
                }

                Path possiblePath = calculatePath(newRow, newCol, row, col,
                        seenPaths, tiles);
                if (possiblePath != null) {
                    path.addNext(possiblePath);
                }
            }
        }

        return path;
    }

    /**
     * Determines if a row and column are within the bounds of the MapTile[][].
     * 
     * @param row
     *            row to check
     * @param col
     *            column to check
     * @return true if the row is valid and column is valid
     */
    private static boolean withinBounds(int row, int col, MapTile[][] tiles) {
        boolean rowValid = row >= 0 && row < tiles.length;
        boolean colValid = col >= 0 && col < tiles[0].length;
        return rowValid && colValid;
    }

    /**
     * Returns the center x, y point at a given row, col according to the number
     * of rows and number of columns in the tileset.
     * 
     * @param row
     *            int row in 2d array
     * @param col
     *            int col in 2d array
     * @param numRows
     *            number of rows
     * @param numCols
     *            number of columns
     * @return [x, y] representing the center of a box.
     */
    private static double[] centerPoint(int row, int col, MapTile[][] tiles) {
        double x = (col + 0.5) * (1.0 / tiles[0].length);
        double y = (row + 0.5) * (1.0 / tiles.length);
        return new double[] { x, y };
    }

}
