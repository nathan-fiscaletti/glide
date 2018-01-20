package two.d.engine.graphics;

import java.awt.Rectangle;
import java.awt.Shape;

public class Bounds {
	public static boolean intersectsWith(Shape a, two.d.engine.Entity<?> b){
		Rectangle r2 = new Rectangle();
		r2.setBounds(b.position.x, b.position.y, b.renderedSprite.getWidth(), b.renderedSprite.getHeight());
		
		return (a.intersects(r2));
	}

}
