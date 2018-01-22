package jtwod.engine;

import jtwod.engine.drawable.Entity;
import jtwod.engine.drawable.Shape;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

public abstract class EntityController<ParentEngine extends Engine> extends Drawable<ParentEngine> {

    /**
     * A random object for use within the controller.
     */
    private Random random = new Random();

    /**
     * All entities currently being rendered alongside this controller.
     */
    private LinkedList<Entity<ParentEngine>> entities = new LinkedList<Entity<ParentEngine>>();

    /**
     * The screen that this controller is attached to.
     */
    private Screen<ParentEngine> parentScreen;

    /**
     * Create a new EntityController and attach it to the supplied Screen.
     * @param screen
     */
    public EntityController(Screen<ParentEngine> screen)
    {
        super(screen.getParentEngine());
        this.parentScreen = screen;
    }

    /**
     * Handles a control tick.
     */
    protected void runControlTick() {
        // Not implemented by default.
    }

    /**
     * Handles a control tick on a per Entity basis.
     * @param entity
     */
    protected void iterateEntityPerTick(Entity<ParentEngine> entity)
    {
        // Not implemented by default.
    }

    /**
     * Render out all Entities under the scope of this EntityController.
     *
     * @param graphics
     * @param screen
     */
    @Override
    public final void render(Graphics graphics, Screen<ParentEngine> screen)
    {
        for (int entityId = 0; entityId < getAllEntities().size(); entityId++) {
            getAllEntities().get(entityId).render(graphics, screen);
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
            if (
            	! entity.getPosition().isInsideBoundsOf(
            		Shape.MaxSizeBaseObject(
            				entity.getParentEngine().getClass(),
							this.getParentEngine(),
							128
					)
				)
			) {
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
    public final Screen<ParentEngine> getParentScreen()
    {
        return this.parentScreen;
    }

    /**
     * Get the random utility object.
     *
     * @return
     */
    public final Random getRandom()
    {
        return this.random;
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
