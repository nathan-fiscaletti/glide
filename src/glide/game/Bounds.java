package glide.game;

import jtwod.engine.Engine;
import jtwod.engine.drawable.Entity;

import java.awt.Rectangle;
import java.awt.Shape;

public final class Bounds {
	public static boolean intersectsWith(Shape a, Entity<? extends Engine> b){
		Rectangle r2 = new Rectangle();
		r2.setBounds(b.getPosition().getX(), b.getPosition().getY(), b.getSize().getWidth(), b.getSize().getHeight());
		
		return (a.intersects(r2));
	}
}
