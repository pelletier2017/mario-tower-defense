package model.utils;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class contains a number of static methods used to help rotate vectors.
 * Temporarily it also includes some methods used to create a Lightning Tower.
 * 
 *
 * 
 * @author Andrew Pelletier
 *
 */
public class RotationUtils {

    private static Random r = new Random();
    
    /**
     * Rotate a {x, y} coordinate around a given {x, y} pivot by a given angle in radians.
     * @param point double[]{x, y} point to rotate
     * @param pivot double[]{x, y} axis of rotation
     * @param angle rotation angle in radians
     * @return {x, y} after rotation
     */
    public static Double[] rotateVector(Double[] point, Double[] pivot, double angle) {
        double x = point[0];
        double y = point[1];
        double originX = pivot[0];
        double originY = pivot[1];
        
        double newX = ((x - originX) * Math.cos(angle)) - ((y - originY) * Math.sin(angle)) + originX;
        double newY = ((y - originY) * Math.cos(angle)) + ((x - originX) * Math.sin(angle)) + originY;
        
        return new Double[] {newX, newY};
    }
    
    /**
     * Returns the angle formed between the two points from the x axis.
     * @param a {x, y} point A
     * @param b {x, y} point B
     * @return angle in radians
     */
    public static double angleBetweenPoints(Double[] a, Double[] b) {
        double x1 = a[0];
        double y1 = a[1];
        double x2 = b[0];
        double y2 = b[1];
        
        double xDist = x2 - x1;
        double yDist = y2 - y1;
        
        double addedAngle = 0;
        if (xDist > 0 && yDist >= 0) {
            // 1st quadrant
            addedAngle = 0;
        } else if (xDist <= 0 && yDist > 0) {
            // 2nd quadrant
            addedAngle = Math.PI / 2;
        } else if (xDist < 0 && yDist <= 0) {
            // 3rd quadrant
            addedAngle = Math.PI;
        } else if (xDist >= 0 && yDist < 0) {
            // 4th quadrant
            addedAngle = 3 * Math.PI / 2;
        }
        
        if (xDist == 0) {
            return addedAngle;
        }
        
        double absDistX = Math.abs(xDist);
        double absDistY = Math.abs(yDist);
        return Math.atan(absDistY / absDistX) + addedAngle;
    }
    
    public static double distance(Double[] a, Double[] b) {
        double x1 = a[0];
        double y1 = a[1];
        double x2 = b[0];
        double y2 = b[1];
        
        double xDist = Math.abs(x2 - x1);
        double yDist = Math.abs(y2 - y1);
        
        return Math.sqrt((Math.pow(xDist, 2) + Math.pow(yDist, 2)));
    }
    
    //TODO move methods creating lightning to the LightningTower
    public static ArrayList<Double[]> createNormalizedLightning(Double[] a, Double[] b) {
        double interval = 0.01;
        double breadth = 0.005;
        
        ArrayList<Double[]> points = new ArrayList<>();
        points.add(new Double[]{0.0, 0.0});
        
        double distance = distance(a, b);
        
        double x = 0;
        boolean up = true;
        while (x < distance) {
            double y;
            if (up) {
                y = r.nextDouble() * breadth;
            } else {
                y = r.nextDouble() * (-breadth);
            }
            x += interval;
            
            if (x < distance) {
                points.add(new Double[] {x, y});
            }
            up = !up;
        }
        points.add(new Double[] {distance, 0.0});
        return points;
    }
    
    public static ArrayList<Double[]> createLightning(Double[] a, Double[] b) {
        ArrayList<Double[]> points = createNormalizedLightning(a, b);
        double angle = angleBetweenPoints(a, b);
        
        for (Double[] point : points) {
            point[0] += a[0];
            point[1] += a[1];
            Double[] rotatedPoint = rotateVector(point, a, angle);
            point[0] = rotatedPoint[0];
            point[1] = rotatedPoint[1];
        }
        return points;
    }
    
}
