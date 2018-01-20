package glide.game.entities;

import glide.engine.Entity;
import glide.engine.Screen;
import glide.engine.Vector;
import glide.game.Glide;

public class Bullet extends Entity {
	
	private int speed = 20;
	
	/**
	 * Create a new Bullet entity.
	 * 
	 * @param position
	 * @param attachedGame
	 */
	public Bullet(Vector position, Screen screen)
	{
		super(position, screen);
		
		// Set the sprite.
		this.renderedSprite = Entity.getTextures().bullet;
		
		// Play the shoot sound.
		Glide.s_shoot.play();
		
		// Set the entities velocity
		this.velocity = new Vector(0, -speed);
	}
	
	@Override
	public final void onCollide(Entity collidedWith)
	{
		// Handle Collision with Meteor
		if (collidedWith instanceof Meteor) {
			this.kill();
			((Meteor)collidedWith).kill(false);
			Glide.s_explosion.play();
		}
		
		// Handle Collision with Small Meteor
		else if (collidedWith instanceof SmallMeteor) {
			((SmallMeteor)collidedWith).kill();
			Glide.s_explosion.play();
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
				Glide.s_explosion.play();
			}
		}
	}
}
