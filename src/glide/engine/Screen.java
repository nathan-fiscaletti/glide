package glide.engine;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

import glide.game.Glide;

public abstract class Screen extends Canvas implements Runnable
{
	/**
	 * Serial Version UID 
	 */
	private static final long serialVersionUID = -1303916252996012557L;
	
	/**
	 * The name of the screen.
	 */
	public String name = "Screen";
	
	/**
	 * The entity controller for controlling
	 * any entities you might want to have
	 * attached to this screen.
	 */
	public EntityController controller;
	
	/**
	 * The Ticks Per Second for this screen.
	 */
	public int tps;
	
	/**
	 * The Frames Per Second for this screen.
	 */
	public int fps;
	
	/**
	 * If set to true, the logo will be rendered out.
	 */
	public boolean shouldRenderLogo = false;
	
	/**
	 * If set to true, the background will be rendered out.
	 */
	public boolean shouldRenderBackground = false;
	
	/**
	 * Primary thread control.
	 */
	private boolean running = false;
	
	/**
	 * Primary Thread.
	 */
	private Thread thread;
	
	/**
	 * Called to initialize properties.
	 */
	protected abstract void initialize();
	
	/**
	 * Called when a frame should be rendered out.
	 *
	 * @param graphics
	 */
	protected abstract void renderFrame(Graphics graphics);
	
	/**
	 * Called when an update to this screen should occure.
	 */
	protected abstract void update();
	
	/**
	 * Called when a key is pressed down.
	 *
	 * @param keyEvent
	 */
	protected abstract void onKeyPressed(KeyEvent keyEvent);
	
	/**
	 * Called when a key is released.
	 *
	 * @param keyEvent
	 */
	protected abstract void onKeyReleased(KeyEvent keyEvent);
	
	/**
	 * Primary run body for controlling screens.
	 */
	@Override
	public final void run() {
		init();
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		Glide.frame.setTitle(Glide.TITLE);
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1){
				runUpdate();
				updates++;
				delta--;
			}
			renderFrame();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				tps = updates;
				fps = frames;
				updates = 0;
				frames = 0;
			}
		}
		try {
			stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Initialize the screens primary thread.
	 */
	public final synchronized void start() 
	{
		if (this.running) {
			return;
		}
		
		this.running = true;
		this.thread = new Thread(this);
		this.thread.start();
	}
	
	/**
	 * Stop the screens primary thread.
	 * @throws Exception
	 */
	public final synchronized void stop() throws Exception
	{
		this.running = false;
	}
	
	/**
	 * Internal initialization function.
	 */
	private void init()
	{
		this.requestFocus();
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e){
				onKeyPressed(e);
			}
			
			@Override
			public void keyReleased(KeyEvent e){
				onKeyReleased(e);
			}
		});
		this.initialize();
	}
	
	/**
	 * Internal renderFrame function.
	 */
	private void renderFrame()
	{
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		
		Graphics graphics = bs.getDrawGraphics();
		
		if (this.shouldRenderBackground) {
			Glide.backgroundRenderer.render(graphics, this);
		}
		if (this.shouldRenderLogo) {
			Glide.logoRenderer.render(graphics, this);
		}
		
		this.renderFrame(graphics);
		
		graphics.dispose();
		bs.show();
	}
	
	/**
	 * Internal update function.
	 */
	private void runUpdate()
	{
		Glide.backgroundRenderer.update();
		Glide.logoRenderer.update();
		this.update();
	}
}
