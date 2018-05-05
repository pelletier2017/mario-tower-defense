package test.entity.map.path;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.entity.map.path.BranchingPath;
import model.entity.map.path.Path;

class BranchingPathTest {

    @Test
    void testGetters() {
        BranchingPath path = new BranchingPath(17.1, 25.5);
        assertEquals(17.1, path.getX(), 0.001);
        assertEquals(25.5, path.getY(), 0.001);
        assertEquals(null, path.getNext());
    }
    
    @Test
    void testAlernating() {
        Path branchingPath = new BranchingPath(10, 80);
        
        Path a = new Path(3, 19);
        Path b = new Path(27, 60);
        
        branchingPath.addNext(a);
        branchingPath.addNext(b);
        
        assertEquals(3, branchingPath.getNext().getX(), 0.001);
        // path has alternated to other
        assertEquals(27, branchingPath.getNext().getX(), 0.001);
        // path alternated back
        assertEquals(3.0, branchingPath.getNext().getX(), 0.001);
        // it alternated again so branch changes
        assertNotEquals(3.0, branchingPath.getNext().getX());
        assertNotEquals(null, branchingPath.toString());
        
        BranchingPath path2 = new BranchingPath(5, 10);
        assertNotEquals(null, path2.toString());
    }

}
