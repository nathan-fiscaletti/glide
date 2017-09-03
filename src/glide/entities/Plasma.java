package glide.entities;

import java.awt.image.BufferedImage;

import glide.SinglePlayerGame;

public class Plasma extends Entity{

	public Plasma(double x, double y, SinglePlayerGame game) {
		super(x, y, game);
		this.setType(Entity.Type.PLASMAPLAYER);
	}
	
	@Override
	public BufferedImage getEntityImage()
	{
		return game.getTextures().plasmaplayer;
	}

}
