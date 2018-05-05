package test.sprite;

import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import javafx.embed.swing.JFXPanel;
import model.sprite.SpriteSheetLoader;
import model.sprite.SpriteSheet;

class SpriteLoaderTest {
    
    // TODO update this test once sprite loader is up to date
    
    
    @Test
    void testGetImage() {
        
        // this is needed to setup toolkit for javafx to allow Images to be created
        new JFXPanel();
        SpriteSheet sprite = SpriteSheetLoader.getSheet("map", 64, 64, 1, new int[] {4});
        assertNotEquals(sprite, null);
        
        
    }

}
