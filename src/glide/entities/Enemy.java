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
		if(speed == 5){
			this.setEntityImage(game.getTextures().enemy2);
			this.drop = true;
			shootw = 30;
		}else if(speed == 1){
			Random r2 = new Random();
			int rr = r2.nextInt(10);
			if(rr == 5){
				this.setEntityImage(game.getTextures().enemy3);
				this.drop = false;
				shootw = 10000000;
				isBomb = true;
				lives = 2;
			}else{
				Random r3 = new Random();
				int asdf = r3.nextInt(4 - 2 + 1) + 2;
				speed = asdf;
				this.setEntityImage(game.getTextures().enemy);
			}
		}else{
			this.setEntityImage(game.getTextures().enemy);
		}
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
					boolean go = r.nextBoolean();
					if(go){
						this.game.getController().spawnDrop(this.getX(), this.getY(), Drop.TYPE_HEALTHPACK);
					}else{
						this.game.getController().spawnDrop(this.getX(), this.getY(), Drop.TYPE_BEAM);
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
	

}
