package glide.game.renderers;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import two.d.engine.Engine;
import two.d.engine.Renderer;
import two.d.engine.graphics.BufferedImageLoader;

public class LogoRenderer extends Renderer {

	BufferedImage logo;
	Engine parentEngine;
	
	public LogoRenderer(String logoPath, Engine engine)
	{
		try {
			logo = BufferedImageLoader.load(logoPath);
		} catch (Exception e){
			System.err.println("Error: Failed to load Logo.");
		}
		
		this.parentEngine = engine;
		this.topMost = true;
	}
	
	@Override
	public final void render(Graphics g, Canvas canvas) {
		if (logo != null) {
			g.drawImage(logo, (parentEngine.getWidth() / 2) - (logo.getWidth() / 2), 120, null);
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
