package glide.spritehandles;

import glide.Game;

import java.awt.image.BufferedImage;


public class Textures {
	
	public BufferedImage player,enemy,enemy2,enemy3,bullet,enemybullet,healthbar1,healthbar2,healthbar3,
						 healthbar4,healthbar5,healthpack,beam, diamond,des1,des2,des3,player2,playerhurt,
						 plasma,plasmaplayer,mdbullet,mdppickup,max,bombsp;
	
	private SpriteSheet sprite_sheet = null;
	public Textures(Game game){
		sprite_sheet = new SpriteSheet(game.getSpriteSheet());
		getTextures();
	}
	
	private void getTextures(){
		player = sprite_sheet.grabImage(1,1,32,32);
		player2 = sprite_sheet.grabImage(1, 2, 32, 32);
		playerhurt = sprite_sheet.grabImage(1, 3, 32, 32);
		enemy = sprite_sheet.grabImage(4,1,32,32);
		enemy2 = sprite_sheet.grabImage(4,2,32,32);
		enemy3 = sprite_sheet.grabImage(4, 3, 32, 32);
		bullet = sprite_sheet.grabImage(2,1,32,32);
		enemybullet = sprite_sheet.grabImage(2, 2, 32, 32);
		mdbullet = sprite_sheet.grabImage(2, 3, 32, 32);
		healthbar1 = sprite_sheet.grabImage(3,1,32,32);
		healthbar2 = sprite_sheet.grabImage(3,2,32,32);
		healthbar3 = sprite_sheet.grabImage(3,3,32,32);
		healthbar4 = sprite_sheet.grabImage(3,4,32,32);
		healthbar5 = sprite_sheet.grabImage(3,5,32,32);
		healthpack = sprite_sheet.grabImage(5, 1, 32,32);
		beam = sprite_sheet.grabImage(5, 2, 32,32);
		diamond = sprite_sheet.grabImage(5, 3, 32,32);
		des1 = sprite_sheet.grabImage(1, 8, 32,32);
		des2 = sprite_sheet.grabImage(2, 8, 32,32);
		des3 = sprite_sheet.grabImage(3, 8, 32,32);
		plasma = sprite_sheet.grabImage(5, 4, 32, 32);
		plasmaplayer = sprite_sheet.grabImage(1, 4, 32, 32);
		mdppickup = sprite_sheet.grabImage(5,5,32,32);
		max = sprite_sheet.grabImage(6, 1, 32, 32);
		bombsp = sprite_sheet.grabImage(5, 6, 32, 32);
	}
}
