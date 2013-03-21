package glide;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ControlsMenuKeyListener extends KeyAdapter{
	
	ControlsMenu menu;
	
	public ControlsMenuKeyListener (ControlsMenu game){
		this.menu = game;
	}
	
	public void keyPressed(KeyEvent e){
		menu.keyPressed(e);
	}
	
	public void keyReleased(KeyEvent e){
		menu.keyReleased(e);
	}

}
