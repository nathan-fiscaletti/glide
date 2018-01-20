package glide.game.entities;

import glide.game.GlideEngine;
import glide.game.GlideTextures;
import two.d.engine.Entity;
import two.d.engine.Screen;
import two.d.engine.Vector;

public class MultiDirectionalBullet extends Entity<GlideEngine>
{
	
	private int speed = 5;
	
	public MultiDirectionalBullet(Vector position, Screen<GlideEngine> screen, int tofro) {
		super(position, screen);
		this.renderedSprite = Entity.getTextures(GlideTextures.class).mdbullet;
		
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
	public final void onCollide(Entity<GlideEngine> collidedWith)
	{
		// Handle Collision with Meteor
		if (collidedWith instanceof Meteor) {
			((Meteor)collidedWith).kill(false);
			this.parentEngine.sounds.s_explosion.play(this.parentEngine);
		} 
		
		// Handle Collision with Small Meteor
		else if (collidedWith instanceof SmallMeteor) {
			((SmallMeteor)collidedWith).kill();
			this.parentEngine.sounds.s_explosion.play(this.parentEngine);
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
				
				this.parentEngine.sounds.s_explosion.play(this.parentEngine);
			}
		}
	}
}
