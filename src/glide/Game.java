package glide;

import glide.entities.Bullet;
import glide.entities.HealthBar;
import glide.entities.Plasma;
import glide.entities.Player;
import glide.input.Controller;
import glide.input.KeyInput;
import glide.spritehandles.BufferedImageLoader;
import glide.spritehandles.Textures;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;



public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = -4093553489357496142L;
	
	/* Cheats */
		public static boolean mdb_cheat = false;
		public static boolean beam_cheat = false;
		public static boolean shield_cheat = false;
		public static boolean health_cheat = false;
		public static boolean cod_cheat = false;
		
		public String cheatString = "";
		public String cheatError = "";
	/* End Cheats */
	
		
		
	
	/* Game Properties */
	private boolean running = false;
	private Thread thread;
	private BufferedImage image = (GlideSystem.isApplet) ? new BufferedImage(Glide.WIDTH,Glide.HEIGHT,BufferedImage.TYPE_INT_RGB) : new BufferedImage(Glide.WIDTH,Glide.HEIGHT,BufferedImage.TYPE_INT_RGB);
	private BufferedImage spriteSheet = null;
	
	
	private Player p;
	private Controller c;
	private HealthBar healthBar;
	private Plasma plasmae;
	private Textures textures;
	
	private int score = 0;
	private boolean paused = false;
	private boolean status = true;
	private boolean lost = false;
	private boolean won = false;
	private boolean cheat = false;
	public boolean plasma = false;
	public BufferedImageLoader loader = new BufferedImageLoader();
	
	/* Multi Directional Bombs / Bombs caught - Counters */
	public int mdbs = 5;
	public int boc = 0;
	
	/* Bomb Spawning */
	public int bsc = 0;
	
	private int level = 1;
	
	
	/* Status */
	private int tps;
	private int fps;
	
	/* Giant Circle Shit */
	public int cods = 2;
	public int max_cods = 2;
	private boolean circling;
	public Shape circle = new Ellipse2D.Float();
	float circle_width = 0;
	float circle_height = 0;
	float circle_x = ((Glide.WIDTH * Glide.SCALE) / 2) - (circle_width / 2);
	float circle_y = ((Glide.HEIGHT * Glide.SCALE) / 2) - (circle_height / 2);
	float pcx = 0;
	float pcy = 0;
	
	/* Power Ups */
	public int shield = 0;
	public int beam = 0;
	
	/* Initial CountDown */
	private String cntdwn_string = "... Get Ready To Play! ...";
	private int cntdwn_ticks = 5;
	private boolean cntdwn_done = false;
	
	
	
	/* Countdown Control */
	
	public void countDownTick(){
		if(cntdwn_ticks == 0){
			cntdwn_string = "";
			cntdwn_done = true;
		}else if(cntdwn_ticks == 1){
			cntdwn_string = "Go!";
			cntdwn_ticks --;
		}else{
			cntdwn_ticks --;
		}
	}
	
	public void drawCountDown(Graphics g){
		g.setFont(new Font("Ariel", Font.BOLD, 24));
		g.setColor(Color.GREEN);
		g.drawChars(cntdwn_string.toCharArray(), 0, cntdwn_string.toCharArray().length, ((Glide.WIDTH * Glide.SCALE) / 2) - (g.getFontMetrics().stringWidth(cntdwn_string) / 2), ((Glide.HEIGHT * Glide.SCALE) / 2) - 24);
		g.setFont(new Font("Ariel", Font.BOLD, 36));
		g.setColor(Color.ORANGE);
		if(!(cntdwn_ticks == 0)){
			g.drawChars(Integer.toString(cntdwn_ticks).toCharArray(), 0, Integer.toString(cntdwn_ticks).toCharArray().length, ((Glide.WIDTH * Glide.SCALE) / 2) - (g.getFontMetrics().stringWidth(Integer.toString(cntdwn_ticks)) / 2), ((Glide.HEIGHT * Glide.SCALE) / 2) + 20);
		}
	}
	
	/* End Countdown Control */

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
	
	
	public void init(){
		requestFocus();
		
		
		try{
			spriteSheet = loader.loadImage("/images/sprite_sheet.png");
		}catch(IOException e){
			e.printStackTrace();
		}
		
		addKeyListener(new KeyInput(this));
		
		setTextures(new Textures(this));
		
		p = new Player(((Glide.WIDTH * Glide.SCALE) / 2) - 16, (Glide.HEIGHT * Glide.SCALE) - 104, this);
		c = new Controller(this);
		healthBar = new HealthBar((this.getWidth() / 2) - (this.getTextures().healthbar1.getWidth() / 2), this.getHeight() - (this.getTextures().healthbar1.getHeight() + 10), this);
		plasmae = new Plasma(((Glide.WIDTH * Glide.SCALE) / 2) - 16, (Glide.HEIGHT * Glide.SCALE) - 52, this);
		Glide.backgroundSpeed = 7;
	}
	
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
	int adde = -60;
	int addm = 0;
	int lvlup = 0;
	int cntDwnT = 0;
	
	int curlvl = 120;
	
	private void tick(){
		
		if(!cntdwn_done){
			cntDwnT ++;
			if(cntDwnT == 60){	
				countDownTick();
				cntDwnT = 0;
			}
		}
		
		if(!isPaused() && !lost() && !won() && !cheating()){
			Glide.b1y+=Glide.backgroundSpeed;
			Glide.b2y+=Glide.backgroundSpeed;
			p.tick();
			c.tick();
			plasmae.tick();
			healthBar.tick();
			lvlup ++;
			adde++;
			addm ++;
			if(adde == curlvl){
				if(cntdwn_done){
					this.c.spawnEnemy();
				}
				adde = 0;
			}
			if(addm == 45){
				if(cntdwn_done){
					this.c.spawnMeteor();
				}
				addm = 0;
			}
			if(lvlup == 1800){
				if(cntdwn_done){
					this.c.spawnBomb();
				}
				if(level != 10)
				{
					level ++;
				}else{
					if(boc == 10){
							win();
					}	
				}
				lvlup = 0;
				curlvl = clu(curlvl);
				adde = 0;
				addm = 0;
			}
			if(isCircling() && (cntdwn_done)){
				circle_width = circle_width + 20;
				circle_height = circle_width + 20;
				circle_x = pcx - (circle_width / 2);
				circle_y = pcy - (circle_height / 2);
				if(circle_width > (Glide.WIDTH * Glide.SCALE) * 2){
					stopCircling();
				}
			}
		}
	}
	
	
	
	private int clu(int in){
		if(in == 120)
			return 90;
		if(in == 90)
			return 60;
		if(in == 60)
			return 45;
		if (in == 45)
			return 30;
		if (in == 30)
			return 25;
		if (in == 25)
			return 20;
		if (in == 20)
			return 15;
		if (in == 15)
			return 10;
		if (in == 10)
			return 5;
		if (in == 5)
			return 2;
		
		return 120;
		
	}
	private void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		////////////////////////////////////////////////////////
		
		
		/* Background Scroller */
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		
		if(Glide.b1y >= (Glide.HEIGHT * Glide.SCALE)){
			Glide.b1y = Glide.b2y - Glide.background.getHeight();
		}
		if(Glide.b2y >= (Glide.HEIGHT * Glide.SCALE)){
			Glide.b2y = Glide.b1y - Glide.background2.getHeight();
		}
		g.drawImage(Glide.background, 0, (int)Glide.b1y, null);
		g.drawImage(Glide.background2, 0, (int)Glide.b2y, null);
		/* End Background Scroller */
		
		p.render(g);
		if(plasma){
			plasmae.setX(p.getX());
			plasmae.setY(p.getY());
			plasmae.render(g);
		}
		c.render(g);
		
		drawCountDown(g);
		
		g.setFont(new Font("Ariel", Font.BOLD, 24));
		
		healthBar.render(g);

		/* HUD Container */
		int wid1 = (Glide.WIDTH * Glide.SCALE) - 20;
		int hit1 = ((Glide.HEIGHT * Glide.SCALE) - 60);
		int rectx1 = 10;
		int recty1 = (Glide.HEIGHT * Glide.SCALE) - 50;
		g.setColor(Color.WHITE);		
		Graphics2D g2d = (Graphics2D)g;
		Stroke os = g2d.getStroke();
		g2d.setStroke(new BasicStroke(5));
		g2d.drawRoundRect(rectx1, recty1, wid1, hit1, 10, 10);
		g2d.setStroke(os);
		g.setColor(Color.GREEN);
		/* End HUD Container */
		
		/* MDBs */
		int out = 32;
		for(int i = 0;i < mdbs;i++){
			g.drawImage(this.getTextures().mdbullet, out, ((Glide.HEIGHT * Glide.SCALE) - 40), null);
			out = out + 32;
		}
		if(mdbs == 5){
			g.drawImage(textures.max, 192, ((Glide.HEIGHT * Glide.SCALE) - 40), null);
		}
		
		/* CODs */
		out = 257;
		for(int i = 0;i < cods; i++){
			g.drawImage(this.getTextures().cod, out, ((Glide.HEIGHT * Glide.SCALE) - 40), null);
			out = out + 32;
		}
		if(cods == 2){
			g.drawImage(textures.max_cod, 321, ((Glide.HEIGHT * Glide.SCALE) - 40), null);
		}
		
		/* Lvl / Score */
		g.setFont(new Font("Ariel", Font.BOLD, 18));
		int lvli = level;
		String lvl = "Level: " + lvli;
		g.setColor(Color.ORANGE);
		g.drawChars(lvl.toCharArray(), 0, lvl.toCharArray().length, (Glide.WIDTH * Glide.SCALE) - (g.getFontMetrics().stringWidth(lvl) * 2) + 50, (Glide.HEIGHT * Glide.SCALE) - 67);
		g.setColor(Color.GREEN);
		String sco = "Score: " + score;
		g.drawChars(sco.toCharArray(), 0, sco.toCharArray().length, (Glide.WIDTH * Glide.SCALE) - (g.getFontMetrics().stringWidth(sco) * 3), (Glide.HEIGHT * Glide.SCALE) - 67);
		
		
		/* Bomb Counter */
		String c = String.valueOf(boc); 
		
		g.drawChars(c.toCharArray(), 0, c.toCharArray().length, 32, (Glide.HEIGHT * Glide.SCALE) - 67);
		g.drawImage(this.getTextures().bombsp, 32 + g.getFontMetrics().stringWidth(c), (Glide.HEIGHT * Glide.SCALE) - 92, null);
		
		/* Power Ups */
		int statsoffset = 64 + g.getFontMetrics().stringWidth(c);
		g.setFont(new Font("Ariel", Font.BOLD, 24));
		if(this.getPlayer().isBeaming()){
			BufferedImage beamer = getTextures().beam;
			BufferedImage level = getTextures().powerbar1;
			if(beam == 1){
				level = getTextures().powerbar5;
			}else if(beam == 2){
				level = getTextures().powerbar4;
			}else if(beam == 3){
				level = getTextures().powerbar3;
			}else if(beam == 4){
				level = getTextures().powerbar2;
			}else if(beam == 5){
				level = getTextures().powerbar1;
			}
			
			g.drawImage(beamer, 32 + statsoffset, (Glide.HEIGHT * Glide.SCALE) - 92, null);
			g.drawImage(level, 32 + 34 + statsoffset, (Glide.HEIGHT * Glide.SCALE) - 92, null);
		}else{
			BufferedImage beamer = getTextures().grayscale_beam;
			g.drawImage(beamer, 32 + statsoffset, (Glide.HEIGHT * Glide.SCALE) - 92, null);
		}
		
		if(this.plasma && this.getPlayer().isPlasma()){
			BufferedImage shielded = getTextures().plasma;
			BufferedImage level = getTextures().powerbar1;
			if(shield == 1){
				level = getTextures().powerbar5;
			}else if(shield == 2){
				level = getTextures().powerbar4;
			}else if(shield == 3){
				level = getTextures().powerbar3;
			}else if(shield == 4){
				level = getTextures().powerbar2;
			}else if(shield == 5){
				level = getTextures().powerbar1;
			}
			
			g.drawImage(shielded, 100 + statsoffset, (Glide.HEIGHT * Glide.SCALE) - 92, null);
			g.drawImage(level, 134 + statsoffset, (Glide.HEIGHT * Glide.SCALE) - 92, null);
		}else{
			BufferedImage shielded = getTextures().grayscale_sheild;
			g.drawImage(shielded, 100 + statsoffset, (Glide.HEIGHT * Glide.SCALE) - 92, null);
		}
		
		
		/* status */
		if(isStatus()){
			int bz = getController().b.size() + getController().eb.size() + getController().mdb.size();
			String status = "MOS: " + (getController().meteors.size() + getController().small_meteors.size())  + " DOS: "+getController().drops.size()+" - BOS: "+bz+" - EOS: " + getController().e.size() + " - TPS: " + getTps() + " -  FPS: " + getFps();
			g.setFont(new Font("Ariel", Font.BOLD, 10));
			g.setColor(Color.ORANGE);
			g.drawChars(status.toCharArray(), 0, status.toCharArray().length, ((Glide.WIDTH * Glide.SCALE)) - (g.getFontMetrics().stringWidth(status)) - 40, ((Glide.HEIGHT * Glide.SCALE)) + (g.getFontMetrics().getDescent()) - 22);
		}
		
		/*//////////////// MENUS / DIALOGS ////////////////////*/
		
		/* pause menu */
		if(isPaused()){
			String pause = "Press 'Escape' to resume";
			String pause2 = "Press 'q' to return to Main Menu";
			String sounds = "Press 'z' to " + ((Glide.sounds) ? "disable" : "enable") + " sounds";
			String music = "Press 'c' to " + ((Glide.music) ? "disable" : "enable") + " music";
			
			g.setFont(new Font("Ariel", Font.BOLD, 36));
			g.drawChars(pause.toCharArray(), 0, pause.toCharArray().length, ((Glide.WIDTH * Glide.SCALE) / 2) - (g.getFontMetrics().stringWidth(pause) / 2), ((Glide.HEIGHT * Glide.SCALE) / 2) + (g.getFontMetrics().getDescent() - 38));
			g.drawChars(pause2.toCharArray(), 0, pause2.toCharArray().length, ((Glide.WIDTH * Glide.SCALE) / 2) - (g.getFontMetrics().stringWidth(pause2) / 2), ((Glide.HEIGHT * Glide.SCALE) / 2) + (g.getFontMetrics().getDescent()));
			g.setFont(new Font("Ariel", Font.BOLD, 18));
			g.drawChars(sounds.toCharArray(), 0, sounds.toCharArray().length, ((Glide.WIDTH * Glide.SCALE) / 4) - (g.getFontMetrics().stringWidth(sounds) / 4) - 20, ((Glide.HEIGHT * Glide.SCALE) / 2) + (g.getFontMetrics().getDescent() + 100));
			g.drawChars(music.toCharArray(), 0, music.toCharArray().length, ((Glide.WIDTH * Glide.SCALE) - (Glide.WIDTH * Glide.SCALE) / 4) - (g.getFontMetrics().stringWidth(music) - (g.getFontMetrics().stringWidth(pause2) / 4)), ((Glide.HEIGHT * Glide.SCALE) / 2) + (g.getFontMetrics().getDescent() + 100));
			g.setFont(new Font("Ariel", Font.BOLD, 24));
		}
		
		/* lost */
		if(lost()){
			g.setFont(new Font("Ariel", Font.BOLD, 12));
			String lose = "Game Over!";
			String lose2 = "Score: " + score;
			String lose3 = "Press 'Enter' to start over";
			String lose4 = "Press 'q' to return to Main Menu";
			String lose5 = "Level: " + lvli;
			//Lose border
			int wid = (g.getFontMetrics().stringWidth(lose4) + 4);
			int hit = (g.getFontMetrics().getDescent()) + 52 + 26 + 118 + 4;
			int rectx = ((Glide.WIDTH * Glide.SCALE) / 2) - (g.getFontMetrics().stringWidth(lose) / 2) + 60;
			int recty = ((Glide.HEIGHT * Glide.SCALE) / 2) + (g.getFontMetrics().getDescent() - 118) - 20;
			g.setColor(Color.ORANGE);
			g.drawRoundRect(wid, hit, rectx, recty, 10, 10);
			
			
			g.setFont(new Font("Ariel", Font.BOLD, 42));
			g.setColor(Color.RED);
			g.drawChars(lose.toCharArray(), 0, lose.toCharArray().length, ((Glide.WIDTH * Glide.SCALE) / 2) - (g.getFontMetrics().stringWidth(lose) / 2), ((Glide.HEIGHT * Glide.SCALE) / 2) + (g.getFontMetrics().getDescent() - 118));
			g.setFont(new Font("Ariel", Font.BOLD, 24));
			g.setColor(Color.ORANGE);
			g.drawChars(lose2.toCharArray(), 0, lose2.toCharArray().length, ((Glide.WIDTH * Glide.SCALE) / 2) - (g.getFontMetrics().stringWidth(lose2) / 2), ((Glide.HEIGHT * Glide.SCALE) / 2) + (g.getFontMetrics().getDescent() - 52));
			
			g.drawChars(lose5.toCharArray(), 0, lose5.toCharArray().length, ((Glide.WIDTH * Glide.SCALE) / 2) - (g.getFontMetrics().stringWidth(lose5) / 2), ((Glide.HEIGHT * Glide.SCALE) / 2) + (g.getFontMetrics().getDescent() - 26));
			
			g.drawChars(lose3.toCharArray(), 0, lose3.toCharArray().length, ((Glide.WIDTH * Glide.SCALE) / 2) - (g.getFontMetrics().stringWidth(lose3) / 2), ((Glide.HEIGHT * Glide.SCALE) / 2) + (g.getFontMetrics().getDescent()));
			g.drawChars(lose4.toCharArray(), 0, lose4.toCharArray().length, ((Glide.WIDTH * Glide.SCALE) / 2) - (g.getFontMetrics().stringWidth(lose4) / 2), ((Glide.HEIGHT * Glide.SCALE) / 2) + (g.getFontMetrics().getDescent() + 26));
			g.setFont(new Font("Ariel", Font.BOLD, 24));
		}
		
		/* won */
		if(won()){
			g.setFont(new Font("Ariel", Font.BOLD, 12));
			String lose = "You win!";
			String lose2 = "Score: " + score;
			String lose3 = "Press 'Enter' to start over";
			String lose4 = "Press 'q' to return to Main Menu";
			String lose5 = "Level: " + lvli;
			//win border
			int wid = (g.getFontMetrics().stringWidth(lose4) + 4);
			int hit = (g.getFontMetrics().getDescent()) + 52 + 26 + 118 + 4;
			int rectx = ((Glide.WIDTH * Glide.SCALE) / 2) - (g.getFontMetrics().stringWidth(lose) / 2) + 60;
			int recty = ((Glide.HEIGHT * Glide.SCALE) / 2) + (g.getFontMetrics().getDescent() - 118) - 20;
			g.setColor(Color.ORANGE);
			g.drawRoundRect(wid, hit, rectx, recty, 10, 10);
			
			
			g.setFont(new Font("Ariel", Font.BOLD, 42));
			g.setColor(Color.GREEN);
			g.drawChars(lose.toCharArray(), 0, lose.toCharArray().length, ((Glide.WIDTH * Glide.SCALE) / 2) - (g.getFontMetrics().stringWidth(lose) / 2), ((Glide.HEIGHT * Glide.SCALE) / 2) + (g.getFontMetrics().getDescent() - 118));
			g.setFont(new Font("Ariel", Font.BOLD, 24));
			g.setColor(Color.ORANGE);
			g.drawChars(lose2.toCharArray(), 0, lose2.toCharArray().length, ((Glide.WIDTH * Glide.SCALE) / 2) - (g.getFontMetrics().stringWidth(lose2) / 2), ((Glide.HEIGHT * Glide.SCALE) / 2) + (g.getFontMetrics().getDescent() - 52));
			
			g.drawChars(lose5.toCharArray(), 0, lose5.toCharArray().length, ((Glide.WIDTH * Glide.SCALE) / 2) - (g.getFontMetrics().stringWidth(lose5) / 2), ((Glide.HEIGHT * Glide.SCALE) / 2) + (g.getFontMetrics().getDescent() - 26));
			
			g.drawChars(lose3.toCharArray(), 0, lose3.toCharArray().length, ((Glide.WIDTH * Glide.SCALE) / 2) - (g.getFontMetrics().stringWidth(lose3) / 2), ((Glide.HEIGHT * Glide.SCALE) / 2) + (g.getFontMetrics().getDescent()));
			g.drawChars(lose4.toCharArray(), 0, lose4.toCharArray().length, ((Glide.WIDTH * Glide.SCALE) / 2) - (g.getFontMetrics().stringWidth(lose4) / 2), ((Glide.HEIGHT * Glide.SCALE) / 2) + (g.getFontMetrics().getDescent() + 26));
			g.setFont(new Font("Ariel", Font.BOLD, 24));
		}
		
		/* cheat menu */
		if(cheating()){
			
			String Title = "Enter Cheat Code";
			String message = "Press 'enter' to submit cheat. Press 'escape' to return to game.";
			g.setFont(new Font("Ariel", Font.BOLD, 24));
			
			g.setColor(Color.ORANGE);
			g.drawChars(Title.toCharArray(), 0, Title.toCharArray().length, ((Glide.WIDTH * Glide.SCALE) / 2) - (g.getFontMetrics().stringWidth(Title) / 2), ((Glide.HEIGHT * Glide.SCALE) / 2) + (g.getFontMetrics().getDescent() - 40));
			g.setColor(Color.GREEN);
			g.setFont(new Font("Ariel", Font.BOLD, 12));
			g.drawChars(message.toCharArray(), 0, message.toCharArray().length, ((Glide.WIDTH * Glide.SCALE) / 2) - (g.getFontMetrics().stringWidth(message) / 2), ((Glide.HEIGHT * Glide.SCALE) / 2) + (g.getFontMetrics().getDescent() - 20));
			g.setColor(Color.RED);
			g.drawChars(cheatError.toCharArray(), 0, cheatError.toCharArray().length, ((Glide.WIDTH * Glide.SCALE) / 2) - (g.getFontMetrics().stringWidth(cheatError) / 2), ((Glide.HEIGHT * Glide.SCALE) / 2) + (g.getFontMetrics().getDescent()));
			g.setFont(new Font("Ariel", Font.BOLD, 12));
			g.setColor(Color.YELLOW);
			g.drawChars(cheatString.toCharArray(), 0, cheatString.toCharArray().length, ((Glide.WIDTH * Glide.SCALE) / 2) - (g.getFontMetrics().stringWidth(cheatString) / 2), ((Glide.HEIGHT * Glide.SCALE) / 2) + (g.getFontMetrics().getDescent() + 20));
		}
		
		g.setColor(Color.RED);
		
		if(isCircling()){
			g.setColor(Color.GREEN);
			circle = new Ellipse2D.Float(circle_x, circle_y, circle_width, circle_height);
			((Graphics2D)g).draw(circle);
			g.setColor(Color.RED);
		}
		
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
		if(cntdwn_done){
			if(key == KeyEvent.VK_UP){
				if(!isPaused() && !lost() && !cheating()){
					this.getPlayer().setVelocityY(-getPlayer().speed);
				}
			}else if(key == KeyEvent.VK_LEFT){
				if(!isPaused() && !lost() && !cheating()){
					this.getPlayer().setVelocityX(-getPlayer().speed);
				}
			}else if(key == KeyEvent.VK_DOWN){
				if(!isPaused() && !lost() && !cheating()){
					this.getPlayer().setVelocityY(getPlayer().speed);
				}
			}else if(key == KeyEvent.VK_RIGHT){
				if(!isPaused() && !lost() && !cheating()){	
					this.getPlayer().setVelocityX(getPlayer().speed);
				}
			}else if(key == KeyEvent.VK_Z){
				if(!isPaused() && !lost() && !cheating()){	
					this.getPlayer().setVelocityX(-Player.boostSpeed);
				}else if(isPaused() && !lost() && ! cheating()){
					if(Glide.sounds){
						Glide.muteSounds();
					}else{
						Glide.unmuteSounds();
					}
				}
			}else if(key == KeyEvent.VK_C){ 
				if(!isPaused() && !lost() && !cheating()){	
					this.getPlayer().setVelocityX(Player.boostSpeed);
				}else if(isPaused() && !lost() && ! cheating()){
					if(Glide.music){
						Glide.muteMusic();
					}else{
						Glide.unmuteMusic();
					}
				}
			}else if(key == KeyEvent.VK_SPACE && !getPlayer().isShooting()){
				if(!isPaused() && !lost() && !cheating()){
					if(!getPlayer().isBeaming()){
						c.addBullet(new Bullet(p.getX(), p.getY() - 32, this));
						getPlayer().setShooting(true);
					}				
				}
			}else if(key == KeyEvent.VK_ESCAPE){
				if(!isPaused()){
					if(!lost() && !cheating()){
						pause();
					}else if(cheating()){
						stopCheating();
					}
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
				if((isPaused() || lost() || won()) && !cheating()){
						MainMenu mm;
						Glide.backgroundSpeed = 1;
						mm = new MainMenu();
						mm.setPreferredSize(new Dimension(Glide.WIDTH * Glide.SCALE, Glide.HEIGHT * Glide.SCALE));
						mm.setMaximumSize(new Dimension(Glide.WIDTH * Glide.SCALE, Glide.HEIGHT * Glide.SCALE));
						mm.setMinimumSize(new Dimension(Glide.WIDTH * Glide.SCALE, Glide.HEIGHT * Glide.SCALE));
						try {
							stop();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						Glide.mm = mm;
					
					
						Glide.frame.remove(Glide.game);
						Glide.frame.add(Glide.mm);
						Glide.frame.pack();
						Glide.mm.start();
					
				}
			}else if(key == KeyEvent.VK_X){
				if(!won() && !lost() && !isPaused() && !cheating())
				if(mdbs > 0){
					getController().shootMDB(this.getPlayer().getX(), this.getPlayer().getY());
					mdbs--;
				}
			}else if(key == KeyEvent.VK_ENTER){
				if((lost() || won()) && !cheating()){
					getController().removeAll();
					stopCircling();
					getHealthBar().setHealth(8);
					setScore(0);
					mdbs = 5;
					cods = 2;
					double x = getPlayer().getX();
					double y = getPlayer().getX();
					setPlayer(new Player(x, y, this));
					getPlayer().setX(((Glide.WIDTH * Glide.SCALE) / 2) - 16);
					getPlayer().setY((Glide.HEIGHT * Glide.SCALE) - 104);
					boc = 0;
					level = 1;
					curlvl = 120;
					restartAfterLost();
				}else if(!cheating() && !won() && !lost() && !isPaused()){
					cheat();
				}else if(cheating()){
					if(cheatString.equalsIgnoreCase("20120614")){
						cheatString = "";
						if(beam_cheat){
							cheatError = "De-Activated Beam Cheat!";
							beam_cheat = false;
						}else{
							cheatError = "Activated Beam Cheat!";
							beam_cheat = true;
						}
					}else if(cheatString.equalsIgnoreCase("19951122")){
						cheatString = "";
						if(shield_cheat){
							cheatError = "De-Activated Shield Cheat!";
							shield_cheat = false;
						}else{
							cheatError = "Activated Shield Cheat!";
							shield_cheat = true;
						}
					}else if(cheatString.equalsIgnoreCase("11232013")){
						cheatString = "";
						if(health_cheat){
							cheatError = "Deactivated Health Cheat!";
							health_cheat = false;
						}else{
							cheatError = "Activated Health Cheat!";
							health_cheat = true;
						}
					}else if(cheatString.equalsIgnoreCase("26435")){
						cheatString = "";
						if(mdb_cheat){
							cheatError = "Deactivated MDB Cheat!";
							mdb_cheat = false;
						}else{
							cheatError = "Activated MDB Cheat!";
							mdb_cheat = true;
						}
					}else if(cheatString.equalsIgnoreCase("4568353669")){
						cheatString="";
						if(cod_cheat){
							cheatError = "Deactivated Sweeper-Detonator Cheat!";
							cod_cheat = false;
						}else{
							cheatError = "Activated Sweeper-Detonator Cheat!";
							cod_cheat = true;
						}
					}else{
						//Bad Cheat //TODO
						cheatError = "Bad Cheat";
						cheatString = "";
					}
				}
			}else if(key == KeyEvent.VK_0 || key == KeyEvent.VK_1 || key == KeyEvent.VK_2 || key == KeyEvent.VK_3 || key == KeyEvent.VK_4 || key == KeyEvent.VK_5 || key == KeyEvent.VK_6 || key == KeyEvent.VK_7 || key == KeyEvent.VK_8 || key == KeyEvent.VK_9){
				if(!lost() && !won() && !isPaused() && cheating()){
					cheatString += KeyEvent.getKeyText(key);
				}
			}else if(key == KeyEvent.VK_BACK_SPACE){
				if(!lost() && !won() && !isPaused() && cheating()){
					if(cheatString.length() > 0){
						cheatString = cheatString.substring(0,cheatString.length()-1);
					}
				}
			}else if(key == KeyEvent.VK_V){
				if(cods > 0 && !isCircling()){
					startCircling((float)this.getPlayer().getX(), (float)this.getPlayer().getY());
					cods -=1;
				}
			}
		}
	}
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_UP){
			if(!isPaused()  && !lost() && !cheating()){
				this.getPlayer().setVelocityY(0);
			}
		}else if(key == KeyEvent.VK_LEFT){
			if(!isPaused()  && !lost() && !cheating()){
				this.getPlayer().setVelocityX(0);
			}
		}else if(key == KeyEvent.VK_DOWN){
			if(!isPaused()  && !lost() && !cheating()){
				this.getPlayer().setVelocityY(0);
			}
		}else if(key == KeyEvent.VK_RIGHT){
			if(!isPaused()  && !lost() && !cheating()){
				this.getPlayer().setVelocityX(0);
			}
		}else if(key == KeyEvent.VK_Z){
			if(!isPaused() && !lost() && !cheating()){	
				this.getPlayer().setVelocityX(0);
			}
		}else if(key == KeyEvent.VK_C){ 
			if(!isPaused() && !lost() && !cheating()){	
				this.getPlayer().setVelocityX(0);
			}
		}else if(key == KeyEvent.VK_SPACE){
			if(!isPaused()  && !lost() && !cheating()){
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
	
	public boolean lost(){
		return this.lost;
	}
	
	public void lose(){
		this.lost = true;
		Glide.gameover.play();
	}
	
	public void restartAfterLost(){
		this.lost = false;
		this.won = false;
	}
	
	public void win(){
		this.won = true;
	}
	
	public boolean won(){
		return this.won;
	}
	
	public boolean cheating(){
		return this.cheat;
	}
	
	public void cheat(){
		this.cheat = true;
	}
	
	public void stopCheating(){
		this.cheat = false;	
		cheatError = "";
		cheatString = "";
	}
	
	public boolean isCircling() {
		return circling;
	}

	public void stopCircling() {
		this.circling = false;
		this.circle_width = 0;
		this.circle_height = 0;
	}
	
	public void startCircling(float x, float y){
		this.pcx = x;
		this.pcy = y;
		this.circling = true;
	}


}
