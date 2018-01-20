package glide.engine;

import java.awt.Canvas;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

public abstract class EntityController extends Renderer {
	
	public Random random = new Random();
	private LinkedList<Entity> entities = new LinkedList<Entity>();
	private Screen parentScreen;
	
	public EntityController(Screen screen)
	{
		this.parentScreen = screen;
	}
	
	protected abstract void runControlTick();
	protected abstract void iterateEntityPerTick(Entity entity);
	
	@Override
	public final void render(Graphics g, Canvas canvas)
	{
		for (int entityId = 0; entityId < getAllEntities().size(); entityId++) {
			getAllEntities().get(entityId).render(g, canvas);
		}
	}
	
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
	
	public final Screen parentScreen()
	{
		return this.parentScreen;
	}
	
	public final LinkedList<Entity> getAllEntities()
	{
		return this.entities;
	}
	
	public final void spawnEntity(Entity entity)
	{
		this.entities.add(entity);
	}
	
	public final void deSpawnEntity(Entity entity)
	{
		this.entities.remove(entity);
	}
	
	public final void deSpawnAllEntities()
	{
		while(entities.size() > 0)
			entities.remove();
	}
	
}
