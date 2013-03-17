package glide.entities;

import glide.Game;
import glide.Glide;

public class Player extends Entity{
	private double velX = 0;
	private double velY = 0;
	private boolean shooting = false;
	private boolean beaming = false;
	private boolean hurting = false;
	private boolean t = true;
	private boolean t2 = true;

	public Player(double x, double y, Game game){
		super(x, y, game);
		this.setType(Entity.TYPE_PLAYER);
		this.setEntityImage(game.getTextures().player);
	}
	
	int beamtick = 0;
	int hurttick = 0;
	@Override
	public void tick(){
		this.setY(this.getY()+velY);
		this.setX(this.getX()+velX);

		if(isBeaming()){
			if(!Glide.game.isPaused()  && !Glide.game.lost()){
				Glide.game.getController().addBullet(new Bullet(getX(), getY() - 32, Glide.game));
				beamtick ++;
				if(beamtick > 420){
					setBeaming(false);
					beamtick = 0;
				}
			}
			setEntityImage(game.getTextures().player2);
			t = false;
		}else if(!t){
			setEntityImage(game.getTextures().player);
			t = true;
		}
		
		if(hurttick < 10 && isHurting()){
			setEntityImage(game.getTextures().playerhurt);
			t2 = false;
			hurttick ++;
		}else if(!t2){
			hurttick = 0;
			if(!isBeaming()){
				setEntityImage(game.getTextures().player);
			}else{
				setEntityImage(game.getTextures().player2);
			}
			t2 = true;
			setHurting(false);
		}
		
		if(this.getX() < 0)
			this.setX(0);
		if(this.getX() >= (Glide.WIDTH * Glide.SCALE) - 22)
			this.setX((Glide.WIDTH * Glide.SCALE) - 22);
		if(this.getY() <= 0)
			this.setY(0);
		if(this.getY() >= (Glide.HEIGHT * Glide.SCALE) - 32)
			this.setY((Glide.HEIGHT * Glide.SCALE) - 32);
	}
	
	public void hurt(){
		setHurting(true);
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

	public boolean isHurting() {
		return hurting;
	}

	public void setHurting(boolean hurting) {
		this.hurting = hurting;
	}
}
