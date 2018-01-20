package glide.engine;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import glide.game.Glide;

/**
 * Class for implementing both render and update methods.
 *
 * @author Nathan
 */
public abstract class Renderer {
	/**
	 * Render graphics out to a canvas.
	 *
	 * @param g
	 * @param canvas
	 */
	protected abstract void render(Graphics g, Canvas canvas);
	
	/**
	 * Update this renderer.
	 */
	protected abstract void update();
	
	/**
	 * Retrieve a totally black image.
	 *
	 * @return
	 */
	public static BufferedImage blackImage()
	{
		return new BufferedImage(Glide.WIDTH*Glide.SCALE,Glide.HEIGHT*Glide.SCALE,BufferedImage.TYPE_INT_RGB);
	}
}
