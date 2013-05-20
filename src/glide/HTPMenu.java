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
	
	
	/* Game Properties */
	private boolean running = false;
	private Thread thread;
	
	private BufferedImage logo;
	
	
	private Textures textures;
	
	private int score = 0;
	
	private int tps;
	private int fps;
	
	private int selected = 1;
	
	/*
	private int level = 1;
	*/
	
	
	

	public void init(){
		requestFocus();
		BufferedImageLoader loader = new BufferedImageLoader();
		try{
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
				tps = updates;
				fps = frames;
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
		Glide.b1y+=Glide.backgroundSpeed;
		Glide.b2y+=Glide.backgroundSpeed;
	}
	
	private void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		////////////////////////////////////////////////////////

		if(Glide.b1y >= (Glide.HEIGHT * Glide.SCALE)){
			Glide.b1y = Glide.b2y - Glide.background.getHeight();
		}
		
		if(Glide.b2y >= (Glide.HEIGHT * Glide.SCALE)){
			Glide.b2y = Glide.b1y - Glide.background2.getHeight();
		}
			
		
		g.drawImage(Glide.background, 0, (int)Glide.b1y, null);
		
		g.drawImage(Glide.background2, 0, (int)Glide.b2y, null);
		
		Font f = new Font("Ariel", Font.BOLD, 16);
		g.setFont(f);
		
		/* Logo */
		int logow = logo.getWidth();
		int logoh = logo.getHeight();
		g.drawImage(logo, ((Glide.WIDTH * Glide.SCALE) / 2) - (logow / 2), 120, null);
		
		String htp = "Use the arrow keys to move your spaceship back and forth";
		String htp2 = "Use space to shoot and destroy enemies"; 
		String htp3 = "Pick up drops along the way, and defeat the enemy invaders!";
		
		g.setColor(Color.ORANGE);
		BufferedImageLoader l = new BufferedImageLoader();
		BufferedImage ht = null;
		try {
			ht = l.loadImage("/images/htp.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g.drawImage(ht, ((Glide.WIDTH * Glide.SCALE) / 2) - (ht.getWidth() / 2), 250, this);
		
		
		/* Menu Items */

		//Back
		///
		f = new Font("Ariel", Font.BOLD, 24);
		g.setFont(f);
		g.setColor(Color.GREEN);
		String sco3 = "Back";
		int w3 = g.getFontMetrics().stringWidth(sco3);
		g.drawChars(sco3.toCharArray(), 0, sco3.toCharArray().length, ((Glide.WIDTH * Glide.SCALE) / 2) - (w3 / 2), ((Glide.HEIGHT * Glide.SCALE) / 2) + (g.getFontMetrics().getDescent() + logoh));
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
			Glide.enter.play();
			MainMenu mm = new MainMenu();
			mm.setPreferredSize(new Dimension(Glide.WIDTH * Glide.SCALE, Glide.HEIGHT * Glide.SCALE));
			mm.setMaximumSize(new Dimension(Glide.WIDTH * Glide.SCALE, Glide.HEIGHT * Glide.SCALE));
			mm.setMinimumSize(new Dimension(Glide.WIDTH * Glide.SCALE, Glide.HEIGHT * Glide.SCALE));
			try {
				stop();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(!GlideSystem.isApplet){
				Glide.mm = mm;
				Glide.frame.remove(Glide.htp);
				Glide.frame.add(Glide.mm);
				Glide.frame.pack();
				Glide.mm.start();
			}else{
				Glide.mm = mm;
				Glide.frame.remove(Glide.htp);
				Glide.frame.add(Glide.mm);
				Glide.mm.start();
			}
		}
	}
	public void keyReleased(KeyEvent e){
		
	}

	public static int getScale() {
		return Glide.SCALE;
	}
	
	public Textures getTextures() {
		return textures;
	}

	public void setTextures(Textures textures) {
		this.textures = textures;
	}

	public int getTps() {
		return tps;
	}

	public void setTps(int tps) {
		this.tps = tps;
	}

	public int getFps() {
		return fps;
	}

	public void setFps(int fps) {
		this.fps = fps;
	}

	public int getSelected() {
		return selected;
	}

	public void setSelected(int selected) {
		this.selected = selected;
	}
	

}
