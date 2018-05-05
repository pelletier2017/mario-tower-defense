package model.entity.map.path;

import java.io.Serializable;

/**
 * This path is a linked list connecting the Enemy from the Spawn Zone to the End Zone.
 * If getNext returns null, this is the end of the Path linked list.
 * 
 * @author Andrew Pelletier
 *
 */
public class Path implements Serializable {

    private double x;
    private double y;
    protected Path nextPath;
    
    protected int distanceFromGoal;
    
    public Path(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Gets the x position of the Path
     * @return double x within the Map
     */
    public double getX() {
        return x;
    }
    
    /**
     * Gets the y position of the Path
     * @return double y within the Map
     */
    public double getY() {
        return y;
    }
    
    /**
     * Gets the pointer to the next Path.
     * @return A new Path
     */
    public Path getNext() {
        return nextPath;
    }
    
    /**
     * Sets the nextPath to  a new Path
     * @param path Path to change it to
     */
    public void addNext(Path path) {
        this.nextPath = path;
    }
    
    @Override
    public String toString() {
        
        String nextString = null;
        if (nextPath == null) {
            nextString = "null";
        } else {
            nextString = nextPath.toString();
        }
        return String.format("(%.3f, %.3f)->", x, y) + nextString;
    }
    
    // returns the number of path elements until the goal
    //    the 'goal' is considered the end of the path and therefore a null pointer.
    public int getDistanceFromGoal() {
    	
    	if( distanceFromGoal > 0) {
    		return distanceFromGoal;
    	}
    	
    	if( nextPath == null) {
    		distanceFromGoal = 1;
    		return 1 ;
    	}
    	
    	distanceFromGoal = getNextPathDistance() +1;
    	
    	return distanceFromGoal;
    }

    // returns the distance of the next path
	public int getNextPathDistance() {
		return nextPath.getDistanceFromGoal();
	}
}
