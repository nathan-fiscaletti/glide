package glide.entities;

import glide.engine.Entity;
import glide.engine.Vector;
import glide.game.Glide;
import glide.game.Glide.Difficulty;
import glide.game.screens.SinglePlayerGame;

public class Meteor extends Entity{
	protected int speed = 1 + (int)(Math.random()*3);
	protected int xVelocityMultiplier = (this.random.nextBoolean()) ? -1 : 1;
	protected int meteorType = 1;
	protected int changeDirectionTick = 0;
	protected boolean forceSmall = false;
	
	public Meteor(Vector position, SinglePlayerGame attachedGame) {
		super(position, attachedGame);
		meteorType = random.nextInt(7);
		
		this.velocity = this.velocity.plusY(speed);
		
		// Should the entity be small or large?
		// When we want it to be small, we simply 
		// kill this meteor which will spawn
		// a group of smaller ones.
		if(!random.nextBoolean()) {
			this.kill(true);
		} else {
			// Tell the meteor to play the death animation when it dies.
			this.playDeathAnimation = true;
			
			// Initialize the sprite for the entity.
			switch(meteorType) {
				case 1 : this.renderedSprite = this.attachedGame.getTextures().meteor1; break;
				case 2 : this.renderedSprite = this.attachedGame.getTextures().meteor2; break;
				case 3 : this.renderedSprite = this.attachedGame.getTextures().meteor3; break;
				case 4 : this.renderedSprite = this.attachedGame.getTextures().meteor4; break;
				case 5 : this.renderedSprite = this.attachedGame.getTextures().meteor5; break;
				case 6 : this.renderedSprite = this.attachedGame.getTextures().meteor6; break;
				
				default: this.renderedSprite = this.attachedGame.getTextures().meteor1;
			}
		}
	}
	
	@Override
	public final void onCollide(Entity collidedWith)
	{
		// Handle a Meteor colliding with another meteor.
		if (collidedWith instanceof Meteor) {
			if ((Meteor)collidedWith != this && ! collidedWith.isDead()) {
				this.kill(true);
				((Meteor)collidedWith).kill(true);
				Glide.s_explosion.play();
			}
		}
	}
	
	@Override
	public final void update()
	{
		// If the difficulty is anything besides EASY, 
		// move the meteor left or right as well.
		if(Glide.difficulty != Difficulty.Easy){
			
			// Move the meteor either left or right.
			this.velocity.x = speed*xVelocityMultiplier;
			
			// If you set the difficulty to expert, the meteors
			// should change their X velocity randomly every 60 ticks.
			if (Glide.difficulty == Difficulty.Expert) {
				
				// After every 60 ticks, we need to
				// change the left or right toggle.
				if (this.changeDirectionTick == 60) {
					this.xVelocityMultiplier = (this.random.nextBoolean()) ? -1 : 1;
				}
				
				// Update how many ticks it's been
				// since we last changed the
				// left or right toggle.
				this.changeDirectionTick += (this.changeDirectionTick == 60) ? -60 : 1;
			}
		}
	}
	
	@Override
	public final void onDeath()
	{
		// Generate the child meteors when a meteor is destroyed
		if(this.random.nextBoolean() || this.forceSmall){
			
			// Generate between 0 and 5 meteors
			int smallMeteorCount = this.random.nextInt(5);
			
			// Give the small meteors a spread of 70 pixels
			int spread = 70;
			for (int i = 0;i<smallMeteorCount;i++) {
				SmallMeteor small_meteor = new SmallMeteor(this.position, this.attachedGame);
				small_meteor.position = small_meteor.position.plusX((this.random.nextBoolean() ? -this.random.nextInt(spread) : +this.random.nextInt(spread)));
				small_meteor.position = small_meteor.position.plusY((this.random.nextBoolean() ? -this.random.nextInt(spread) : +this.random.nextInt(spread)));
				this.attachedGame.getController().spawnEntity(small_meteor);
			}
		}
	}
	
	public final void kill(boolean forceSmall){
		this.forceSmall = true;
		this.kill();
	}

}
