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
	int shootw = 30;
	public Enemy(double x, double y, Game game, boolean drop, boolean bomb) {
		super(x, y, game);
		this.drop = drop;
		this.setType(Entity.Type.ENEMY);
		Random r = new Random();
		speed = r.nextInt(5 - 1 + 1) + 1;
		if(bomb){
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
				shootw = 15;
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
							Random dia = new Random();
							int diacatch = dia.nextInt(4);
							if(diacatch == 1){
								this.game.getController().spawnDrop(this.getX(), this.getY(), Entity.Type.DIAMOND);
							}else if(diacatch == 2){
								this.game.getController().spawnDrop(this.getX(), this.getY(), Entity.Type.DIAMOND2);
							}else if(diacatch == 3){
								this.game.getController().spawnDrop(this.getX(), this.getY(), Entity.Type.DIAMOND3);
							}else{
								this.game.getController().spawnDrop(this.getX(), this.getY(), Entity.Type.DIAMOND);
							}
							return;
						}
						int go = r.nextInt(26);
						if(go < 5){
							this.game.getController().spawnDrop(this.getX(), this.getY(),Entity.Type.HEALTHPACK);
						}else if (go > 5 && go <= 10){
							this.game.getController().spawnDrop(this.getX(), this.getY(), Entity.Type.BEAM);
						}else if(go > 10 && go <= 15){
							this.game.getController().spawnDrop(this.getX(), this.getY(), Entity.Type.PLASMA);
						}else if(go > 15 && go <= 20){
							this.game.getController().spawnDrop(this.getX(), this.getY(), Entity.Type.MDB);
						}else if(go > 23 && go <= 25){
							//Rarest Drop
							this.game.getController().spawnDrop(this.getX(), this.getY(), Entity.Type.COD);
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
