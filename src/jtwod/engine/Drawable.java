package jtwod.engine;

import javafx.scene.Parent;

import java.awt.Canvas;
import java.awt.Graphics;

/**
 * Class for implementing both render and update methods.
 *
 * @author Nathan
 */
public abstract class Drawable<ParentEngine extends Engine> {

    /**
     * Enumeration used to define Center constraints in Drawable objects.
     */
    public enum Center {
        Vertically,
        Horizontally,
        None
    }

    /**
     * If set to false, this will not render when it is in the global render scope.
     */
    private boolean shouldRenderWhenGlobal = true;

    /**
     * If set to true, this render will render on top of everything else.
     */
    private boolean topMost = false;

    /**
     * The parent engine for this Drawable.
     */
    private ParentEngine parentEngine;

    /**
     * Create the renderer with a parent engine.
     *
     * @param engine
     */
    public Drawable(ParentEngine engine)
    {
        this.parentEngine = engine;
    }

    /**
     * Render graphics out to a canvas.
     *
     * @param g
     * @param canvas
     */
    protected abstract void render(Graphics g, Screen<ParentEngine> canvas);

    /**
     * Update this renderer.
     */
    protected abstract void update();

    /**
     * Update whether or not this Drawable should render in the global scope.
     *
     * @param shouldRenderWhenGlobal
     */
    public final void setShouldRenderWhenGlobal(boolean shouldRenderWhenGlobal)
    {
        this.shouldRenderWhenGlobal = shouldRenderWhenGlobal;
    }

    /**
     * Checks if this renderer should render in the global scope.
     *
     * @return
     */
    public final boolean shouldREnderWhenGlobal()
    {
        return this.shouldRenderWhenGlobal;
    }

    /**
     * Update whether or not this Drawable should render on top of everything else.
     *
     * @param topMost
     */
    public final void setTopMost(boolean topMost)
    {
        this.topMost = topMost;
    }

    /**
     * Checks if this Drawable should render on top of everything else.
     *
     * @return
     */
    public final boolean isTopMost()
    {
        return this.topMost;
    }

    /**
     * Retrieve the parent engine.
     *
     * @return
     */
    public final ParentEngine getParentEngine()
    {
        return this.parentEngine;
    }
}
