package test.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URL;

import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import model.utils.ImageUtils;

class ImageUtilsTest {

    @Test
    void testTint() {
        new JFXPanel();
        
        // cannot run this on the main thread because it uses Image snapshot which must be run in a javafx thread.
        // Platform.runLater() does not seem to trigger code coverage either
        
        Platform.runLater( () -> {
            Image img = new Image("resources/images/clouds.jpg");
            Image red = ImageUtils.addTint(img, 100, 0, 0);
            
            PixelReader imgReader = img.getPixelReader();
            PixelReader redReader = red.getPixelReader();
            
            Color originalColor = imgReader.getColor(20, 20);
            Color tintedColor = redReader.getColor(20, 20);
            assertTrue(originalColor.getRed() < tintedColor.getRed());
        });
    }
    
    @Test
    void testOverTint() {
        new JFXPanel();
        
        // cannot run this on the main thread because it uses Image snapshot which must be run in a javafx thread.
        // Platform.runLater() does not seem to trigger code coverage either
        
        Platform.runLater( () -> {
            Image img = new Image("resources/images/clouds.jpg");
            Image black = ImageUtils.addTint(img, 256, 256, 256);
        });
    }
    
    @Test
    void testOpacity() {
        new JFXPanel();
        
        // run later because it had to be run on javafx thread
        Platform.runLater( () -> {
            Image img = new Image("resources/images/clouds.jpg");
            Image lowerOpacity = ImageUtils.setOpacity(img, 0.20);
            
            PixelReader imgReader = img.getPixelReader();
            PixelReader lowerOpacityReader = lowerOpacity.getPixelReader();
            
            int imgARGB = imgReader.getArgb(50, 50);
            int opacityARGB = lowerOpacityReader.getArgb(50, 50);
            
            // alpha is most significant bit in the ARGB int
            assertTrue(Math.abs(opacityARGB) < Math.abs(imgARGB));
        });
    }

}
