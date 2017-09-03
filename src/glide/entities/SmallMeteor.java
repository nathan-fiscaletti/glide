package glide.entities;

import java.awt.image.BufferedImage;

import glide.Glide;
import glide.Glide.Difficulty;
import glide.SinglePlayerGame;

public class SmallMeteor extends Entity{

	int speed = 1 + (int)(Math.random()*2);
	int plusOrMinus = (random.nextBoolean()) ? -1 : 1;
	int meteorType = 1;
	
	public SmallMeteor(double x, double y, SinglePlayerGame game) {
		super(x, y, game);
		this.meteorType = random.nextInt(7);
		this.setType(Entity.Type.METEORSMALL);
	}
	
	@Override
	public BufferedImage getEntityImage()
	{
		switch (meteorType) {
			case 1 : return game.getTextures().smallmeteor1;
			case 2 : return game.getTextures().smallmeteor2;
			case 3 : return game.getTextures().smallmeteor3;
			case 4 : return game.getTextures().smallmeteor4;
			case 5 : return game.getTextures().smallmeteor5;
			case 6 : return game.getTextures().smallmeteor6;
			
			default : return game.getTextures().smallmeteor1;
		}
	}
	
	@Override
	public void tick(){
		this.setY(this.getY() + speed);
		
		if(Glide.difficulty != Difficulty.Easy){
			this.setX(this.getX() + speed*plusOrMinus);
		}
	}

}
