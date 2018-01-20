package glide.entities;

import glide.engine.Entity;
import glide.engine.Vector;
import glide.game.Glide;
import glide.game.screens.SinglePlayerGame;

public class MultiDirectionalBullet extends Entity{
	
	private int speed = 5;
	
	public MultiDirectionalBullet(Vector position, SinglePlayerGame attachedGame, int tofro) {
		super(position, attachedGame);
		this.renderedSprite = this.attachedGame.getTextures().mdbullet;
		
		// Set the velocity for the entity.
		if(tofro == 1){
			this.velocity = this.velocity.plus(speed, speed);
		}else if(tofro == 2){
			this.velocity = this.velocity.plus(speed, -speed);
		}else if(tofro == 3){
			this.velocity = this.velocity.plus(-speed, speed);
		}else if(tofro == 4){
			this.velocity = this.velocity.plus(-speed, -speed);
		}else if(tofro == 5){
			this.velocity = this.velocity.plusX(speed);
		}else if(tofro == 6){
			this.velocity = this.velocity.plusX(-speed);
		}else if(tofro == 7){
			this.velocity = this.velocity.plusY(speed);
		}else if(tofro == 8){
			this.velocity = this.velocity.plusY(-speed);
		}
	}
	
	@Override
	public final void onCollide(Entity collidedWith)
	{
		// Handle Collision with Meteor
		if (collidedWith instanceof Meteor) {
			((Meteor)collidedWith).kill(false);
			Glide.s_explosion.play();
		} 
		
		// Handle Collision with Small Meteor
		else if (collidedWith instanceof SmallMeteor) {
			((SmallMeteor)collidedWith).kill();
			Glide.s_explosion.play();
		}
		
		// Handle Collision with Enemy
		else if (collidedWith instanceof Enemy) {
			Enemy enemy = (Enemy)collidedWith;
			if (! enemy.isDead) {
				if (enemy.isBomb) {
					enemy.kill(false);
				} else {
					enemy.kill();
				}
				
				Glide.s_explosion.play();
			}
		}
	}
}
