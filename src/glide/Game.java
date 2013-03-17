package glide;

import glide.entities.Bullet;
import glide.entities.HealthBar;
import glide.entities.Player;
import glide.input.Controller;
import glide.input.KeyInput;
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


public class Game extends Canvas implements Runnable{

	/**
	 * Generated Serial Version UID
	 */
	private static final long serialVersionUID = -4093553489357496142L;
	
	/* Game Properties */
	private boolean running = false;
	private Thread thread;
	
	private BufferedImage image = new BufferedImage(Glide.WIDTH,Glide.HEIGHT,BufferedImage.TYPE_INT_RGB);
	private BufferedImage spriteSheet = null;
	private BufferedImage background = null;
	
	
	private Player p;
	private Controller c;
	private HealthBar healthBar;
	
	private Textures textures;
	
	private int score = 0;
	private boolean paused = false;
	private boolean status = true;
	/*
	private int level = 1;
	*/
	
	/* status */
	private int tps;
	private int fps;
	
	

	public void init(){
		requestFocus();
		BufferedImageLoader loader = new BufferedImageLoader();
		try{
			spriteSheet = loader.loadImage("/images/sprite_sheet.png");
			background = loader.loadImage("/images/mm_b.png");
		}catch(IOException e){
			e.printStackTrace();
		}
		
		addKeyListener(new KeyInput(this));
		
		setTextures(new Textures(this));
		
		p = new Player(((Glide.WIDTH * Glide.SCALE) / 2) - 16, (Glide.HEIGHT * Glide.SCALE) - 52, this);
		c = new Controller(this);
		healthBar = new HealthBar(this.getWidth() - 52, 20, this);
		
		
	}
	
	/* Thread Control */
	public synchronized void start(){
		if(running){
			return;
		}
		
		thread = new Thread(this);
		thread.start();
		running = true;
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
		if(!isPaused()){
			p.tick();
			c.tick();
			healthBar.tick();
			if(adde == 20){
				this.c.spawnEnemy();
				adde = 0;
			}
			adde++;
		}
	}
	
	private void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		////////////////////////////////////////////////////////
		
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		
		g.drawImage(background, 0, 0, null);
		
		p.render(g);
		c.render(g);
		
		g.setFont(new Font("Ariel", Font.BOLD, 24));
		g.setColor(Color.GREEN);
		String sco = "Score: " + score;
		g.drawChars(sco.toCharArray(), 0, sco.toCharArray().length, 20, 40);
		
		healthBar.render(g);
		
		/* pause menu */
		if(isPaused()){
			String pause = "Press 'Escape' to resume";
			String pause2 = "Press 'q' to return to Main Menu";
			g.setFont(new Font("Ariel", Font.BOLD, 36));
			g.drawChars(pause.toCharArray(), 0, pause.toCharArray().length, ((Glide.WIDTH * Glide.SCALE) / 2) - (g.getFontMetrics().stringWidth(pause) / 2), ((Glide.HEIGHT * Glide.SCALE) / 2) + (g.getFontMetrics().getDescent() - 38));
			g.drawChars(pause2.toCharArray(), 0, pause2.toCharArray().length, ((Glide.WIDTH * Glide.SCALE) / 2) - (g.getFontMetrics().stringWidth(pause2) / 2), ((Glide.HEIGHT * Glide.SCALE) / 2) + (g.getFontMetrics().getDescent()));
			g.setFont(new Font("Ariel", Font.BOLD, 24));
		}
		
		/* status */
		if(isStatus()){
			String status = "TPS: " + getTps() + " |  FPS: " + getFps();
			g.setFont(new Font("Ariel", Font.BOLD, 12));
			g.setColor(Color.ORANGE);
			g.drawChars(status.toCharArray(), 0, status.toCharArray().length, ((Glide.WIDTH * Glide.SCALE)) - (g.getFontMetrics().stringWidth(status) / 2) - 64, ((Glide.HEIGHT * Glide.SCALE)) + (g.getFontMetrics().getDescent()) - 15);
		}
		
		////////////////////////////////////////////////////////
		g.dispose();
		bs.show();
	}
	
	

	
	public Game() {
		
	}
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_UP){
			if(!isPaused()){
				this.getPlayer().setVelocityY(-3);
			}
		}else if(key == KeyEvent.VK_LEFT){
			if(!isPaused()){
				this.getPlayer().setVelocityX(-3);
			}
		}else if(key == KeyEvent.VK_DOWN){
			if(!isPaused()){
				this.getPlayer().setVelocityY(3);
			}
		}else if(key == KeyEvent.VK_RIGHT){
			if(!isPaused()){	
				this.getPlayer().setVelocityX(3);
			}
		}else if(key == KeyEvent.VK_SPACE && !getPlayer().isShooting()){
			if(!isPaused()){
				if(!getPlayer().isBeaming()){
					c.addBullet(new Bullet(p.getX(), p.getY(), this));
					getPlayer().setShooting(true);
				}				
			}
		}else if(key == KeyEvent.VK_ESCAPE){
			if(!isPaused()){
				pause();
			}else{
				resume();
			}
		}else if(key == KeyEvent.VK_F3){
			if(!isStatus()){
				setStatus(true);
			}else{
				setStatus(false);
			}
		}else if(key == KeyEvent.VK_Q){
			if(isPaused()){
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
				Glide.mm = mm;
				Glide.frame.remove(Glide.game);
				Glide.frame.add(Glide.mm);
				Glide.frame.pack();
				Glide.mm.start();
			}
		}
	}
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_UP){
			if(!isPaused()){
				this.getPlayer().setVelocityY(0);
			}
		}else if(key == KeyEvent.VK_LEFT){
			if(!isPaused()){
				this.getPlayer().setVelocityX(0);
			}
		}else if(key == KeyEvent.VK_DOWN){
			if(!isPaused()){
				this.getPlayer().setVelocityY(0);
			}
		}else if(key == KeyEvent.VK_RIGHT){
			if(!isPaused()){
				this.getPlayer().setVelocityX(0);
			}
		}else if(key == KeyEvent.VK_SPACE){
			if(!isPaused()){
				if(!getPlayer().isBeaming()){
					getPlayer().setShooting(false);
				}
			}
		}
	}
	public Player getPlayer() {
		return p;
	}

	public void setPlayer(Player p) {
		this.p = p;
	}

	public static int getScale() {
		return Glide.SCALE;
	}

	
	
	public BufferedImage getSpriteSheet(){
		return this.spriteSheet;
	}
	
	public Textures getTextures() {
		return textures;
	}

	public void setTextures(Textures textures) {
		this.textures = textures;
	}
	
	public HealthBar getHealthBar() {
		return healthBar;
	}

	public void setHealthBar(HealthBar healthBar) {
		this.healthBar = healthBar;
	}

	public Controller getController() {
		return c;
	}

	public void setController(Controller c) {
		this.c = c;
	}
	
	public boolean isPaused() {
		return paused;
	}

	public void pause(){
		this.paused = true;
	}
	public void resume(){
		this.paused = false;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
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

}
