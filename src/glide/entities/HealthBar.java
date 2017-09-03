package glide.entities;

import java.awt.image.BufferedImage;

import glide.SinglePlayerGame;

public class HealthBar extends Entity{
	private int health = 8;
	private SinglePlayerGame game;
	
	public HealthBar(double x, double y, SinglePlayerGame game) {
		super(x, y, game);
		this.game = game;
		this.setType(Entity.Type.HEALTHBAR);
	}
	
	@Override
	public void tick(){
		health = (health > 8) ? 8 : ((health < 1) ? 1 : health);
	}
	
	@Override
	public BufferedImage getEntityImage()
	{
		switch (health) {
			case 8 : return game.getTextures().healthbar1;
			case 7 : return game.getTextures().healthbar2;
			case 6 : return game.getTextures().healthbar3;
			case 5 : return game.getTextures().healthbar4;
			case 4 : return game.getTextures().healthbar5;
			case 3 : return game.getTextures().healthbar6;
			case 2 : return game.getTextures().healthbar7;
			case 1 : return game.getTextures().healthbar8;
			
			default : return game.getTextures().healthbar1;
		}
	}
	
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
}
