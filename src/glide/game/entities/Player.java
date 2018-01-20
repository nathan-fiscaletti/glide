package glide.game.entities;

import glide.engine.Entity;
import glide.engine.Screen;
import glide.engine.Vector;
import glide.game.Glide;
import glide.game.screens.SinglePlayerGame;

public class Player extends Entity{
	public boolean shooting = false;
	public boolean beaming = false;
	public boolean plasma = false;
	public boolean hurting = false;
	public boolean t = true;
	public boolean t2 = true;
	
	public static final int normalSpeed = 8;
	public static final int beemSpeed = 15;
	public static final int boostSpeed = 15;
	
	public int speed = Player.normalSpeed;
	
	int beamtick = 0;
	int hurttick = 0;
	int plasmatick = 0;
	
	public Player(Vector position, Screen screen){
		super(position, screen);
		this.renderedSprite = Entity.getTextures().player;
		this.positionConstraint = Vector.Max().plusX(-this.renderedSprite.getWidth());
	}
	
	@Override
	public final void updateSprite()
	{
		if (this.beaming) {
			this.renderedSprite = Entity.getTextures().player2;
		} else if (!t) {
			this.renderedSprite = Entity.getTextures().player;
		}
		
		if (hurttick < 10 && this.hurting) {
			this.renderedSprite = Entity.getTextures().playerhurt;
		} else if (!t2) {
			if(!this.beaming){
				this.renderedSprite = Entity.getTextures().player;
			}else{
				this.renderedSprite = Entity.getTextures().player2;
			}
		}
	}
	
	@Override
	public final void onCollide(Entity collidedWith)
	{
		
		// Handle Collision with Enemy
		if (collidedWith instanceof Enemy) {
			Enemy enemy = (Enemy)collidedWith;
			if (! enemy.isDead) {
				if(!this.plasma && !Glide.health_cheat){
					int h = (this.getGame().getHealthBar().health > 1) ? this.getGame().getHealthBar().health - 1 : 8;
					
					if(h == 8){
						this.getGame().lose();
					}else{
						if(enemy.isBomb){
							h = 8;
							this.getGame().lose();
						} else {
							enemy.kill();
						}
						Glide.s_explosion.play();
						
						this.getGame().getHealthBar().health = h;
						this.hurting = true;
					}
					Glide.s_hurt.play();
				}else{
					if(enemy.isBomb){
						enemy.kill(false);
					} else {
						enemy.kill();
					}
					
					Glide.s_explosion.play();
				}
			}
		}
		
		// Handle Collision with Drop
		else if (collidedWith instanceof Drop) {
			Drop drop = (Drop)collidedWith;
			if (! drop.isDead) {
				if (drop.dropType == Drop.Type.HEALTHPACK) {
					this.getGame().getHealthBar().health += 1;
				} else if(drop.dropType == Drop.Type.BEAM) {
					this.setBeaming(true);
				} else if(drop.dropType == Drop.Type.DIAMOND) {
					this.getGame().setScore(this.getGame().getScore() + 15);
				} else if(drop.dropType == Drop.Type.DIAMOND2) {
					this.getGame().getHealthBar().health = 8;
				} else if(drop.dropType == Drop.Type.DIAMOND3) {
					this.getGame().mdbs = 5;
				} else if(drop.dropType == Drop.Type.PLASMA) {
					this.setPlasma(true);
					this.getGame().isPlasmaActive = true;
				} else if(drop.dropType == Drop.Type.MDB) {
					if(this.getGame().mdbs < 5){
						this.getGame().mdbs++;
					}
				} else if(drop.dropType == Drop.Type.COD) {
					if (this.getGame().cods < this.getGame().max_cods) {
						this.getGame().cods ++;
					}
				}
				
				drop.kill();
				Glide.s_pickup.play();
			}
		}
		
		// Handle Collision with Meteor
		else if (collidedWith instanceof Meteor || collidedWith instanceof SmallMeteor) {
			this.parentScreen.controller.deSpawnEntity(collidedWith);
			
			if(!this.plasma && !Glide.health_cheat){
				int h = (this.getGame().getHealthBar().health > 1) ? this.getGame().getHealthBar().health - 1 : 8;
				if(h == 8){
					this.getGame().lose();
				}else{
					this.getGame().getHealthBar().health = h;
					this.hurting = true;
					Glide.s_hurt.play();
				}
			}else{
				Glide.s_explosion.play();
			}
		}
	}
	
	@Override
	public final void update()
	{
		if(this.beaming){
			if(!this.getGame().isPaused()  && !this.getGame().lost() && !this.getGame().won()){
				speed = Player.beemSpeed;
				this.parentScreen.controller.spawnEntity(new Bullet(this.position.plusY(-32), this.parentScreen));
				beamtick ++;
				if(beamtick == 60){
					this.getGame().beam = 4;
				}else if(beamtick == 120){
					this.getGame().beam = 3;
				}else if (beamtick == 180){
					this.getGame().beam = 2;
				}else if (beamtick == 240){
					this.getGame().beam = 1;
				}else if(beamtick == 300){
					speed = Player.normalSpeed;
					setBeaming(false);
					beamtick = 0;
					this.getGame().beam = 0;
				}
			}
			t = false;
		}else if(!t){
			t = true;
		}
		
		if(this.plasma){
			if(!this.getGame().isPaused() && !this.getGame().lost() && !this.getGame().won()){
				plasmatick++;
				
				if(plasmatick == 60){
					this.getGame().shield = 4;
					this.getGame().isPlasmaActive = true;
				}else if(plasmatick == 120){
					this.getGame().shield = 3;
					this.getGame().isPlasmaActive = true;
				}else if (plasmatick == 180){
					this.getGame().shield = 2;
					this.getGame().isPlasmaActive = true;
				}else if (plasmatick == 240){
					this.getGame().shield = 1;
					this.getGame().isPlasmaActive = true;
				}else if(plasmatick == 300){
					setPlasma(false);
					this.getGame().isPlasmaActive = false;
					plasmatick = 0;
					this.getGame().shield = 0;
				}
			}
		}
	
		
		if(hurttick < 10 && this.hurting){
			t2 = false;
			hurttick ++;
		}else if(!t2){
			hurttick = 0;
			t2 = true;
			this.hurting = false;
		}
	}
	
	public final void setBeaming(boolean beam) {
		this.beaming = beam;
		this.getGame().beam = (beam) ? 5 : 0;
		if(beam){
			this.beamtick = 0;
		}
	}

	public final void setPlasma(boolean plasma) {
		this.plasma = plasma;
		this.getGame().shield = (plasma) ? 5 : 0;
		if(plasma){
			this.plasmatick = 0;
		}
	}
	
	private SinglePlayerGame getGame()
	{
		return (SinglePlayerGame)this.parentScreen;
	}
}
