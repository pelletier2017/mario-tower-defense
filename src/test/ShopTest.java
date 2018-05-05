package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import model.Shop;
import model.entity.tower.Tower;

class ShopTest {

    @Test
    void testTowers() {
        List<Tower> towers = Shop.getTowers();
        Tower tower = Shop.getTower(0);
    }

}
