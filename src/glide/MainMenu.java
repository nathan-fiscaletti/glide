package glide;

import glide.spritehandles.BufferedImageLoader;
import glide.spritehandles.Textures;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;




public class MainMenu extends Canvas implements Runnable{

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
		addKeyListener(new MenuKeyInput(this));
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
		
		/* Logo */
		int logow = logo.getWidth();
		int logoh = logo.getHeight();
		g.drawImage(logo, ((Glide.WIDTH * Glide.SCALE) / 2) - (logow / 2), 120, null);
		f = new Font("Ariel", Font.BOLD, 12);
		g.setFont(f);
		g.setColor(Color.GREEN);
		g.drawChars(Glide.version.toCharArray(), 0, Glide.version.length(), ((Glide.WIDTH * Glide.SCALE) / 2) - (g.getFontMetrics().stringWidth(Glide.version) / 2), 130 + logoh);
		f = new Font("Ariel", Font.BOLD, 24);
		g.setFont(f);
		
		/* Menu Items */
		
		//Play
		/////
		if(selected == 1){
			g.setColor(Color.GREEN);
		}else{
			g.setColor(Color.DARK_GRAY);
		}
		String sco = "Play";
		int w = g.getFontMetrics().stringWidth(sco);
		g.drawChars(sco.toCharArray(), 0, sco.toCharArray().length, ((Glide.WIDTH * Glide.SCALE) / 2) - (w / 2), ((Glide.HEIGHT * Glide.SCALE) / 2) + (g.getFontMetrics().getDescent() - 49 + logoh));
		////
		
		
		//How to play
		///
		if(selected == 2){
			g.setColor(Color.GREEN);
		}else{
			g.setColor(Color.DARK_GRAY);
		}
		String sco2 = "How To Play";
		int w2 = g.getFontMetrics().stringWidth(sco2);
		g.drawChars(sco2.toCharArray(), 0, sco2.toCharArray().length, ((Glide.WIDTH * Glide.SCALE) / 2) - (w2 / 2), ((Glide.HEIGHT * Glide.SCALE) / 2) + (g.getFontMetrics().getDescent() - 25 + logoh));
		///
		
		//Options
		///
		if(selected == 3){
			g.setColor(Color.GREEN);
		}else{
			g.setColor(Color.DARK_GRAY);
		}
		sco2 = "Options";
		w2 = g.getFontMetrics().stringWidth(sco2);
		g.drawChars(sco2.toCharArray(), 0, sco2.toCharArray().length, ((Glide.WIDTH * Glide.SCALE) / 2) - (w2 / 2), ((Glide.HEIGHT * Glide.SCALE) / 2) + (g.getFontMetrics().getDescent() + logoh));
		///
		
		//Exit
		///
		if(selected == 4){
			g.setColor(Color.GREEN);
		}else{
			g.setColor(Color.DARK_GRAY);
		}
		String sco3 = "Exit";
		int w3 = g.getFontMetrics().stringWidth(sco3);
		g.drawChars(sco3.toCharArray(), 0, sco3.toCharArray().length, ((Glide.WIDTH * Glide.SCALE) / 2) - (w3 / 2), ((Glide.HEIGHT * Glide.SCALE) / 2) + (g.getFontMetrics().getDescent() + logoh + 25));
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
				Glide.enter.play();
				Game game = new Game();
				game.setPreferredSize(new Dimension(Glide.WIDTH * Glide.SCALE, Glide.HEIGHT * Glide.SCALE));
				game.setMaximumSize(new Dimension(Glide.WIDTH * Glide.SCALE, Glide.HEIGHT * Glide.SCALE));
				game.setMinimumSize(new Dimension(Glide.WIDTH * Glide.SCALE, Glide.HEIGHT * Glide.SCALE));
				try {
					stop();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(!GlideSystem.isApplet){
					Glide.game = game;
					Glide.game.addFocusListener(new FocusListener(){
						
						@Override
						public void focusLost(FocusEvent arg0) {
							if(Glide.game != null){
								if(!Glide.game.won() && !Glide.game.lost() && !Glide.game.cheating()){
									Glide.game.pause();
								}
							}
						}

						@Override
						public void focusGained(FocusEvent e) {
							// TODO Auto-generated method stub
							
						}
						
					});
					Glide.frame.remove(Glide.mm);
					Glide.frame.add(Glide.game);
					Glide.frame.pack();
					Glide.game.start();
				}else{
					Glide.game = game;
					Glide.game.addFocusListener(new FocusListener(){
						
						@Override
						public void focusLost(FocusEvent arg0) {
							if(Glide.game != null){
								Glide.game.pause();
							}
						}

						@Override
						public void focusGained(FocusEvent e) {
							// TODO Auto-generated method stub
							
						}
						
					});
					Glide.frame.remove(Glide.mm);
					Glide.frame.add(Glide.game);
					Glide.game.start();
				}
			}else if(selected == 2){
				Glide.enter.play();
				HTPMenu htp = new HTPMenu();
				htp.setPreferredSize(new Dimension(Glide.WIDTH * Glide.SCALE, Glide.HEIGHT * Glide.SCALE));
				htp.setMaximumSize(new Dimension(Glide.WIDTH * Glide.SCALE, Glide.HEIGHT * Glide.SCALE));
				htp.setMinimumSize(new Dimension(Glide.WIDTH * Glide.SCALE, Glide.HEIGHT * Glide.SCALE));
				
				try{
					stop();
				} catch (Exception e1){
					e1.printStackTrace();
				}
				if(!GlideSystem.isApplet){
					Glide.htp = htp;
					Glide.frame.remove(Glide.mm);
					Glide.frame.add(Glide.htp);
					Glide.frame.pack();
					Glide.htp.start();
				}else{
					Glide.htp = htp;
					Glide.frame.remove(Glide.mm);
					Glide.frame.add(Glide.htp);
					Glide.htp.start();
				}
				
			}else if(selected == 3){
				Glide.enter.play();
				OptionsMenu op = new OptionsMenu();
				op.setPreferredSize(new Dimension(Glide.WIDTH * Glide.SCALE, Glide.HEIGHT * Glide.SCALE));
				op.setMaximumSize(new Dimension(Glide.WIDTH * Glide.SCALE, Glide.HEIGHT * Glide.SCALE));
				op.setMinimumSize(new Dimension(Glide.WIDTH * Glide.SCALE, Glide.HEIGHT * Glide.SCALE));
				
				try{
					stop();
				} catch (Exception e1){
					e1.printStackTrace();
				}
				if(!GlideSystem.isApplet){
					Glide.op = op;
					Glide.frame.remove(Glide.mm);
					Glide.frame.add(Glide.op);
					Glide.frame.pack();
					Glide.op.start();
				}else{
					Glide.op = op;
					Glide.frame.remove(Glide.mm);
					Glide.frame.add(Glide.op);
					Glide.op.start();
				}
			}else if(selected == 4){
				System.exit(0);
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
