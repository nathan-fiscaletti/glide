package glide.game.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import glide.game.GlideEngine;
import two.d.engine.Screen;
import two.d.engine.graphics.BufferedImageLoader;



public final class HTPMenu extends Screen<GlideEngine>
{

	public HTPMenu(GlideEngine engine) {
		super(engine);
	}

	/**
	 * Generated Serial Version UID
	 */
	private static final long serialVersionUID = -4093553489357496142L;
	
	@Override
	protected final void initialize()
	{
		this.name = "HTPMenu";
	}
	
	@Override
	protected final void update()
	{
		// Don't implement
	}
	
	@Override
	protected final void renderFrame(Graphics graphics){

		Font f = new Font("Ariel", Font.BOLD, 16);
		graphics.setFont(f);
		graphics.setColor(Color.ORANGE);
		
		BufferedImage ht = null;
		try {
			ht = BufferedImageLoader.load("/images/htp.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		graphics.drawImage(ht, (this.parentEngine.getWidth() / 2) - (ht.getWidth() / 2), 250, this);
		
		
		/* Menu Items */

		//Back
		///
		f = new Font("Ariel", Font.BOLD, 24);
		graphics.setFont(f);
		graphics.setColor(Color.GREEN);
		String sco3 = "Back";
		int w3 = graphics.getFontMetrics().stringWidth(sco3);
		graphics.drawChars(sco3.toCharArray(), 0, sco3.toCharArray().length, (this.parentEngine.getWidth() / 2) - (w3 / 2), (this.parentEngine.getHeight() / 2) + (graphics.getFontMetrics().getDescent() + this.parentEngine.logoRenderer.getLogoHeight()));
		///

	}
	
	@Override
	protected final void onKeyPressed(KeyEvent e){
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_ENTER){
			this.parentEngine.sounds.s_enter.play(this.parentEngine);
			this.parentEngine.setScreen(new MainMenu(this.parentEngine));
		}
	}
	
	@Override
	protected final void onKeyReleased(KeyEvent e){
		// Don't Implement
	}

}
