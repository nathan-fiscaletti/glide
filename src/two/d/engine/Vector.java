package two.d.engine;

public class Vector {
	
	/**
	 * The X value.
	 */
	public int x;
	
	/**
	 * The Y value.
	 */
	public int y;
	
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
	 * Check if this Vector is out of the bounds of another.
	 *
	 * @param bounds
	 *
	 * @return boolean
	 */
	public final boolean isOutOfBoundsOf(Vector bounds)
	{
		return this.x < -bounds.buffer || this.y < -bounds.buffer || this.x > bounds.x || this.y > bounds.y;
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
		Vector result = new Vector(engine.getWidth() + buffer, engine.getHeight() + buffer);
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
