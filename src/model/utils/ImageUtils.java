package model.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * Static Class to handle Image pixel conversions.
 * 
 * @author Andrew Pelletier
 *
 */
public class ImageUtils {
    
    public static Image addTint(Image img, int redDelta, int greenDelta, int blueDelta) {
        ImageView imgView = new ImageView(img);
        
        WritableImage writableImg = imgView.snapshot(null, null);
        
        PixelWriter pixelWriter = writableImg.getPixelWriter();
        PixelReader pixelReader = writableImg.getPixelReader();
        
        for (int y = 0; y < writableImg.getHeight(); y++) {
            for (int x = 0; x < writableImg.getWidth(); x++) {
                Color color = pixelReader.getColor(x, y);
                
                int red = (int) (color.getRed() * 255);
                red += redDelta;
                if (red > 255) {
                    red = 255;
                } else if (red < 0) {
                    red = 0;
                }
                
                int green = (int) (color.getGreen() * 255);
                green += greenDelta;
                if (green > 255) {
                    green = 255;
                } else if (green < 0) {
                    green = 0;
                }
                
                int blue = (int) (color.getBlue() * 255);
                blue += blueDelta;
                if (blue > 255) {
                    blue = 255;
                } else if (blue < 0) {
                    blue = 0;
                }
                
                Color c = Color.rgb(red, green, blue);
                pixelWriter.setColor(x, y, c);
            }
        }
        return writableImg;
    }
    
    public static Image setOpacity(Image img, double opacity) {
        ImageView imgView = new ImageView(img);
        imgView.setOpacity(opacity);
        return imgView.snapshot(null, null);
    }
    
}
