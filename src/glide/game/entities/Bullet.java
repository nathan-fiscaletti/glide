package glide.game.entities;

import glide.game.GlideEngine;
import glide.game.GlideTextures;
import two.d.engine.Entity;
import two.d.engine.Screen;
import two.d.engine.Vector;

public class Bullet extends Entity<GlideEngine> {

	private int speed = 20;
	
	/**
	 * Create a new Bullet entity.
	 * 
	 * @param position
	 * @param attachedGame
	 */
	public Bullet(Vector position, Screen<GlideEngine> screen)
	{
		super(position, screen);
		
		// Set the sprite.
		this.renderedSprite = Entity.getTextures(GlideTextures.class).bullet;
		
		// Play the shoot sound.
		this.parentEngine.sounds.s_shoot.play(this.parentEngine);
		
		// Set the entities velocity
		this.velocity = new Vector(0, -speed);
	}
	
	@Override
	public final void onCollide(Entity<GlideEngine> collidedWith)
	{
		// Handle Collision with Meteor
		if (collidedWith instanceof Meteor) {
			this.kill();
			((Meteor)collidedWith).kill(false);
			this.parentEngine.sounds.s_explosion.play(this.parentEngine);
		}
		
		// Handle Collision with Small Meteor
		else if (collidedWith instanceof SmallMeteor) {
			((SmallMeteor)collidedWith).kill();
			this.parentEngine.sounds.s_explosion.play(this.parentEngine);
		}
		
		// Handle collision with Enemy
		else if (collidedWith instanceof Enemy) {
			Enemy enemy = (Enemy)collidedWith;
			if (! enemy.isDead) {
				if (enemy.lives == 1) {
					if (enemy.isBomb) {
						enemy.kill(false);
					} else {
						enemy.kill();
					}
				}else{
					enemy.lives --;
				}
				
				this.kill();
				this.parentEngine.sounds.s_explosion.play(this.parentEngine);
			}
		}
	}
}
