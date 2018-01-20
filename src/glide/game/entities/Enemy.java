package glide.game.entities;

import glide.game.Difficulty;
import glide.game.GlideEngine;
import glide.game.GlideTextures;
import glide.game.screens.SinglePlayerGame;
import two.d.engine.Entity;
import two.d.engine.Screen;
import two.d.engine.Vector;

public class Enemy extends Entity<GlideEngine>
{
	public boolean shouldDrop;
	public boolean isDead;
	public boolean isBomb = false;
	public boolean isProtector = false;
	public ProtectorType protectorType;
	
	// The number of lives that this enemy has.
	public int lives = 1;
	
	// How many points you get for killing this enemy.
	protected int worth = 1;
	
	// Direction Control (0 = Straight, -1 = left, 1 = right)
	protected int currentDirection = (random.nextBoolean()) ? -1 : 1;
	protected int ticksSinceLastDirectionChangeAttempt = 0;
	protected int changeDirectrionEveryXTicks = 100;
	
	// Speed properties
	public int highSpeed = 1;
	public int lowSpeed = 1;
	
	// Shooting properties
	protected boolean canFireLaser = true;
	protected int tryToFireLaserEveryXTicks = 30;
	protected int ticksSinceLastLaserFireAttempt = 0;
	
	public static enum ProtectorType {
		Normal, Hard, None;
	}
	
	public Enemy(Vector position, Screen<GlideEngine> screen, boolean shouldDrop, boolean bomb, boolean isBombProtector, ProtectorType protectorType) {
		super(position, screen);
		
		this.shouldDrop = shouldDrop;
		this.protectorType = protectorType;
		this.playDeathAnimation = true;
		
		if (bomb) {
			this.shouldDrop = false;
			this.canFireLaser = false;
			this.worth = 10;
			this.isBomb = true;
			this.lives = 15;
			this.getGame().bsc = -1;
			this.position.x = this.parentEngine.getWidth() / 2 - 32;
			this.currentDirection = 0;
		}else{
			this.highSpeed = (random.nextInt(5 - 3 + 1) + 3);
			if(isBombProtector){
				this.isProtector = true;
				this.highSpeed = 1;
				this.currentDirection = 0;
			}
			if(highSpeed == 5){
				this.shouldDrop = true;
				tryToFireLaserEveryXTicks = 15;
				this.worth = 5;
			}else{
				this.worth = (random.nextInt(4 - 1 + 1) + 1);
				if(protectorType == ProtectorType.Hard){
					this.lives = 2;
				}
			}
		}	
		
		this.getGame().bsc ++;

		// Set up the sprite for the entity.
		this.renderedSprite = Entity.getTextures(GlideTextures.class).enemy;
		if (isBomb) {
			this.renderedSprite = Entity.getTextures(GlideTextures.class).enemy3;
		} else if (highSpeed == 5) {
			this.renderedSprite = Entity.getTextures(GlideTextures.class).enemy2;
		} else if (protectorType == ProtectorType.Hard) {
			this.renderedSprite = Entity.getTextures(GlideTextures.class).bossprotector;
		}
	}
	
	/**
	 * Kills the enemy and provides an enforced drop value.
	 * 
	 * @param shouldDrop
	 */
	public final void kill(boolean shouldDrop){
		this.shouldDrop = shouldDrop;
		this.kill();
	}
	
	@Override
	public final void update()
	{
		if (! this.isDead) {
			this.tryShoot();
			this.updateVelocity();
		}
	}
	
	@Override
	public final void onExitBounds()
	{
		if (this.isBomb) {
			this.getGame().lose();
		}
		
		this.kill();
		this.parentScreen.controller.deSpawnEntity(this);
	}
	
	@Override
	public final void onCollide(Entity<GlideEngine> collidedWith)
	{
		// Handle Meteor collision
		if (collidedWith instanceof Meteor) {
			((Meteor)collidedWith).kill(true);
			
			if (! this.isProtector && ! this.isBomb) {
				this.kill();
			}
		}
	}
	
	@Override
	public final void onDeath()
	{
		if(isBomb){
			this.getGame().stopCircling();
			this.getGame().startCircling(this.position);	
		}
		
		this.getGame().setScore(this.getGame().getScore() + worth);
		if (this.shouldDrop) {
			// Only Gold enemies have a high speed of 5
			if(highSpeed == 5){
				int diamondSpawnDecision = random.nextInt(4);
				switch (diamondSpawnDecision) {
					case 1 :
						this.parentScreen.controller.spawnEntity(new Drop(this.position, this.parentScreen, Drop.Type.DIAMOND));
						break;
					case 2 :
						this.parentScreen.controller.spawnEntity(new Drop(this.position, this.parentScreen, Drop.Type.DIAMOND2));
						break;
					case 3 :
						this.parentScreen.controller.spawnEntity(new Drop(this.position, this.parentScreen, Drop.Type.DIAMOND3));
						break;
					default : 
						this.parentScreen.controller.spawnEntity(new Drop(this.position, this.parentScreen, Drop.Type.DIAMOND));
				}
				return;
			}
			
			// Other enemies have a chance of dropping certain drops.
			// This will only happen if when they are initialized,
			// their 'drop' value is set to 'true'.
			int dropChance = random.nextInt(26);
			if(dropChance < 5){
				this.parentScreen.controller.spawnEntity(new Drop(this.position, this.parentScreen, Drop.Type.HEALTHPACK));
			}else if (dropChance > 5 && dropChance <= 10){
				this.parentScreen.controller.spawnEntity(new Drop(this.position, this.parentScreen, Drop.Type.BEAM));
			}else if(dropChance > 10 && dropChance <= 15){
				this.parentScreen.controller.spawnEntity(new Drop(this.position, this.parentScreen, Drop.Type.PLASMA));
			}else if(dropChance > 15 && dropChance <= 20){
				this.parentScreen.controller.spawnEntity(new Drop(this.position, this.parentScreen, Drop.Type.MDB));
			}else if(dropChance > 23 && dropChance <= 25){
				this.parentScreen.controller.spawnEntity(new Drop(this.position, this.parentScreen, Drop.Type.COD));
			}
		}
	}
	
	private SinglePlayerGame getGame()
	{
		return (SinglePlayerGame)this.parentScreen;
	}
	
	private void updateVelocity()
	{
		int yDeviancy = random.nextInt(highSpeed - lowSpeed + 1) + lowSpeed;;
		int xDeviancy = 0;
		
		if (! this.isBomb) {
			if (this.parentEngine.difficulty == Difficulty.Hard) {
				xDeviancy = 1 * currentDirection;
			} else if (this.parentEngine.difficulty == Difficulty.Expert) {
				this.ticksSinceLastDirectionChangeAttempt++;
				if(this.ticksSinceLastDirectionChangeAttempt == this.changeDirectrionEveryXTicks){
					if(random.nextBoolean()){
						this.currentDirection = -this.currentDirection;
					}
					this.ticksSinceLastDirectionChangeAttempt = 0;
				}
	
				xDeviancy = yDeviancy * currentDirection;
			}
		}
		
		this.velocity = new Vector(xDeviancy, yDeviancy);
	}
	
	private void tryShoot()
	{
		if (this.canFireLaser) {
			this.ticksSinceLastLaserFireAttempt++;
			if(this.ticksSinceLastLaserFireAttempt == this.tryToFireLaserEveryXTicks){
				if(random.nextBoolean() && !((this.parentEngine.difficulty == Difficulty.Expert) ? this.renderedSprite.equals(Entity.getTextures(GlideTextures.class).bossprotector) : false)){
					
					this.parentScreen.controller.spawnEntity(new EnemyBullet(this.position.plusY(32), this.parentScreen, 0));
					
					if (this.parentEngine.difficulty == Difficulty.Expert) {
						if (highSpeed == 5) {
							this.parentScreen.controller.spawnEntity(new EnemyBullet(this.position.plusY(32).plusX(-32), this.parentScreen, 1));
							this.parentScreen.controller.spawnEntity(new EnemyBullet(this.position.plusY(32).plusX(32), this.parentScreen, 2));
						} 
					}
					
					this.parentEngine.sounds.s_shoot.play(this.parentEngine);
				}
				this.ticksSinceLastLaserFireAttempt = 0;
			}
		}
	}
}
