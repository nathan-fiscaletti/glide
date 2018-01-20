package glide.game.screens;

import glide.engine.Screen;
import glide.game.Glide;
import glide.versioning.Updater;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public final class MainMenu extends Screen {

	/**
	 * Generated Serial Version UID
	 */
	private static final long serialVersionUID = -4093553489357496142L;
	
	private int selected = 1;
	
	@Override
	protected final void initialize() {
		this.name = "MainMenu";
		this.shouldRenderLogo = true;
		this.shouldRenderBackground = true;
		if (! Glide.checkedForUpdate) {
			Updater updater = null;
			if(Updater.internetCheck()){
				try {
					updater = new Updater();
				} catch (Exception e) {
					Glide.update = "There was an error while checking for update. - Unable to connect to Glide Servers.";
					Glide.update_color = Color.RED;
				}
		
				if(updater != null && updater.needsUpdate()){
					Glide.update = "There is an update available for version " + updater.getLatestVersion().getVersion() + " Build " + updater.getLatestVersion().getBuild() + "!";
					Glide.update2 = "Update at " + Updater.updateAt;
					Glide.update_color = Color.YELLOW;
					Glide.TITLE = Glide.TITLE + " ~ !!UPDATE AVAILABLE!!";
				}
			}else{
				Glide.update = "There was an error while checking for update. - Unable to connect to Glide Servers.";
				Glide.update_color = Color.RED;
			}
			Glide.checkedForUpdate = true;
		}
	}
	
	@Override
	protected final void update() {
		// Don't implement
	}
	
	@Override
	protected final void renderFrame(Graphics graphics){
		Font f = new Font("Ariel", Font.BOLD, 24);
		graphics.setFont(f);
		
		/* Logo */
		f = new Font("Ariel", Font.BOLD, 12);
		graphics.setFont(f);
		graphics.setColor(Color.GREEN);
		graphics.drawChars(Glide.version.toCharArray(), 0, Glide.version.length(), ((Glide.WIDTH * Glide.SCALE) / 2) - (graphics.getFontMetrics().stringWidth(Glide.version) / 2), 130 + Glide.logoRenderer.getLogoHeight());
		
		/* Updater */
		graphics.setColor(Glide.update_color);
		graphics.drawChars(Glide.update.toCharArray(), 0, Glide.update.length(), ((Glide.WIDTH * Glide.SCALE) / 2) - (graphics.getFontMetrics().stringWidth(Glide.update) / 2), 152 + Glide.logoRenderer.getLogoHeight());
		graphics.drawChars(Glide.update2.toCharArray(), 0, Glide.update2.length(), ((Glide.WIDTH * Glide.SCALE) / 2) - (graphics.getFontMetrics().stringWidth(Glide.update2) / 2), 174 + Glide.logoRenderer.getLogoHeight());
		/* End Updater */
		
		graphics.setColor(Color.GREEN);
		f = new Font("Ariel", Font.BOLD, 24);
		graphics.setFont(f);
		
		/* Menu Items */
		
		//Play
		/////
		graphics.setColor((selected == 1) ? Color.GREEN : Color.DARK_GRAY);
		String sco = "Play";
		int w = graphics.getFontMetrics().stringWidth(sco);
		graphics.drawChars(sco.toCharArray(), 0, sco.toCharArray().length, ((Glide.WIDTH * Glide.SCALE) / 2) - (w / 2), ((Glide.HEIGHT * Glide.SCALE) / 2) + (graphics.getFontMetrics().getDescent() - 49 + Glide.logoRenderer.getLogoHeight()));
		////
		
		
		//How to play
		///
		graphics.setColor((selected == 9001) ? Color.GREEN : Color.DARK_GRAY);
		String sco2 = "Multiplayer (Coming soon)";
		int w2 = graphics.getFontMetrics().stringWidth(sco2);
		graphics.drawChars(sco2.toCharArray(), 0, sco2.toCharArray().length, ((Glide.WIDTH * Glide.SCALE) / 2) - (w2 / 2), ((Glide.HEIGHT * Glide.SCALE) / 2) + (graphics.getFontMetrics().getDescent() - 25 + Glide.logoRenderer.getLogoHeight()));
		///
		
		//How to play
		///
		graphics.setColor((selected == 2) ? Color.GREEN : Color.DARK_GRAY);
		sco2 = "How To Play";
		w2 = graphics.getFontMetrics().stringWidth(sco2);
		graphics.drawChars(sco2.toCharArray(), 0, sco2.toCharArray().length, ((Glide.WIDTH * Glide.SCALE) / 2) - (w2 / 2), ((Glide.HEIGHT * Glide.SCALE) / 2) + (graphics.getFontMetrics().getDescent() - 25 + Glide.logoRenderer.getLogoHeight() + 25));
		///
		
		//Options
		///
		graphics.setColor((selected == 3) ? Color.GREEN : Color.DARK_GRAY);
		sco2 = "Options";
		w2 = graphics.getFontMetrics().stringWidth(sco2);
		graphics.drawChars(sco2.toCharArray(), 0, sco2.toCharArray().length, ((Glide.WIDTH * Glide.SCALE) / 2) - (w2 / 2), ((Glide.HEIGHT * Glide.SCALE) / 2) + (graphics.getFontMetrics().getDescent() + Glide.logoRenderer.getLogoHeight() + 25));
		///
		
		//Exit
		///
		graphics.setColor((selected == 4) ? Color.GREEN : Color.DARK_GRAY);
		String sco3 = "Exit";
		int w3 = graphics.getFontMetrics().stringWidth(sco3);
		graphics.drawChars(sco3.toCharArray(), 0, sco3.toCharArray().length, ((Glide.WIDTH * Glide.SCALE) / 2) - (w3 / 2), ((Glide.HEIGHT * Glide.SCALE) / 2) + (graphics.getFontMetrics().getDescent() + Glide.logoRenderer.getLogoHeight() + 25 + 25));
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
			Glide.s_select.play();
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
			Glide.s_select.play();
		}else if(key == KeyEvent.VK_ENTER){
			if(selected == 1){
				Glide.s_enter.play();
				Glide.setScreen(new SinglePlayerGame());
			}else if(selected == 2){
				Glide.s_enter.play();
				Glide.setScreen(new HTPMenu());
			}else if(selected == 3){
				Glide.s_enter.play();
				Glide.setScreen(new OptionsMenu());
			}else if(selected == 4){
				System.exit(0);
			}
		}
	}
	
	@Override
	protected final void onKeyReleased(KeyEvent e){
		// Don't implement
	}

}
