package two.d.engine;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Class for implementing both render and update methods.
 *
 * @author Nathan
 */
public abstract class Renderer {
	
	/**
	 * If set to false, this will not render when it is in the global render scope.
	 */
	public boolean shouldRenderWhenGlobal = true;
	
	/**
	 * If set to true, this render will render on top of everything else.
	 */
	public boolean topMost = false;
	
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
	public static BufferedImage blackImage(Engine engine)
	{
		return new BufferedImage (
				engine.getWidth(),
				engine.getHeight(),
				BufferedImage.TYPE_INT_RGB
		);
	}
}
