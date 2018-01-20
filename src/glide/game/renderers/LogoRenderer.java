package glide.game.renderers;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import glide.engine.Renderer;
import glide.engine.graphics.BufferedImageLoader;
import glide.game.Glide;

public class LogoRenderer extends Renderer {

	BufferedImage logo;
	
	public LogoRenderer(String logoPath)
	{
		try {
			logo = BufferedImageLoader.load(logoPath);
		} catch (Exception e){
			System.err.println("Error: Failed to load Logo.");
		}
	}
	
	@Override
	public final void render(Graphics g, Canvas canvas) {
		if (logo != null) {
			g.drawImage(logo, ((Glide.WIDTH * Glide.SCALE) / 2) - (logo.getWidth() / 2), 120, null);
		}
	}

	@Override
	public final void update() {
		// Don't implement
	}

	public final int getLogoHeight()
	{
		return (this.logo == null) ? 0 : this.logo.getHeight();
	}
}
