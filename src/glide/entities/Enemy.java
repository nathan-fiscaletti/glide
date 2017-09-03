package glide.entities;

import glide.Glide.Difficulty;
import glide.SinglePlayerGame;

import java.awt.image.BufferedImage;

import glide.Glide;


public class Enemy extends Entity{
	public int highSpeed;
	public int lowSpeed;
	public boolean drop;
	private boolean dead;
	public boolean isBomb = false;
	private boolean isProtector = false;
	private ProtectorType protectorType;
	public int lives = 1;
	
	private int worth = 1;
	
	private int plusOrMinus = (random.nextBoolean()) ? -1 : 1;
	
	public static enum ProtectorType {
		Normal, Hard, None;
	}
	
	
	public Enemy(double x, double y, SinglePlayerGame game, boolean drop, boolean bomb, boolean isBombProtector, ProtectorType protectorType) {
		super(x, y, game);
		this.drop = drop;
		this.setType(Entity.Type.ENEMY);
		this.protectorType = protectorType;
		if(bomb){
			this.drop = false;
			shootw = 10000000;
			int isGold = (random.nextInt(5 - 1 + 1) + 1);
			worth = isGold;
			isBomb = true;
			highSpeed = 1;
			lowSpeed = 1;
			lives = 15;
			game.bsc = -1;
			setX((Glide.WIDTH * Glide.SCALE) / 2 - 32);
			plusOrMinus = 0;
		}else{
			highSpeed = (random.nextInt(5 - 3 + 1) + 3);
			if(isBombProtector){
				this.isProtector = true;
				highSpeed = 1;
				plusOrMinus = 0;
			}
			lowSpeed = 1;
			if(highSpeed == 5){
				this.drop = true;
				shootw = 15;
			}else{
				if(protectorType == ProtectorType.Hard){
					this.lives = 2;
				}
			}
		}	
		game.bsc ++;
	}
	
	@Override
	public BufferedImage getEntityImage()
	{
		if (isBomb) {
			return game.getTextures().enemy3;
		}
		
		if (highSpeed == 5) {
			return game.getTextures().enemy2;
		}
		
		if (protectorType == ProtectorType.Hard) {
			return game.getTextures().bossprotector;
		}
		
		return game.getTextures().enemy;
	}
	
	int shootw = 30;
	int switchupw = 100;
	int deathticks = 0;
	int shoot = 0;
	int switchup = 0;
	@Override
	public void tick(){
		if(isDead()){
			// TODO: Implement death animation
			/**
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
			**/
				game.setScore(game.getScore() + worth);
				this.game.getController().removeEnemy(this);
				if(drop){
						if(highSpeed == 5){
							int diacatch = random.nextInt(4);
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
						int go = random.nextInt(26);
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
				
			/**}**/
		}else{
			shoot ++;
			if(shoot == shootw){
				if(random.nextBoolean() && !((Glide.difficulty == Difficulty.Expert) ? this.getEntityImage().equals(game.getTextures().bossprotector) : false)){
					game.getController().addEnemyBullet(new EnemyBullet(getX(), getY() + 32, game));
				}
				shoot = 0;
			}
			
			int sp = random.nextInt(highSpeed - lowSpeed + 1) + lowSpeed;
			
			setY(getY() + sp);
			
			/* Difficulty Definer */
			if(Glide.difficulty == Difficulty.Hard && !isBomb){
				setX(getX() + (1 * plusOrMinus));
			}else if(Glide.difficulty == Difficulty.Expert && !isBomb){
				switchup++;
				if(switchup == switchupw){
					if(random.nextBoolean()){
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
		if(isBomb){
			game.stopCircling();
			game.startCircling((float)this.getX(), (float)this.getY());	
		}
	}

	public boolean isDead() {
		return dead;
	}

	public boolean isProtector() {
		return isProtector;
	}


	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public void blowUpBomb() {
		
	}
	

}
