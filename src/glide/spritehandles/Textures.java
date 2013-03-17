package glide.spritehandles;

import glide.Game;

import java.awt.image.BufferedImage;


public class Textures {
	
	public BufferedImage player,enemy,enemy2,bullet,healthbar1,healthbar2,healthbar3,healthpack,beam,diamond;
	
	private SpriteSheet sprite_sheet = null;
	public Textures(Game game){
		sprite_sheet = new SpriteSheet(game.getSpriteSheet());
		getTextures();
	}
	
	private void getTextures(){
		player = sprite_sheet.grabImage(1,1,32,32);
		enemy = sprite_sheet.grabImage(4,1,32,32);
		enemy2 = sprite_sheet.grabImage(4,2,32,32);
		bullet = sprite_sheet.grabImage(2,1,32,32);
		healthbar1 = sprite_sheet.grabImage(3,1,32,32);
		healthbar2 = sprite_sheet.grabImage(3,2,32,32);
		healthbar3 = sprite_sheet.grabImage(3,3,32,32);
		healthpack = sprite_sheet.grabImage(5, 1, 32,32);
		beam = sprite_sheet.grabImage(5, 2, 32,32);
		diamond = sprite_sheet.grabImage(5, 3, 32,32);
	}
}
