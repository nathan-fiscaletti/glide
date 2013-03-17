package glide.entities;

import glide.Game;
import glide.Glide;

import java.util.Random;


public class Enemy extends Entity{
	public int speed;
	public boolean drop;
	private boolean dead;
	public Enemy(double x, double y, Game game, boolean drop) {
		super(x, y, game);
		this.drop = drop;
		this.setType(Entity.TYPE_ENEMY);
		Random r = new Random();
		speed = r.nextInt(5 - 1 + 1) + 1;
		if(speed == 5){
			this.setEntityImage(game.getTextures().enemy2);
			this.drop = true;
		}else{
			this.setEntityImage(game.getTextures().enemy);
		}
	}
	
	int deathticks = 0;
	@Override
	public void tick(){
		if(isDead()){
			if(deathticks < 5){
				this.setEntityImage(Glide.game.getTextures().des1);
				deathticks ++;
			}else if(deathticks >= 3 && deathticks < 5){
				this.setEntityImage(Glide.game.getTextures().des2);
				deathticks ++;
			}else if(deathticks >= 5 && deathticks < 10){
				this.setEntityImage(Glide.game.getTextures().des3);
				deathticks ++;
			}else if(deathticks == 10){
				game.setScore(game.getScore() + speed);
				Glide.game.getController().removeEnemy(this);
				if(drop){
					Random r = new Random();
					int go = r.nextInt(3);
					if(speed == 5){
						this.game.getController().spawnDrop(this.getX(), this.getY(), Drop.TYPE_DIAMOND);
						return;
					}
					if(go == 1){
						this.game.getController().spawnDrop(this.getX(), this.getY(), Drop.TYPE_HEALTHPACK);
					}else if(go == 2){
						this.game.getController().spawnDrop(this.getX(), this.getY(), Drop.TYPE_BEAM);
					}
				}
			}
		}else{
			setY(getY() + speed);
		}
		
	}

	public void die(){
		setDead(true);
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}
	

}
