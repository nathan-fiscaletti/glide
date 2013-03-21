package glide.entities;

import glide.Game;
import glide.Glide;

import java.util.Random;


public class Enemy extends Entity{
	public int speed;
	public boolean drop;
	private boolean dead;
	public boolean isBomb = false;
	public int lives = 1;
	int shootw = 120;
	public Enemy(double x, double y, Game game, boolean drop) {
		super(x, y, game);
		this.drop = drop;
		this.setType(Entity.TYPE_ENEMY);
		Random r = new Random();
		speed = r.nextInt(5 - 1 + 1) + 1;
		if(game.bsc == 50){
			this.setEntityImage(game.getTextures().enemy3);
			this.drop = false;
			shootw = 10000000;
			isBomb = true;
			speed = 1;
			lives = 15;
			game.bsc = -1;
			setX((Glide.WIDTH * Glide.SCALE) / 2 - 32);
		}else{
			if(speed == 5){
				this.setEntityImage(game.getTextures().enemy2);
				this.drop = true;
				shootw = 30;
			}else{
				this.setEntityImage(game.getTextures().enemy);
			}
		}	
		game.bsc ++;
	}
	
	int deathticks = 0;
	int shoot = 0;
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
					if(speed == 5){
						this.game.getController().spawnDrop(this.getX(), this.getY(), Drop.TYPE_DIAMOND);
						return;
					}
					int go = r.nextInt(4);
					if(go == 1){
						this.game.getController().spawnDrop(this.getX(), this.getY(), Drop.TYPE_HEALTHPACK);
					}else if (go == 2){
						this.game.getController().spawnDrop(this.getX(), this.getY(), Drop.TYPE_BEAM);
					}else if(go == 3){
						this.game.getController().spawnDrop(this.getX(), this.getY(), Drop.TYPE_PLASMA);
					}
				}
			}
		}else{
			shoot ++;
			if(shoot == shootw){
				Random r = new Random();
				if(r.nextBoolean()){
					game.getController().addEnemyBullet(new EnemyBullet(getX(), getY() + 32, game));
				}
				shoot = 0;
			}
			
			setY(getY() + speed);
		}
		
	}

	public void die(boolean in){
		drop = in;
		setDead(true);
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

	public void blowUpBomb() {
		//Blow up the bomb		
	}
	

}
