package glide.engine.graphics;



import java.awt.Rectangle;
import java.awt.Shape;


public class Bounds {
	public static boolean intersectsWith(glide.engine.Entity a, glide.engine.Entity b){
		Rectangle r = new Rectangle();
		r.setBounds(a.position.x, a.position.y, a.renderedSprite.getWidth(), a.renderedSprite.getHeight());
		
		Rectangle r2 = new Rectangle();
		r2.setBounds(b.position.x, b.position.y, b.renderedSprite.getWidth(), b.renderedSprite.getHeight());
		
		return (r.intersects(r2));
	}
	
	public static boolean intersectsWith(Shape a, glide.engine.Entity b){
		Rectangle r2 = new Rectangle();
		r2.setBounds(b.position.x, b.position.y, b.renderedSprite.getWidth(), b.renderedSprite.getHeight());
		
		return (a.intersects(r2));
	}

}
