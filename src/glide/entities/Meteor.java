package glide.entities;

import java.awt.image.BufferedImage;
import java.util.Random;

import glide.Game;

public class Meteor extends Entity{
	int speed = 1 + (int)(Math.random()*3);
	public Meteor(double x, double y, Game game) {
		super(x, y, game);
		this.setType(TYPE_METEORBIG);
		this.setEntityImage(EI());
		
		//Make small or no..
		Random r = new Random();
		if(!r.nextBoolean()) this.die();
		
	}
	
	public BufferedImage EI(){
		Random r = new Random();
		int i = r.nextInt(7);
		if(i == 1){
			return game.getTextures().meteor1;
		}else if(i == 2){
			return game.getTextures().meteor2;
		}else if(i == 3){
			return game.getTextures().meteor3;
		}else if(i == 4){
			return game.getTextures().meteor4;
		}else if(i == 5){
			return game.getTextures().meteor5;
		}else if(i == 6){
			return game.getTextures().meteor6;
		}else{
			return game.getTextures().meteor1;
		}
	}
	
	public void die(){
		if(new Random().nextBoolean()){
			SmallMeteor s1 = new SmallMeteor(this.getX(), this.getY(), this.game);
			SmallMeteor s2 = new SmallMeteor(this.getX(), this.getY(), this.game);
			SmallMeteor s3 = new SmallMeteor(this.getX(), this.getY(), this.game);
		
			int spread = 70;
		
			//Small 1
			boolean updown = new Random().nextBoolean();
			boolean leftright = new Random().nextBoolean();
			int i2 = new Random().nextInt(spread);
			int i3 = new Random().nextInt(spread);
			
			s1.setX((updown) ? (this.getX() - i2) : (this.getX() + i2));
			s1.setY((leftright) ? (this.getY() - i3) : (this.getY() - i3));
			
			//Small 2
			updown = new Random().nextBoolean();
			leftright = new Random().nextBoolean();
			i2 = new Random().nextInt(spread);
			i3 = new Random().nextInt(spread);
			
			s2.setX((updown) ? (this.getX() - i2) : (this.getX() + i2));
			s2.setY((leftright) ? (this.getY() - i3) : (this.getY() - i3));
			
			//Small 3
			updown = new Random().nextBoolean();
			leftright = new Random().nextBoolean();
			i2 = new Random().nextInt(spread);
			i3 = new Random().nextInt(spread);
			
			s3.setX((updown) ? (this.getX() - i2) : (this.getX() + i2));
			s3.setY((leftright) ? (this.getY() - i3) : (this.getY() - i3));
			
			game.getController().addSmallMeteor(s1);
			game.getController().addSmallMeteor(s2);
			game.getController().addSmallMeteor(s3);
			game.getController().removeMeteor(this);
		}else{
		
			game.getController().removeMeteor(this);
		
		}
	}
	
	@Override
	public void tick(){
		this.setY(this.getY() + speed);
	}

}
