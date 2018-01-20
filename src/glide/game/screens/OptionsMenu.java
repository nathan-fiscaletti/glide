package glide.game.screens;

import glide.engine.Screen;
import glide.game.Glide;
import glide.game.Glide.Difficulty;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public final class OptionsMenu extends Screen {

	/**
	 * Generated Serial Version UID
	 */
	private static final long serialVersionUID = -4093553489357496142L;
	
	private int selected = 1;
	
	@Override
	protected final void initialize(){
		this.name = "OptionsMenu";
		this.shouldRenderBackground = true;
		this.shouldRenderLogo = true;
	}
	
	@Override
	protected final void update()
	{
		// Don't implement
	}
	
	@Override
	protected final void renderFrame(Graphics graphics){
		Font f = new Font("Ariel", Font.BOLD, 24);
		graphics.setFont(f);
		
		String op = "Options";
		f = new Font("Ariel", Font.BOLD, 32);
		graphics.setFont(f);
		graphics.setColor(Color.ORANGE);
		int w = graphics.getFontMetrics().stringWidth(op);
		graphics.drawChars(op.toCharArray(), 0, op.toCharArray().length, ((Glide.WIDTH * Glide.SCALE) / 2) - (w / 2), ((Glide.HEIGHT * Glide.SCALE) / 2) + (graphics.getFontMetrics().getDescent() - 130 + Glide.logoRenderer.getLogoHeight()));
		
		
		/* Menu Items */
		
		f = new Font("Ariel", Font.BOLD, 24);
		graphics.setFont(f);
		
		
		//Audio
		/////
		if(selected == 1){
			graphics.setColor(Color.GREEN);
		}else{
			graphics.setColor(Color.DARK_GRAY);
		}
		String sco = "Music - " + ((Glide.music) ? "Enabled" : "Disabled");
		w = graphics.getFontMetrics().stringWidth(sco);
		graphics.drawChars(sco.toCharArray(), 0, sco.toCharArray().length, ((Glide.WIDTH * Glide.SCALE) / 2) - (w / 2), ((Glide.HEIGHT * Glide.SCALE) / 2) + (graphics.getFontMetrics().getDescent() - 49 + Glide.logoRenderer.getLogoHeight()));
		////
		//
		
		//Sounds
			/////
			if(selected == 2){
				graphics.setColor(Color.GREEN);
			}else{
				graphics.setColor(Color.DARK_GRAY);
			}
			sco = "Sounds - " + ((Glide.sounds) ? "Enabled" : "Disabled");
			w = graphics.getFontMetrics().stringWidth(sco);
			graphics.drawChars(sco.toCharArray(), 0, sco.toCharArray().length, ((Glide.WIDTH * Glide.SCALE) / 2) - (w / 2), ((Glide.HEIGHT * Glide.SCALE) / 2) + (graphics.getFontMetrics().getDescent() + Glide.logoRenderer.getLogoHeight() - 13));
			////
			//
			
    	//Controls
		/////
			if(selected == 3){
				graphics.setColor(Color.GREEN);
			}else{
				graphics.setColor(Color.DARK_GRAY);
			}
			sco = "Controls";
			w = graphics.getFontMetrics().stringWidth(sco);
			graphics.drawChars(sco.toCharArray(), 0, sco.toCharArray().length, ((Glide.WIDTH * Glide.SCALE) / 2) - (w / 2), ((Glide.HEIGHT * Glide.SCALE) / 2) + (graphics.getFontMetrics().getDescent() + Glide.logoRenderer.getLogoHeight() + 24));
			////
			//

		//Difficulty
		/////
			if(selected == 4){
				graphics.setColor(Color.GREEN);
			}else{
				graphics.setColor(Color.DARK_GRAY);
			}
			sco = "Difficulty - ";
			String sco2 = Glide.difficulty.toString();
			
			
			w = graphics.getFontMetrics().stringWidth(sco);
			int w2 = graphics.getFontMetrics().stringWidth(sco2);
			graphics.drawChars(sco.toCharArray(), 0, sco.toCharArray().length, ((Glide.WIDTH * Glide.SCALE) / 2) - ((w + w2) / 2), ((Glide.HEIGHT * Glide.SCALE) / 2) + (graphics.getFontMetrics().getDescent() + Glide.logoRenderer.getLogoHeight() + 59));
			graphics.setColor(Glide.getDifficultyColor(Glide.difficulty));
			graphics.drawChars(sco2.toCharArray(), 0, sco2.toCharArray().length, ((Glide.WIDTH * Glide.SCALE) / 2) - ((w + w2) / 2) + w, ((Glide.HEIGHT * Glide.SCALE) / 2) + (graphics.getFontMetrics().getDescent() + Glide.logoRenderer.getLogoHeight() + 59));
			graphics.setColor(Color.GREEN);
			////
			//

		
		
		//Back
		///
		if(selected == 5){
			graphics.setColor(Color.GREEN);
		}else{
			graphics.setColor(Color.DARK_GRAY);
		}
		String sco3 = "Back";
		int w3 = graphics.getFontMetrics().stringWidth(sco3);
		graphics.drawChars(sco3.toCharArray(), 0, sco3.toCharArray().length, ((Glide.WIDTH * Glide.SCALE) / 2) - (w3 / 2), ((Glide.HEIGHT * Glide.SCALE) / 2) + (graphics.getFontMetrics().getDescent() + Glide.logoRenderer.getLogoHeight() + 108));
		///
	}
	
	@Override
	protected final void onKeyPressed(KeyEvent e){

		int key = e.getKeyCode();
		if(key == KeyEvent.VK_UP){
			if(selected == 1){
				selected = 5;
			}else if(selected == 2){
				selected = 1;
			}else if(selected == 3){
				selected = 2;
			}else if(selected == 4){
				selected = 3;
			}else if(selected == 5){
				selected = 4;
			}
			Glide.s_select.play();
		}else if (key == KeyEvent.VK_DOWN){
			if(selected == 1){
				selected = 2;
			}else if(selected == 2){
				selected = 3;
			}else if(selected == 3){
				selected = 4;
			}else if(selected == 4){
				selected = 5;
			}else if(selected == 5){
				selected = 1;
			}
			Glide.s_select.play();
		}else if(key == KeyEvent.VK_ENTER){
			if(selected == 1){
				if(Glide.music){
					Glide.muteMusic();
				}else{
					Glide.unmuteMusic();
				}
			}else if(selected == 2){
				if(Glide.sounds){
					Glide.muteSounds();
				}else{
					Glide.unmuteSounds();
				}
			}else if(selected == 3){
				//Controls screen
				Glide.s_enter.play();
				Glide.setScreen(new ControlsMenu());
			}else if(selected == 4){
				Glide.s_enter.play();
				if(Glide.difficulty == Difficulty.Easy){
					Glide.difficulty = Difficulty.Normal;
				}else if(Glide.difficulty == Difficulty.Normal){
					Glide.difficulty = Difficulty.Hard;
				}else if(Glide.difficulty == Difficulty.Hard){
					Glide.difficulty = Difficulty.Expert;
				}else if(Glide.difficulty == Difficulty.Expert){
					Glide.difficulty = Difficulty.Easy;
				}
			}else if(selected == 5){
				Glide.s_enter.play();
				Glide.setScreen(new MainMenu());
			}
		}
		

	}
	
	@Override
	protected final void onKeyReleased(KeyEvent e){
		// Don't implement
	}
}
