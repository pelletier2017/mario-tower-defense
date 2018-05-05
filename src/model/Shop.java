package model;

import java.util.Arrays;
import java.util.List;

import model.entity.tower.ArrowTower;
import model.entity.tower.BombTower;
import model.entity.tower.BounceTower;
import model.entity.tower.IceTower;
import model.entity.tower.Tower;

/**
 * Static class used to keep track of towers listed in the Shop.
 * 
 * @author Andrew Pelletier
 *
 */
public class Shop {

    private static List<Tower> towers = Arrays.asList(new ArrowTower(0, 0, false), new BombTower(0, 0, false), new IceTower(0, 0, false),
            new BounceTower(0, 0, false));

    /**
     * Returns the list of towers available in the shop.
     * 
     * @return immutable list of towers
     */
    public static List<Tower> getTowers() {
        return towers;
    }
    
    /**
     * Gets the i'th tower from the Shop's list of towers. This is only for display purposes.
     * @param i index of tower list
     * @return Tower located at (0,0) used only to show its details.
     */
    public static Tower getTower(int i) {
        return towers.get(i);
    }

}
