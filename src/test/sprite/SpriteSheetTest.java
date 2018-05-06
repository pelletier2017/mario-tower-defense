package test.sprite;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javafx.embed.swing.JFXPanel;
import javafx.scene.canvas.Canvas;
import model.sprite.SpriteSheet;
import model.sprite.SpriteSheetLoader;
import org.junit.jupiter.api.Test;

public class SpriteSheetTest {
	@Test
	public void testSheet() {
		new JFXPanel();
		SpriteSheet s = SpriteSheetLoader.getSheet("test/MarioWithDeath", 46, 46, 5, new int[] {2,2,8,8,6});
		assertEquals(s.getXOffset(), 46);
		assertEquals(s.getYOffset(), 46);
		assertEquals(s.getColumns(0), 2);
		assertEquals(s.getColumns(1), 2);
		assertEquals(s.getColumns(2), 8);
		assertEquals(s.getColumns(3), 8);
		assertEquals(s.getColumns(4), 6);
		assertEquals(s.getRows(), 5);
		
		Canvas c = new Canvas();
		s.staticRender(c.getGraphicsContext2D(), 0, 0, 0, 0);
		s.staticRender(c.getGraphicsContext2D(), 0, 0, 0, 0, 10, 10);
	}
}
