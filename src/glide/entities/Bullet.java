package glide.entities;

import glide.SinglePlayerGame;

import java.awt.image.BufferedImage;

import glide.Glide;

public class Bullet extends Entity{
	public Bullet(double x, double y, SinglePlayerGame game){
		super(x, y, game);
		this.setType(Entity.Type.BULLET);
		Glide.s_shoot.play();
	}
	
	@Override
	public BufferedImage getEntityImage()
	{
		return game.getTextures().bullet;
	}
	
	@Override
	public void tick(){
		this.setY(this.getY() - 20);
	}
}
