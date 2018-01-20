package glide.engine;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import glide.game.screens.SinglePlayerGame;

/**
 * Parent class for all Entities
 * that get rendered to the screen.
 *
 * @author Nathan Fiscaletti
 */
public abstract class Entity extends Renderer {
	
	/**
	 * The position of the entity.
	 */
	public Vector position;
	
	/**
	 * The constraint for the entitie's position.
	 */
	public Vector positionConstraint;
	
	/**
	 * The velocity of the entity.
	 */
	public Vector velocity;
	
	/**
	 * The Game that the entity is attached to.
	 */
	public SinglePlayerGame attachedGame;
	
	/**
	 * The rendered sprite for the entity.
	 */
	public BufferedImage renderedSprite;
	
	/**
	 * Kill the entity after this many seconds.
	 */
	public int killAfter = 0;
	
	/**
	 * If set to true, the death animation will be played when this entity is killed.
	 */
	public boolean playDeathAnimation = false;
	
	/**
	 * Used to manage the death animation.
	 */
	private int deathTick = 0;
	
	/**
	 * The number of ticks that the entity has been alive.
	 */
	private int lifeLived = 0;
	
	/**
	 * If set to true, the entity will die.
	 */
	private boolean isDead = false;
	
	/**
	 * Some entities make use of a random
	 * value. We provide them the means
	 * to access that through this 
	 * variable definition.
	 */
	protected Random random;
	
	/**
	 * Create a new instance of the Entity Class.
	 *
	 * @param Vector position The initial position.
	 * @param SinglePlayerGame _attachedGame_ The game to attach the entity to.
	 */
	public Entity(Vector position, SinglePlayerGame attachedGame)
	{
		this.position = position;
		this.velocity = Vector.Zero();
		this.positionConstraint = Vector.Zero();
		this.attachedGame = attachedGame;
		this.random = new Random();
	}
	
	/**
	 * Called when the entity is killed.
	 */
	public void onDeath()
	{
		// Not implemented by default.
	}
	
	/**
	 * Called when the entity wants to update it's sprite.
	 */
	public void updateSprite()
	{
		// Not implemented by default.
	}
	
	/**
	 * Called to beat the entities heart.
	 */
	@Override
	public void update()
	{
		// Not implemented by default.
	}
	
	/**
	 * Called when an entity collides with another.
	 * 
	 * @param collidedWith
	 */
	public void onCollide(Entity collidedWith)
	{
		// Not implemented by default.
	}
	
	/**
	 * Called when this entity leaves the render bounds.
	 */
	public void onExitBounds()
	{
		this.kill();
	}
	
	/**
	 * Render the entities sprite out to the screen.
	 *
	 * @param g The graphics object to use for rendering.
	 */
	@Override
	public final void render(Graphics g, Canvas canvas)
	{
		g.drawImage(this.renderedSprite, (int)this.position.x, (int)this.position.y, null);
	}
	
	/**
	 * Perform a heart beat.
	 */
	public final void performUpdate()
	{
		// Update velocity
		if (! this.velocity.isZero()) {
			this.move(velocity);
		}
		
		// Perform the heart beat
		this.update();
		
		// Update the sprite for the entity.
		this.updateSprite();
		
		// Check the remaining life of the entity
		// and kill it if necessary.
		this.checkLife();
		
		// Check if the entity should be killed regardless
		this.runKill();
		
		// Constrain the entity
		if (! this.positionConstraint.isZero()) {
			this.position.constrain(this.positionConstraint);
		}
	}
	
	/**
	 * Move the entity based on a velocity.
	 *
	 * @param moveTo
	 */
	public final void move(Vector moveTo)
	{
		this.position = this.position.plus(moveTo);
	}
	
	/**
	 * Check if this entity is colliding with another.
	 *
	 * @param entity
	 * @return
	 */
	public final boolean isCollidingWith(Entity entity)
	{
		if (entity.renderedSprite == null) {
			System.out.println("Null sprite");
			return false;
		}
		
		Rectangle r = new Rectangle();
		r.setBounds(this.position.x, this.position.y, this.renderedSprite.getWidth(), this.renderedSprite.getHeight());
		
		Rectangle r2 = new Rectangle();
		r2.setBounds(entity.position.x, entity.position.y, entity.renderedSprite.getWidth(), entity.renderedSprite.getHeight());
		
		return (r.intersects(r2));
	}
	
	/**
	 * Tell the entity that it should die.
	 */
	public final void kill()
	{
		this.isDead = true;
		this.velocity = Vector.Zero();
	}
	
	/**
	 * Check if the entity is dead.
	 */
	public final boolean isDead()
	{
		return this.isDead;
	}
	
	/**
	 * Check if we need to die based on our life lived.
	 */
	private void checkLife()
	{
		this.lifeLived++;
		if (this.killAfter > 0) {
			if (this.lifeLived > this.killAfter) {
				this.kill();
			}
		}
	}
	
	/**
	 * Kill the entity.
	 */
	private void runKill()
	{
		if (this.isDead) {
			if (this.playDeathAnimation) {
				if(deathTick < 5){
					this.renderedSprite = this.attachedGame.getTextures().des1;
					deathTick ++;
				}else if(deathTick >= 5 && deathTick < 10){
					this.renderedSprite = this.attachedGame.getTextures().des2;
					deathTick ++;
				}else if(deathTick >= 10 && deathTick < 15){
					this.renderedSprite = this.attachedGame.getTextures().des3;
					deathTick ++;
				}else if(deathTick == 15){
					this.onDeath();
					this.attachedGame.getController().deSpawnEntity(this);
				}
			} else {
				this.onDeath();
				this.attachedGame.getController().deSpawnEntity(this);
			}
		}
	}

}
