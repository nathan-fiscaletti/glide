package glide;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class OptionsKeyInput extends KeyAdapter{
	
	OptionsMenu menu;
	
	public OptionsKeyInput (OptionsMenu game){
		this.menu = game;
	}
	
	public void keyPressed(KeyEvent e){
		menu.keyPressed(e);
	}
	
	public void keyReleased(KeyEvent e){
		menu.keyReleased(e);
	}

}
