package glide;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MenuKeyInput extends KeyAdapter{
	
	MainMenu menu;
	
	public MenuKeyInput (MainMenu game){
		this.menu = game;
	}
	
	public void keyPressed(KeyEvent e){
		menu.keyPressed(e);
	}
	
	public void keyReleased(KeyEvent e){
		menu.keyReleased(e);
	}

}
