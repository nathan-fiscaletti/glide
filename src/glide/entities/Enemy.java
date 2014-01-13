package glide.entities;

import glide.Glide.Difficulty;
import glide.SinglePlayerGame;
import glide.Glide;

import java.util.Random;


public class Enemy extends Entity{
	public int highSpeed;
	public int lowSpeed;
	public boolean drop;
	private boolean dead;
	public boolean isBomb = false;
	public int lives = 1;
	
	private int worth = 1;
	
	private int plusOrMinus = (new Random().nextBoolean()) ? -1 : 1;
	
	public static enum ProtectorType {
		Normal, Hard, None;
	}
	
	
	public Enemy(double x, double y, SinglePlayerGame game, boolean drop, boolean bomb, boolean isBombProtector, ProtectorType protectorType) {
		super(x, y, game);
		this.drop = drop;
		this.setType(Entity.Type.ENEMY);
		if(bomb){
			this.setEntityImage(game.getTextures().enemy3);
			this.drop = false;
			shootw = 10000000;
			int isGold = (new Random().nextInt(5 - 1 + 1) + 1);
			worth = isGold;
			isBomb = true;
			highSpeed = 1;
			lowSpeed = 1;
			lives = 15;
			game.bsc = -1;
			setX((Glide.WIDTH * Glide.SCALE) / 2 - 32);
			plusOrMinus = 0;
		}else{
			highSpeed = (new Random().nextInt(5 - 3 + 1) + 3);
			if(isBombProtector){
				highSpeed = 1;
				plusOrMinus = 0;
			}
			lowSpeed = 1;
			if(highSpeed == 5){
				this.setEntityImage(game.getTextures().enemy2);
				this.drop = true;
				shootw = 15;
			}else{
				if(protectorType == ProtectorType.Hard){
					this.setEntityImage(game.getTextures().bossprotector);
					this.lives = 2;
				}else{
					this.setEntityImage(game.getTextures().enemy);
				}
			}
		}	
		game.bsc ++;
	}
	
	int shootw = 30;
	int switchupw = 100;
	int deathticks = 0;
	int shoot = 0;
	int switchup = 0;
	@Override
	public void tick(){
		if(isDead()){
			if(deathticks < 5){
				this.setEntityImage(this.game.getTextures().des1);
				deathticks ++;
			}else if(deathticks >= 3 && deathticks < 5){
				this.setEntityImage(this.game.getTextures().des2);
				deathticks ++;
			}else if(deathticks >= 5 && deathticks < 10){
				this.setEntityImage(this.game.getTextures().des3);
				deathticks ++;
			}else if(deathticks == 10){
				game.setScore(game.getScore() + worth);
				this.game.getController().removeEnemy(this);
				if(drop){
						Random r = new Random();
						if(highSpeed == 5){
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
				if(r.nextBoolean() && !((Glide.difficulty == Difficulty.Expert) ? this.getEntityImage().equals(game.getTextures().bossprotector) : false)){
					game.getController().addEnemyBullet(new EnemyBullet(getX(), getY() + 32, game));
				}
				shoot = 0;
			}
			
			int sp = new Random().nextInt(highSpeed - lowSpeed + 1) + lowSpeed;
			
			setY(getY() + sp);
			
			/* Difficulty Definer */
			if(Glide.difficulty == Difficulty.Hard && !isBomb){
				setX(getX() + (1 * plusOrMinus));
			}else if(Glide.difficulty == Difficulty.Expert && !isBomb){
				switchup++;
				if(switchup == switchupw){
					if(new Random().nextBoolean()){
						plusOrMinus = -plusOrMinus;
					}
					switchup = 0;
				}
				setX(getX() + ((sp) * plusOrMinus));
			}
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
