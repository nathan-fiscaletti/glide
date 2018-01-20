package glide.engine;

import java.awt.Canvas;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

public abstract class EntityController extends Renderer {
	
	/**
	 * A random object for use within the controller.
	 */
	public Random random = new Random();
	
	/**
	 * All entities currently being rendered alongside this controller.
	 */
	private LinkedList<Entity> entities = new LinkedList<Entity>();
	
	/**
	 * The screen that this controller is attached to. 
	 */
	private Screen parentScreen;
	
	/**
	 * Create a new EntityController and attach it to the supplied Screen.
	 * @param screen
	 */
	public EntityController(Screen screen)
	{
		this.parentScreen = screen;
	}
	
	/**
	 * Handles a control tick.
	 */
	protected abstract void runControlTick();
	
	/**
	 * Handles a control tick on a per Entity basis.
	 * @param entity
	 */
	protected abstract void iterateEntityPerTick(Entity entity);
	
	/**
	 * Render out all Entities under the scope of this EntityController.
	 */
	@Override
	public final void render(Graphics g, Canvas canvas)
	{
		for (int entityId = 0; entityId < getAllEntities().size(); entityId++) {
			getAllEntities().get(entityId).render(g, canvas);
		}
	}
	
	/**
	 * Perform an update.
	 */
	@Override
	public final void update()
	{
		for (int i = 0; i < this.getAllEntities().size(); i++) {
			Entity entity = this.getAllEntities().get(i);
			
			// Perform control tick per entity.
			iterateEntityPerTick(entity);
			
			// Check bounds
			if (entity.position.isOutOfBoundsOf(Vector.Max(256))) {
				entity.onExitBounds();
			}
			
			// Check Entity Collision
			if (! entity.isDead()) {
				for (int collidingEntityId = 0; collidingEntityId < this.getAllEntities().size(); collidingEntityId++) {
					Entity collidingEntity = this.getAllEntities().get(collidingEntityId);
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
	public final Screen parentScreen()
	{
		return this.parentScreen;
	}
	
	/**
	 * Retrieve all Entities attached to this Controller.
	 * @return
	 */
	public final LinkedList<Entity> getAllEntities()
	{
		return this.entities;
	}
	
	/**
	 * Spawn an Entity on this Controller.
	 * @param entity
	 */
	public final void spawnEntity(Entity entity)
	{
		this.entities.add(entity);
	}
	
	/**
	 * Despawn an Entity from this Controller.
	 * @param entity
	 */
	public final void deSpawnEntity(Entity entity)
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
