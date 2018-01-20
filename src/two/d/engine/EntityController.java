package two.d.engine;

import java.awt.Canvas;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

public abstract class EntityController<ParentEngine extends Engine> extends Renderer {
	
	/**
	 * A random object for use within the controller.
	 */
	public Random random = new Random();
	
	/**
	 * All entities currently being rendered alongside this controller.
	 */
	private LinkedList<Entity<ParentEngine>> entities = new LinkedList<Entity<ParentEngine>>();
	
	/**
	 * The screen that this controller is attached to. 
	 */
	private Screen<ParentEngine> parentScreen;
	
	/**
	 * The parent engine for this Controller.
	 */
	private ParentEngine parentEngine;
	
	/**
	 * Create a new EntityController and attach it to the supplied Screen.
	 * @param screen
	 */
	public EntityController(Screen<ParentEngine> screen)
	{
		this.parentScreen = screen;
		this.parentEngine = screen.parentEngine;
	}
	
	/**
	 * Handles a control tick.
	 */
	protected abstract void runControlTick();
	
	/**
	 * Handles a control tick on a per Entity basis.
	 * @param entity
	 */
	protected abstract void iterateEntityPerTick(Entity<ParentEngine> entity);
	
	/**
	 * Render out all Entities under the scope of this EntityController.
	 */
	@Override
	public final void render(Graphics graphics, Canvas canvas)
	{
		for (int entityId = 0; entityId < getAllEntities().size(); entityId++) {
			getAllEntities().get(entityId).render(graphics, canvas);
		}
	}
	
	/**
	 * Perform an update.
	 */
	@Override
	public final void update()
	{
		for (int i = 0; i < this.getAllEntities().size(); i++) {
			Entity<ParentEngine> entity = this.getAllEntities().get(i);
			
			// Perform control tick per entity.
			iterateEntityPerTick(entity);
			
			// Check bounds
			if (entity.position.isOutOfBoundsOf(Vector.Max(256, this.parentScreen.parentEngine))) {
				entity.onExitBounds();
			}
			
			// Check Entity Collision
			if (! entity.isDead()) {
				for (int collidingEntityId = 0; collidingEntityId < this.getAllEntities().size(); collidingEntityId++) {
					Entity<ParentEngine> collidingEntity = this.getAllEntities().get(collidingEntityId);
					if (collidingEntity != entity && !collidingEntity.isDead()) {
						if (entity.isCollidingWith(collidingEntity)) {
							entity.onCollide(collidingEntity);
						}
					}
				}
			}
			
			// Perform Heart Beat
			entity.performUpdate();
		}
	}
	
	/**
	 * The screen that this Controller is attached to.
	 * @return
	 */
	public final Screen<ParentEngine> parentScreen()
	{
		return this.parentScreen;
	}
	
	/**
	 * The parent engine for this Controller.
	 *
	 * @return
	 */
	public final ParentEngine parentEngine()
	{
		return this.parentEngine;
	}
	
	/**
	 * Retrieve all Entities attached to this Controller.
	 * @return
	 */
	public final LinkedList<Entity<ParentEngine>> getAllEntities()
	{
		return this.entities;
	}
	
	/**
	 * Spawn an Entity on this Controller.
	 * @param entity
	 */
	public final void spawnEntity(Entity<ParentEngine> entity)
	{
		this.entities.add(entity);
	}
	
	/**
	 * Despawn an Entity from this Controller.
	 * @param entity
	 */
	public final void deSpawnEntity(Entity<ParentEngine> entity)
	{
		this.entities.remove(entity);
	}
	
	/**
	 * Despawn all entities from this Controller.
	 */
	public final void deSpawnAllEntities()
	{
		while(entities.size() > 0)
			entities.remove();
	}
	
}
