package glide.entities;

import glide.Game;
import glide.Glide;

public class Player extends Entity{
	private double velX = 0;
	private double velY = 0;
	private boolean shooting = false;
	private boolean beaming = false;

	public Player(double x, double y, Game game){
		super(x, y, game);
		this.setType(Entity.TYPE_PLAYER);
		this.setEntityImage(game.getTextures().player);
	}
	
	int beamtick = 0;
	@Override
	public void tick(){
		this.setY(this.getY()+velY);
		this.setX(this.getX()+velX);

		if(isBeaming()){
			if(!Glide.game.isPaused()){
				Glide.game.getController().addBullet(new Bullet(getX(), getY() - 32, Glide.game));
				beamtick ++;
				if(beamtick > 420){
					setBeaming(false);
					beamtick = 0;
				}
			}
		}
		
		if(this.getX() < 0)
			this.setX(0);
		if(this.getX() >= 640 - 22)
			this.setX(640 - 22);
		if(this.getY() <= 0)
			this.setY(0);
		if(this.getY() >= 480 - 32)
			this.setY(480 - 32);
	}
	
	
	public void setVelocityY(double y){
		this.velY = y;
	}
	
	public void setVelocityX(double x){
		this.velX = x;
	}
	
	public boolean isShooting() {
		return shooting;
	}

	public void setShooting(boolean shooting) {
		this.shooting = shooting;
	}


	public boolean isBeaming() {
		return beaming;
	}


	public void setBeaming(boolean beam) {
		this.beaming = beam;
	}
}
