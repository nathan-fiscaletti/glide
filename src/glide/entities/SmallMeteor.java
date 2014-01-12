package glide.entities;

import java.awt.image.BufferedImage;
import java.util.Random;

import glide.Glide;
import glide.Glide.Difficulty;
import glide.SinglePlayerGame;

public class SmallMeteor extends Entity{

	int speed = 1 + (int)(Math.random()*2);
	
	int plusOrMinus = (new Random().nextBoolean()) ? -1 : 1;
	
	public SmallMeteor(double x, double y, SinglePlayerGame game) {
		super(x, y, game);
		this.setType(Entity.Type.METEORSMALL);
		this.setEntityImage(EI());
	}
	
	public BufferedImage EI(){
		Random r = new Random();
		int i = r.nextInt(7);
		if(i == 1){
			return game.getTextures().smallmeteor1;
		}else if(i == 2){
			return game.getTextures().smallmeteor2;
		}else if(i == 3){
			return game.getTextures().smallmeteor3;
		}else if(i == 4){
			return game.getTextures().smallmeteor4;
		}else if(i == 5){
			return game.getTextures().smallmeteor5;
		}else if(i == 6){
			return game.getTextures().smallmeteor6;
		}else{
			return game.getTextures().smallmeteor1;
		}
	}
	
	@Override
	public void tick(){
		this.setY(this.getY() + speed);
		
		/* Difficutly Definer */
		if(Glide.difficulty == Difficulty.Normal || Glide.difficulty == Difficulty.Hard || Glide.difficulty == Difficulty.Expert){
			this.setX(this.getX() + speed*plusOrMinus);
		}
	}

}
