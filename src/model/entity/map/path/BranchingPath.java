package model.entity.map.path;

import java.util.ArrayList;

/**
 * This is a Path that has two options. Currently it will alternate between each path for each time getNextPath() is called.
 * 
 * @author Andrew Pelletier
 *
 */
public class BranchingPath extends Path {

    ArrayList<Path> paths = new ArrayList<>();
    private int nextIndex = 0;
    
    public BranchingPath(double x, double y) {
        super(x, y);
    }
    
    /**
     * Alternates between path. Since multiple enemies have a pointer to this path, 
     * it will alternate when they get the path.
     */
    @Override
    public Path getNext() {
        
        if (paths.size() == 0) {
            return null;
        }
        
        Path choice = paths.get(nextIndex);
        nextIndex = (nextIndex + 1) % paths.size();
        return choice;
    }
    
    /**
     * Adds a path to the list of possible paths.
     */
    @Override
    public void addNext(Path path) {
        paths.add(path);
    }
    
    // TODO having trouble thinking of a nice toString for paths that branch and rejoin
    @Override
    public String toString() {
        String nextString = null;
        if (paths.size() == 0) {
            nextString = "null";
        } else {
            nextString = "[";
            for (Path path : paths) {
                nextString += path.toString();
            }
            nextString += "]";
        }
        return String.format("(%.3f, %.3f)->", getX(), getY()) + nextString;
    }
    

    // returns the longest distance from all the possible paths
    @Override
 	public int getNextPathDistance() {
    	int longestDistance = 0;
    	for( Path curPath: paths) {
    		if( curPath.getDistanceFromGoal() >  longestDistance) {
    			longestDistance = curPath.getDistanceFromGoal();
    		}
    	}
    	
 		return longestDistance;
 	}
    
}
