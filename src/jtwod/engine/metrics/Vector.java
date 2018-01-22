package jtwod.engine.metrics;

import jtwod.engine.drawable.Shape;
import jtwod.engine.Engine;

public final class Vector {

    /**
     * The X value.
     */
    private int x;

    /**
     * The Y value.
     */
    private int y;

    /**
     * The buffer for the vector.
     */
    public int buffer = 0;

    /**
     * Create a new base vector.
     */
    public Vector()
    {
        this.x = 0;
        this.y = 0;
    }

    /**
     * Create a new vector with the supplied X and Y values.
     *
     * @param x The X value.
     * @param y The Y value.
     */
    public Vector(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Constrain this vector to a specific width and height.
     *
     * @param constraint
     */
    public final void constrain(Vector constraint)
    {
        if (this.x < 0) {
            this.x = 0;
        }

        if (this.y < 0) {
            this.y = 0;
        }

        if (this.x > constraint.x) {
            this.x = constraint.x;
        }

        if (this.y > constraint.y) {
            this.y = constraint.y;
        }
    }

    /**
     * Set the X value.
     *
     * @param x
     * @return
     */
    public final Vector setX(int x)
    {
        this.x = x;
        return this;
    }

    /**
     * Set the Y value.
     *
     * @param y
     * @return
     */
    public final Vector setY(int y)
    {
        this.y = y;
        return this;
    }

    /**
     * Retrieve the X value.
     *
     * @return
     */
    public final int getX()
    {
        return this.x;
    }

    /**
     * Retrieve the X value.
     *
     * @return
     */
    public final int getY()
    {
        return this.y;
    }


    /**
     * Add to the Y value to create a new Vector.
     *
     * @param plus The value to add.
     *
     * @return Vector
     */
    public final Vector plusY(int plus) {
        return new Vector(this.x, this.y + plus);
    }

    /**
     * Add to the X value to create a new Vector.
     *
     * @param plus The value to add.
     *
     * @return Vector
     */
    public final Vector plusX(int plus) {
        return new Vector(this.x + plus, this.y);
    }

    /**
     * Add to the X and Y values to create a new Vector.
     *
     * @param x The value to add to X.
     * @param y The value to add to Y.
     *
     * @return Vector
     */
    public final Vector plus(int x, int y) {
        return new Vector(this.x + x, this.y + y);
    }

    /**
     * Add on Vector to another Vector to create a new Vector.
     *
     * @param velocity The Vector to add to this Vector.
     *
     * @return Vector
     */
    public final Vector plus(Vector velocity) {
        return new Vector(this.x + velocity.x, this.y + velocity.y);
    }

    /**
     * Check if this vector is zero.
     *
     * @return boolean
     */
    public final boolean isZero()
    {
        return this.x == 0 && this.y == 0;
    }

    /**
     * Check if this Vector is inside the bounds of a Shape.
     *
     * @param shape
     *
     * @return boolean
     */
    public final boolean isInsideBoundsOf(Shape<? extends Engine> shape)
    {
        return ! (
                this.x < shape.getPosition().x ||
                this.y < shape.getPosition().y ||
                this.x > shape.getPosition().x + shape.getSize().getWidth() ||
                this.y > shape.getPosition().y + shape.getSize().getHeight()
		);
    }

    /**
     * Create a Zero Vector.
     *
     * @return Vector
     */
    public final static Vector Zero() {
        return new Vector(0, 0);
    }

    /**
     * The maximum vector allowed with an additional buffer.
     * @return
     */
    public final static Vector Max(int buffer, Engine engine) {
        Vector result = new Vector(engine.getWindowSize().getWidth() + buffer, engine.getWindowSize().getHeight() + buffer);
        result.buffer = buffer;

        return result;
    }

    /**
     * The maximum vector allowed.
     * @return
     */
    public final static Vector Max(Engine engine) {
        return Vector.Max(0, engine);
    }
}
