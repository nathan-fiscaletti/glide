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

public class ControlsMenu extends Screen<GlideEngine> {

	/**
	 * Create the menu.
	 * @param engine
	 */
	public ControlsMenu(GlideEngine engine) {
		super(engine);
	}

	/**
	 * Generated Serial Version UID
	 */
	private static final long serialVersionUID = -4093553489357496142L;
	
	/* Game Properties */
	private int selected = 1;
	
	@Override
	protected final void initialize()
	{
		this.name = "ControlsMenu";
	}

	@Override
	protected final void update(){
		// Don't implement
	}
	
	@Override
	protected final void renderFrame(Graphics graphics){
		Font f = new Font("Ariel", Font.BOLD, 24);
		graphics.setFont(f);
		graphics.setColor(Color.ORANGE);
		
		String op = "Controls";
		f = new Font("Ariel", Font.BOLD, 32);
		graphics.setFont(f);
		graphics.setColor(Color.ORANGE);
		int w = graphics.getFontMetrics().stringWidth(op);
		graphics.drawChars(op.toCharArray(), 0, op.toCharArray().length, (this.parentEngine.getWidth() / 2) - (w / 2), (this.parentEngine.getHeight() / 2) + (graphics.getFontMetrics().getDescent() - 130 + this.parentEngine.logoRenderer.getLogoHeight()));
		
		
		/* Menu Items */
		f = new Font("Ariel", Font.BOLD, 24);
		graphics.setFont(f);

		BufferedImage controlls = null;
		try {
			  controlls = BufferedImageLoader.load("/images/keys/controlls.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		graphics.drawImage(controlls, (this.parentEngine.getWidth() / 2) - (controlls.getWidth() / 2), 120 + this.parentEngine.logoRenderer.getLogoHeight() + 24 + 40, this);
		//Exit
		///
		graphics.setColor(Color.GREEN);
		String sco3 = "Back";
		int w3 = graphics.getFontMetrics().stringWidth(sco3);
		graphics.drawChars(sco3.toCharArray(), 0, sco3.toCharArray().length, (this.parentEngine.getWidth() / 2) - (w3 / 2), (controlls.getHeight() + 24 + 18 + 140 + this.parentEngine.logoRenderer.getLogoHeight() + 40));
		///
	}
	
	@Override
	protected final void onKeyPressed(KeyEvent e){
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_UP){
			if(selected == 1){
				selected = 4;
			}else if(selected == 2){
				selected = 1;
			}else if(selected == 3){
				selected = 2;
			}else if(selected == 4){
				selected = 3;
			}
			this.parentEngine.sounds.s_select.play(this.parentEngine);
		}else if (key == KeyEvent.VK_DOWN){
			if(selected == 1){
				selected = 2;
			}else if(selected == 2){
				selected = 3;
			}else if(selected == 3){
				selected = 4;
			}else if(selected == 4){
				selected = 1;
			}
			this.parentEngine.sounds.s_select.play(this.parentEngine);
		}else if(key == KeyEvent.VK_ENTER){
			this.parentEngine.sounds.s_enter.play(this.parentEngine);
			this.parentEngine.setScreen(new OptionsMenu(this.parentEngine));
		}
	}
	
	@Override
	protected final void onKeyReleased(KeyEvent e){
		// Don't Implemented
	}
}
