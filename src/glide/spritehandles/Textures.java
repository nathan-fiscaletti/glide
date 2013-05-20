package glide.spritehandles;

import glide.Game;

import java.awt.image.BufferedImage;


public class Textures {
	
	public BufferedImage player,enemy,enemy2,enemy3,bullet,enemybullet,healthbar1,healthbar2,healthbar3,
						 healthbar4,healthbar5,healthbar6,healthbar7,healthbar8,powerbar1,powerbar2,powerbar3,powerbar4,powerbar5,healthpack,beam, diamond, diamond2, diamond3, des1,des2,des3,player2,playerhurt,
						 plasma,plasmaplayer,mdbullet,mdppickup,max,bombsp,meteor1,meteor2,meteor3,meteor4,
						 meteor5,meteor6,smallmeteor1,smallmeteor2,smallmeteor3,smallmeteor4,smallmeteor5,smallmeteor6,cod,cod_pickup,max_cod,grayscale_beam, grayscale_sheild;
	
	private SpriteSheet sprite_sheet = null;
	public Textures(Game game){
		sprite_sheet = new SpriteSheet(game.getSpriteSheet());
		getTextures();
	}
	
	private void getTextures(){
		/* Enemies */
		enemy = sprite_sheet.grabImage(4,1,32,32);
		enemy2 = sprite_sheet.grabImage(4,2,32,32);
		enemy3 = sprite_sheet.grabImage(4, 3, 32, 32);
		
		/* Bullets */
		bullet = sprite_sheet.grabImage(2,1,32,32);
		enemybullet = sprite_sheet.grabImage(2, 2, 32, 32);
		mdbullet = sprite_sheet.grabImage(2, 3, 32, 32);
		
		/* Animations */
		des1 = sprite_sheet.grabImage(1, 8, 32,32);
		des2 = sprite_sheet.grabImage(2, 8, 32,32);
		des3 = sprite_sheet.grabImage(3, 8, 32,32);
		
		/* Player States */
		player = sprite_sheet.grabImage(1,1,32,32);
		player2 = sprite_sheet.grabImage(1, 2, 32, 32);
		playerhurt = sprite_sheet.grabImage(1, 3, 32, 32);
		plasmaplayer = sprite_sheet.grabImage(1, 4, 32, 32);
		
		/* GUI */
		max = sprite_sheet.grabImage(6, 1, 32, 32);
		max_cod = sprite_sheet.grabImage(6, 2, 32, 32);
		bombsp = sprite_sheet.grabImage(5, 6, 32, 32);
		mdppickup = sprite_sheet.grabImage(5,5,32,32);
		cod = sprite_sheet.grabImage(4, 5, 32, 32);
		
		/* Drops */
		beam = sprite_sheet.grabImage(5, 2, 32,32);
		plasma = sprite_sheet.grabImage(5, 4, 32, 32);
		mdppickup = sprite_sheet.grabImage(5,5,32,32);
		diamond = sprite_sheet.grabImage(5, 3, 32,32);
		diamond2 = sprite_sheet.grabImage(6, 3, 32, 32);
		diamond3 = sprite_sheet.grabImage(7, 3, 32, 32);
		healthpack = sprite_sheet.grabImage(5, 1, 32,32);
		cod_pickup = sprite_sheet.grabImage(4, 4, 32, 32);
		
		/* Health Bar */
		healthbar1 = sprite_sheet.grabImage(7, 1, 32, 32);
		healthbar2 = sprite_sheet.grabImage(7, 2, 32, 32);
		healthbar3 = sprite_sheet.grabImage(8, 1, 32, 32);
		healthbar4 = sprite_sheet.grabImage(8, 2, 32, 32);
		healthbar5 = sprite_sheet.grabImage(8, 3, 32, 32);
		healthbar6 = sprite_sheet.grabImage(8, 4, 32, 32);
		healthbar7 = sprite_sheet.grabImage(8, 5, 32, 32);
		healthbar8 = sprite_sheet.grabImage(8, 6, 32, 32);
		
		/* Power Bar */
		powerbar1 = sprite_sheet.grabImage(3,1,32,32);
		powerbar2 = sprite_sheet.grabImage(3,2,32,32);
		powerbar3 = sprite_sheet.grabImage(3,3,32,32);
		powerbar4 = sprite_sheet.grabImage(3,4,32,32);
		powerbar5 = sprite_sheet.grabImage(3,5,32,32);
		
		grayscale_sheild = ImageFunctions.grayScale(plasma);
		grayscale_beam = ImageFunctions.grayScale(beam);

		/* Meteors */
		meteor1 = sprite_sheet.grabImage(1, 7, 32, 32);
		meteor2 = sprite_sheet.grabImage(2, 7, 32, 32);
		meteor3 = sprite_sheet.grabImage(3, 7, 32, 32);
		meteor4 = sprite_sheet.grabImage(1, 6, 32, 32);
		meteor5 = sprite_sheet.grabImage(2, 6, 32, 32);
		meteor6 = sprite_sheet.grabImage(3, 6, 32, 32);
		
		/* Small Meteors */
		smallmeteor1 = sprite_sheet.grabImage(4, 7, 32, 32);
		smallmeteor2 = sprite_sheet.grabImage(5, 7, 32, 32);
		smallmeteor3 = sprite_sheet.grabImage(4, 6, 32, 32);
		smallmeteor4 = sprite_sheet.grabImage(6, 7, 32, 32);
		smallmeteor5 = sprite_sheet.grabImage(7, 7, 32, 32);
		smallmeteor6 = sprite_sheet.grabImage(8, 7, 32, 32);
		
	}
}
