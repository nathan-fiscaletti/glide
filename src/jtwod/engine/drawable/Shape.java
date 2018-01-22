package jtwod.engine.drawable;

import jtwod.engine.Drawable;
import jtwod.engine.Engine;
import jtwod.engine.Screen;
import jtwod.engine.metrics.Dimensions;
import jtwod.engine.metrics.Vector;

import java.awt.*;

/**
 * A base object in the engine.
 *
 * @param <ParentEngine>
 */
public abstract class Shape<ParentEngine extends Engine> extends Drawable<ParentEngine>
{
    /**
     * The position.
     */
    private Vector position;

    /**
     * The bounds.
     */
    private Dimensions size;

    /**
     * The constraint.
     */
    private Vector positionConstraint;

    /**
     * Construct the Shape.
     */
    public Shape(ParentEngine engine)
    {
        super(engine);

        this.position = Vector.Zero();
        this.size = Dimensions.Zero();
        this.positionConstraint = Vector.Max(engine);
    }

    /**
     * Render the object out.
     *
     * @param g
     * @param screen
     */
    @Override
    protected void render(Graphics g, Screen<ParentEngine> screen)
    {
        // Not implemented by default.
    }

    /**
     * Update the object.
     */
    @Override
    protected void update()
    {
        // Not implemented by default.
    }

    /**
     * Perform an update on the object.
     */
    public void performUpdate()
    {
        this.update();
        this.updateConstraints();
    }

    /**
     * Check if this entity is colliding with another.
     *
     * @param entity
     * @return
     */
    public final boolean isCollidingWith(Entity<ParentEngine> entity)
    {
        Rectangle r = new Rectangle();
        r.setBounds(this.position.getX(), this.position.getY(), this.getSize().getWidth(), this.getSize().getHeight());

        Rectangle r2 = new Rectangle();
        r2.setBounds(entity.getPosition().getX(), entity.getPosition().getY(), entity.getSize().getWidth(), entity.getSize().getHeight());

        return (r.intersects(r2));
    }

    /**
     * Move the entity from it's current position to a new position.
     *
     * @param moveTo
     */
    public final void move(Vector moveTo)
    {
        this.position = this.position.plus(moveTo);
    }

    /**
     * Update the position of the object based on it's constraints.
     */
    public final void updateConstraints()
    {
        if (this.position != null && this.positionConstraint != null && ! this.positionConstraint.isZero()) {
            this.position.constrain(this.positionConstraint);
        }
    }

    /**
     * Get the position.
     *
     * @return
     */
    public final Vector getPosition()
    {
        return position;
    }

    /**
     * Update the position.
     *
     * @param position the new position.
     */
    public final void setPosition(Vector position)
    {
        this.position = position;
    }

    /**
     * Get the size.
     *
     * @return
     */
    public final Dimensions getSize()
    {
        return this.size;
    }

    /**
     * Update the size.
     *
     * @param size the new bounds.
     */
    public final void setSize(Dimensions size)
    {
        this.size = size;
    }

    /**
     * Update the position constraint.
     *
     * @param positionConstraint
     */
    public final void setPositionConstraint(Vector positionConstraint)
    {
        this.positionConstraint = positionConstraint;
    }

    /**
     * Retrieves a Shape for the specified engine at the largest size possible.
     *
     * @param engine The engine to pull the Shape from.
     * @return
     */
    public final static <ParentEngine extends Engine> Shape<ParentEngine> MaxSizeBaseObject
    (
        Class<ParentEngine> type,
        Engine engine,
        int buffer
    ) {
        return new Shape<ParentEngine>(type.cast(engine)){
            {
                this.setSize(new Dimensions(engine.getWindowSize().getWidth() + buffer, engine.getWindowSize().getHeight() + buffer));
                this.setPosition(Vector.Zero().plusX(-buffer).plusY(-buffer));
            }
        };
    }
}
