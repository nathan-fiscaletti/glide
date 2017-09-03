package glide.entities;

import java.awt.image.BufferedImage;

import glide.SinglePlayerGame;

public class MultiDirectionalBullet extends Entity{
	
	public int tofro;
	public int speed = 5;
	
	public MultiDirectionalBullet(double x, double y, SinglePlayerGame game, int tofro) {
		super(x, y, game);
		this.setType(Entity.Type.MULTIDIRECTIONALBULLET);
		this.tofro = tofro;
	}
	
	@Override
	public BufferedImage getEntityImage()
	{
		return game.getTextures().mdbullet;
	}
	
	@Override
	public void tick(){
		if(this.tofro == 1){
			this.setX(this.getX() + speed);
			this.setY(this.getY() + speed);
		}else if(this.tofro == 2){
			this.setX(this.getX() + speed);
			this.setY(this.getY() - speed);
		}else if(this.tofro == 3){
			this.setX(this.getX() - speed);
			this.setY(this.getY() + speed);
		}else if(this.tofro == 4){
			this.setX(this.getX() - speed);
			this.setY(this.getY() - speed);
		}else if(this.tofro == 5){
			this.setX(this.getX() + speed);
		}else if(this.tofro == 6){
			this.setX(this.getX() - speed);
		}else if(this.tofro == 7){
			this.setY(this.getY() + speed);
		}else if(this.tofro == 8){
			this.setY(this.getY() - speed);
		}
	}

}
