package test.sprite;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;
import model.entity.map.tiles.TileType;
import model.entity.tower.TowerType;
import model.entity.tower.projectiles.ProjectileType;
import model.sprite.ImageLoader;

class ImageLoaderTest {

    @Test
    void test() {
        new JFXPanel();
        
        ImageLoader loader = new ImageLoader();
//        Image tower = loader.getImage(TowerType.ARROW_TOWER);
//        Image projectile = loader.getImage(ProjectileType.ARROW);
        Image path = loader.getImage(TileType.PATH);
        
//        // repeated a second time, images are the same instance
//        assertEquals(tower, loader.getImage(TowerType.ARROW_TOWER));
//        assertEquals(projectile, loader.getImage(ProjectileType.ARROW));
        assertEquals(path, loader.getImage(TileType.PATH));
        
    }

}
