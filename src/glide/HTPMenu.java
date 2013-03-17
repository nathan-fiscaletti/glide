package glide;

import glide.spritehandles.BufferedImageLoader;
import glide.spritehandles.Textures;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;



public class HTPMenu extends Canvas implements Runnable{

	/**
	 * Generated Serial Version UID
	 */
	private static final long serialVersionUID = -4093553489357496142L;
	
	
	/* Dimensions for the game */
	public static final int WIDTH = 320;
	public static final int HEIGHT = WIDTH / 12 * 9;
	public static final int SCALE = 2;
	
	/* Game Properties */
	public static final String TITLE = "Glide v0.1a";
	private boolean running = false;
	private Thread thread;
	
	private BufferedImage background = null;
	private BufferedImage logo = null;
	
	
	private Textures textures;
	
	private int score = 0;
	
	private int selected = 1;
	
	/*
	private int level = 1;
	*/
	
	
	

	public void init(){
		requestFocus();
		BufferedImageLoader loader = new BufferedImageLoader();
		try{
			background = loader.loadImage("/images/mm_b.png");
			logo = loader.loadImage("/images/logo.png");
		}catch(IOException e){
			e.printStackTrace();
		}
		
		addKeyListener(new HTPKeyInput(this));
		
	}
	
	/* Thread Control */
	public synchronized void start(){
		if(running){
			return;
		}
		
		running = true;
		thread = new Thread(this);
		thread.start();
	
	}
	
	public synchronized void stop() throws Exception{
		if(!running){
			return;
		}
		
		running = false;
	}
	/* End Thread Control */
	
	//Main GAME
	@Override
	public void run() {
		init();
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1){
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				//System.out.println("TPS: " + updates + " , FPS:" + frames);
				updates = 0;
				frames = 0;
			}
		}
		try {
			stop();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	int adde = 0;
	private void tick(){
		
	}
	
	private void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		////////////////////////////////////////////////////////

		g.drawImage(background, 0, 0, null);
		Font f = new Font("Ariel", Font.BOLD, 16);
		g.setFont(f);
		
		/* Logo */
		int logow = logo.getWidth();
		int logoh = logo.getHeight();
		g.drawImage(logo, ((WIDTH * SCALE) / 2) - (logow / 2), 120, null);
		
		String htp = "Use the arrow keys to move your spaceship back and forth";
		String htp2 = "Use space to shoot and destroy enemies"; 
		String htp3 = "Pick up drops along the way, and defeat the enemy invaders!";
		
		g.setColor(Color.ORANGE);
		g.drawChars(htp.toCharArray(), 0, htp.toCharArray().length, ((WIDTH * SCALE) / 2) - (g.getFontMetrics().stringWidth(htp) / 2), ((HEIGHT * SCALE) / 2) + (g.getFontMetrics().getDescent() - 78 + logoh));
		g.drawChars(htp2.toCharArray(), 0, htp2.toCharArray().length, ((WIDTH * SCALE) / 2) - (g.getFontMetrics().stringWidth(htp2) / 2), ((HEIGHT * SCALE) / 2) + (g.getFontMetrics().getDescent() - 62 + logoh));
		g.drawChars(htp3.toCharArray(), 0, htp3.toCharArray().length, ((WIDTH * SCALE) / 2) - (g.getFontMetrics().stringWidth(htp3) / 2), ((HEIGHT * SCALE) / 2) + (g.getFontMetrics().getDescent() - 46 + logoh));
		
		
		/* Menu Items */

		//Back
		///
		f = new Font("Ariel", Font.BOLD, 24);
		g.setFont(f);
		g.setColor(Color.GREEN);
		String sco3 = "Back";
		int w3 = g.getFontMetrics().stringWidth(sco3);
		g.drawChars(sco3.toCharArray(), 0, sco3.toCharArray().length, ((WIDTH * SCALE) / 2) - (w3 / 2), ((HEIGHT * SCALE) / 2) + (g.getFontMetrics().getDescent() + logoh));
		///
		
		////////////////////////////////////////////////////////
		g.dispose();
		bs.show();
	}
	
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_ENTER){
			MainMenu mm = new MainMenu();
			mm.setPreferredSize(new Dimension(MainMenu.WIDTH * MainMenu.SCALE, MainMenu.HEIGHT * MainMenu.SCALE));
			mm.setMaximumSize(new Dimension(MainMenu.WIDTH * MainMenu.SCALE, MainMenu.HEIGHT * MainMenu.SCALE));
			mm.setMinimumSize(new Dimension(MainMenu.WIDTH * MainMenu.SCALE, MainMenu.HEIGHT * MainMenu.SCALE));
			try {
				stop();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Glide.mm = mm;
			Glide.frame.remove(Glide.htp);
			Glide.frame.add(Glide.mm);
			Glide.frame.pack();
			Glide.mm.start();
		}
	}
	public void keyReleased(KeyEvent e){
		
	}

	public static int getScale() {
		return SCALE;
	}
	
	public Textures getTextures() {
		return textures;
	}

	public void setTextures(Textures textures) {
		this.textures = textures;
	}
	

}
