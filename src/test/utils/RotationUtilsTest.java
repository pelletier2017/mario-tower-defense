package test.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import model.utils.RotationUtils;

class RotationUtilsTest {

    @Test
    void testDistance() {
        Double[] a = new Double[] {0.0, 0.0};
        Double[] b = new Double[] {0.5, 0.5};
        //System.out.println(RotationUtils.distance(a, b));
    }
    
    @Test
    void testCreateLightning() {
        // TODO just a visual test to see if it works in general, replace with actual tests later
        Double[] a = new Double[] {0.25, 0.25};
        Double[] b = new Double[] {0.75, 0.75};
        
        ArrayList<Double[]> lightningPoints = RotationUtils.createLightning(a, b);
        for (Double[] point : lightningPoints) {
            System.out.println(Arrays.toString(point));
        }
    }
    
    @Test
    void testRotateVector() {
        Double[] a = new Double[] {0.2, 0.2};
        Double[] pivot = new Double[] {0.1, 0.1};
        
        Double[] result = RotationUtils.rotateVector(a, pivot,  Math.PI/2);
    }
    
    @Test
    void testAngleBetweenPoints() {
        Double[] a = new Double[] {0.1, 0.1};
        Double[] b = new Double[] {0.2, 0.1};
        
        double result = RotationUtils.angleBetweenPoints(a, b);
        
        a = new Double[] {0.2, 0.1};
        b = new Double[] {0.1, 0.1};
        
        result = RotationUtils.angleBetweenPoints(a, b);
        
        a = new Double[] {0.1, 0.2};
        b = new Double[] {0.1, 0.1};
        
        result = RotationUtils.angleBetweenPoints(a, b);
        
        a = new Double[] {0.1, 0.1};
        b = new Double[] {0.1, 0.2};
        
        result = RotationUtils.angleBetweenPoints(a, b);
    }
    
}
