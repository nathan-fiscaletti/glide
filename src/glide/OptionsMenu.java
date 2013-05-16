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




public class OptionsMenu extends Canvas implements Runnable{

	/**
	 * Generated Serial Version UID
	 */
	private static final long serialVersionUID = -4093553489357496142L;
	
	
	/* Game Properties */
	private boolean running = false;
	private Thread thread;
	
	private BufferedImage background = null;
	private BufferedImage logo = null;
	
	
	private Textures textures;
	
	private int score = 0;
	
	private int selected = 1;
	private int tps;
	private int fps;
	
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
		addKeyListener(new OptionsKeyInput(this));
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
		Font f = new Font("Ariel", Font.BOLD, 24);
		g.setFont(f);
		
		/* Logo & Title */
		int logow = logo.getWidth();
		int logoh = logo.getHeight();
		g.drawImage(logo, ((Glide.WIDTH * Glide.SCALE) / 2) - (logow / 2), 120, null);
		String op = "Options";
		f = new Font("Ariel", Font.BOLD, 32);
		g.setFont(f);
		g.setColor(Color.ORANGE);
		int w = g.getFontMetrics().stringWidth(op);
		g.drawChars(op.toCharArray(), 0, op.toCharArray().length, ((Glide.WIDTH * Glide.SCALE) / 2) - (w / 2), ((Glide.HEIGHT * Glide.SCALE) / 2) + (g.getFontMetrics().getDescent() - 130 + logoh));
		
		
		/* Menu Items */
		
		f = new Font("Ariel", Font.BOLD, 24);
		g.setFont(f);
		//Audio
		/////
		if(selected == 1){
			g.setColor(Color.GREEN);
		}else{
			g.setColor(Color.DARK_GRAY);
		}
		String sco = "Music - " + ((Glide.music) ? "Enabled" : "Disabled");
		w = g.getFontMetrics().stringWidth(sco);
		g.drawChars(sco.toCharArray(), 0, sco.toCharArray().length, ((Glide.WIDTH * Glide.SCALE) / 2) - (w / 2), ((Glide.HEIGHT * Glide.SCALE) / 2) + (g.getFontMetrics().getDescent() - 49 + logoh));
		////
		//
		
		//Sounds
			/////
			if(selected == 2){
				g.setColor(Color.GREEN);
			}else{
				g.setColor(Color.DARK_GRAY);
			}
			sco = "Sounds - " + ((Glide.sounds) ? "Enabled" : "Disabled");
			w = g.getFontMetrics().stringWidth(sco);
			g.drawChars(sco.toCharArray(), 0, sco.toCharArray().length, ((Glide.WIDTH * Glide.SCALE) / 2) - (w / 2), ((Glide.HEIGHT * Glide.SCALE) / 2) + (g.getFontMetrics().getDescent() + logoh - 13));
			////
			//
			
    	//Sounds
		/////
			if(selected == 3){
				g.setColor(Color.GREEN);
			}else{
				g.setColor(Color.DARK_GRAY);
			}
			sco = "Controls";
			w = g.getFontMetrics().stringWidth(sco);
			g.drawChars(sco.toCharArray(), 0, sco.toCharArray().length, ((Glide.WIDTH * Glide.SCALE) / 2) - (w / 2), ((Glide.HEIGHT * Glide.SCALE) / 2) + (g.getFontMetrics().getDescent() + logoh + 24));
			////
			//
		
		
		//Back
		///
		if(selected == 4){
			g.setColor(Color.GREEN);
		}else{
			g.setColor(Color.DARK_GRAY);
		}
		String sco3 = "Back";
		int w3 = g.getFontMetrics().stringWidth(sco3);
		g.drawChars(sco3.toCharArray(), 0, sco3.toCharArray().length, ((Glide.WIDTH * Glide.SCALE) / 2) - (w3 / 2), ((Glide.HEIGHT * Glide.SCALE) / 2) + (g.getFontMetrics().getDescent() + logoh + 73));
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
		if(key == KeyEvent.VK_UP){
			if(selected == 1){
				selected = 4;
			}else if(selected == 2){
				selected = 1;
			}else if(selected == 3){
				selected = 2;
			}else if(selected == 4){
				selected = 3;
			}
			Glide.select.play();
		}else if (key == KeyEvent.VK_DOWN){
			if(selected == 1){
				selected = 2;
			}else if(selected == 2){
				selected = 3;
			}else if(selected == 3){
				selected = 4;
			}else if(selected == 4){
				selected = 1;
			}
			Glide.select.play();
		}else if(key == KeyEvent.VK_ENTER){
			if(selected == 1){
				if(Glide.music){
					Glide.muteMusic();
				}else{
					Glide.unmuteMusic();
				}
			}else if(selected == 2){
				if(Glide.sounds){
					Glide.muteSounds();
				}else{
					Glide.unmuteSounds();
				}
			}else if(selected == 3){
				//Controls screen
				Glide.enter.play();
				ControlsMenu cm = new ControlsMenu();
				cm.setPreferredSize(new Dimension(Glide.WIDTH * Glide.SCALE, Glide.HEIGHT * Glide.SCALE));
				cm.setMaximumSize(new Dimension(Glide.WIDTH * Glide.SCALE, Glide.HEIGHT * Glide.SCALE));
				cm.setMinimumSize(new Dimension(Glide.WIDTH * Glide.SCALE, Glide.HEIGHT * Glide.SCALE));
				try {
					stop();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(!GlideSystem.isApplet){
					Glide.cm = cm;
					Glide.frame.remove(Glide.op);
					Glide.frame.add(Glide.cm);
					Glide.frame.pack();
					Glide.cm.start();
				}else{
					Glide.cm = cm;
					Glide.frame.remove(Glide.op);
					Glide.frame.add(Glide.cm);
					Glide.cm.start();
				}
			}else if(selected == 4){

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
					Glide.frame.remove(Glide.op);
					Glide.frame.add(Glide.mm);
					Glide.frame.pack();
					Glide.mm.start();
				}else{
					Glide.mm = mm;
					Glide.frame.remove(Glide.op);
					Glide.frame.add(Glide.mm);
					Glide.mm.start();
				}

			}
		}
		

	}
	
	public void keyReleased(KeyEvent e){
		/*
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_UP){
			this.getPlayer().setVelocityY(0);
		}else if(key == KeyEvent.VK_LEFT){
			this.getPlayer().setVelocityX(0);
		}else if(key == KeyEvent.VK_DOWN){
			this.getPlayer().setVelocityY(0);
		}else if(key == KeyEvent.VK_RIGHT){
			this.getPlayer().setVelocityX(0);
		}else if(key == KeyEvent.VK_SPACE){
			if(!getPlayer().isRapidFire()){
				getPlayer().setShooting(false);
			}
		}
		*/
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
	

}
