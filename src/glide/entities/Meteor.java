package glide.entities;

import java.awt.image.BufferedImage;

import glide.Glide;
import glide.SinglePlayerGame;
import glide.Glide.Difficulty;

public class Meteor extends Entity{
	int speed = 1 + (int)(Math.random()*3);
	int plusOrMinus = (this.random.nextBoolean()) ? -1 : 1;
	int meteorType = 1;
	
	public Meteor(double x, double y, SinglePlayerGame game) {
		super(x, y, game);
		this.setType(Entity.Type.METEORBIG);
		meteorType = random.nextInt(7);
		
		//Make small or no..
		if(!random.nextBoolean()) this.die(false);
		
	}
	
	@Override
	public BufferedImage getEntityImage() {
		switch(meteorType) {
			case 1 : return game.getTextures().meteor1;
			case 2 : return game.getTextures().meteor2;
			case 3 : return game.getTextures().meteor3;
			case 4 : return game.getTextures().meteor4;
			case 5 : return game.getTextures().meteor5;
			case 6 : return game.getTextures().meteor6;
			
			default: return game.getTextures().meteor1;
		}
	}
	
	@Override
	public void tick(){
		// Move the meteor from the top of the screen down based on the entity speed
		this.setY(this.getY() + speed);
		
		// If the difficulty is anything besides EASY, 
		// move the meteor left or right as well.
		if(Glide.difficulty != Difficulty.Easy){
			this.setX(this.getX() + speed*plusOrMinus);
		}
	}
	
	public void die(boolean forceSmall){
		// Generate the child meteors when a meteor is destroyed
		if(random.nextBoolean() || forceSmall){
			
			// Generate between 0 and 5 meteors
			int smallMeteorCount = random.nextInt(5);
			
			// Give the small meteors a spread of 70 pixels
			int spread = 70;
			for (int i = 0;i<smallMeteorCount;i++) {
				SmallMeteor small_meteor = new SmallMeteor(this.getX(), this.getY(), this.game);
				small_meteor.setX(this.getX() + (random.nextBoolean() ? -random.nextInt(spread) : +random.nextInt(spread)));
				small_meteor.setY(this.getY() + (random.nextBoolean() ? -random.nextInt(spread) : +random.nextInt(spread)));
				game.getController().addSmallMeteor(small_meteor);
			}
			
			
		}
		
		game.getController().removeMeteor(this);
	}
	
	

}
