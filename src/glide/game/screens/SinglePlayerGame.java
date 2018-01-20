package glide.game.screens;

import glide.game.GlideEngine;
import glide.game.GlideTextures;
import glide.game.entities.Bullet;
import glide.game.entities.Enemy;
import glide.game.entities.HealthBar;
import glide.game.entities.Meteor;
import glide.game.entities.Plasma;
import glide.game.entities.Player;
import glide.game.entities.Enemy.ProtectorType;
import glide.game.entitycontrollers.SinglePlayerGameController;
import two.d.engine.Entity;
import two.d.engine.Screen;
import two.d.engine.Vector;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

public class SinglePlayerGame extends Screen<GlideEngine> {

	private static final long serialVersionUID = -4093553489357496142L;
	
	/* Cheats */
		public String cheatString = "";
		public String cheatError = "";
	/* End Cheats */
		
	/* Game Properties */
	private Player player;
	private HealthBar healthBar;
	private Plasma plasmae;
	
	private int score = 0;
	private boolean paused = false;
	private boolean status = true;
	private boolean lost = false;
	private boolean won = false;
	private boolean cheat = false;
	public boolean isPlasmaActive = false;
	
	/* Multi Directional Bullets / Bombs caught - Counters */
	public int mdbs = 5;
	public int boc = 0;
	
	/* Bomb Spawning */
	public int bsc = 0;
	
	
	private int level = 1;
	
	/* Giant Circle Shit */
	public int cods = 2;
	public int max_cods = 2;
	public boolean isCircling = false;
	public Shape circle = new Ellipse2D.Float();
	float circle_width = 0;
	float circle_height = 0;
	float circle_x = (this.parentEngine.getWidth() / 2) - (circle_width / 2);
	float circle_y = (this.parentEngine.getHeight() / 2) - (circle_height / 2);
	float pcx = 0;
	float pcy = 0;
	
	/* Power Ups */
	public int shield = 0;
	public int beam = 0;
	
	/* Initial CountDown */
	private String cntdwn_string = "... Prepare to launch! ...";
	private int cntdwn_ticks = 5;
	private boolean cntdwn_done = false;
	
	int adde = -60;
	int addm = 0;
	int lvlup = 0;
	int cntDwnT = 0;
	
	int curlvl = 120;
	
	/* End Countdown Control */

	public SinglePlayerGame(GlideEngine engine) {
		super(engine);
	}
	
	@Override
	public void initialize()
	{
		this.name = "SinglePlayerGame";
		
		player = new Player(new Vector((this.parentEngine.getWidth() / 2) - 16, this.parentEngine.getHeight() - 104), this);
		controller = new SinglePlayerGameController(this);
		healthBar = new HealthBar(new Vector((this.getWidth() / 2) - (Entity.getTextures(GlideTextures.class).healthbar1.getWidth() / 2), this.getHeight() - (Entity.getTextures(GlideTextures.class).healthbar1.getHeight() + 10)), this);
		plasmae = new Plasma(new Vector((this.parentEngine.getWidth() / 2) - 16, this.parentEngine.getHeight() - 52), this);
		
		this.parentEngine.backgroundRenderer.setSpeed(7);
		
		addFocusListener(new FocusListener(){
			@Override
			public void focusLost(FocusEvent arg0) {
				if(!won() && !lost() && !cheating()){
					pause();
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				// Don't implement
			}
		});
	}
	
	@Override
	protected void update()
	{
		// Count Down
		if(!cntdwn_done){
			cntDwnT ++;
			if(cntDwnT == 60){	
				if(cntdwn_ticks == 0){
					cntdwn_string = "";
					cntdwn_done = true;
				}else if(cntdwn_ticks == 1){
					cntdwn_string = "Go!";
					cntdwn_ticks --;
				}else{
					cntdwn_ticks --;
				}
				cntDwnT = 0;
			}
		}
		
		
		this.parentEngine.logoRenderer.shouldRenderWhenGlobal = (isPaused() || lost() || won() || cheating() || !cntdwn_done);
		
		
		if(!isPaused() && !lost() && !won() && !cheating()){
			player.performUpdate();
			this.controller.update();
			plasmae.performUpdate();
			healthBar.performUpdate();
			lvlup ++;
			adde++;
			addm ++;
			if(adde == curlvl){
				if(cntdwn_done){
					controller.spawnEntity(new Enemy(new Vector(getController().random.nextInt(this.parentEngine.getWidth()), -5), this, getController().random.nextBoolean(), false, false, ProtectorType.None));
				}
				adde = 0;
			}
			if(addm == 45){
				if(cntdwn_done){
					controller.spawnEntity(new Meteor(new Vector(controller.random.nextInt(this.parentEngine.getWidth()), -5), this));
				}
				addm = 0;
			}
			if(lvlup == 1800){
				if(cntdwn_done){
					this.getController().spawnBomb();
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
				curlvl = updateLevel(curlvl);
				adde = 0;
				addm = 0;
			}
			if(this.isCircling && (cntdwn_done)){
				circle_width = circle_width + 20;
				circle_height = circle_width + 20;
				circle_x = pcx - (circle_width / 2);
				circle_y = pcy - (circle_height / 2);
				if(circle_width > this.parentEngine.getWidth() * 2){
					stopCircling();
				}
			}
		}
	}
	
	@Override
	protected void renderFrame(Graphics graphics)
	{
		player.render(graphics, this);
		if(isPlasmaActive){
			plasmae.position = player.position;
			plasmae.render(graphics, this);
		}
		controller.render(graphics, this);
		
		// Count Down
		graphics.setFont(new Font("Ariel", Font.BOLD, 24));
		graphics.setColor(Color.GREEN);
		graphics.drawChars(cntdwn_string.toCharArray(), 0, cntdwn_string.toCharArray().length, (this.parentEngine.getWidth() / 2) - (graphics.getFontMetrics().stringWidth(cntdwn_string) / 2), (this.parentEngine.getHeight() / 2) - 24);
		graphics.setFont(new Font("Ariel", Font.BOLD, 36));
		graphics.setColor(Color.ORANGE);
		if(!(cntdwn_ticks == 0)){
			graphics.drawChars(Integer.toString(cntdwn_ticks).toCharArray(), 0, Integer.toString(cntdwn_ticks).toCharArray().length, (this.parentEngine.getWidth() / 2) - (graphics.getFontMetrics().stringWidth(Integer.toString(cntdwn_ticks)) / 2), (this.parentEngine.getHeight() / 2) + 20);
		}
		
		// health bar
		graphics.setFont(new Font("Ariel", Font.BOLD, 24));
		healthBar.render(graphics, this);

		/* HUD Container */
		int wid1 = this.parentEngine.getWidth() - 20;
		int hit1 = (this.parentEngine.getHeight() - 60);
		int rectx1 = 10;
		int recty1 = this.parentEngine.getHeight() - 50;
		graphics.setColor(Color.WHITE);		
		Graphics2D g2d = (Graphics2D)graphics;
		Stroke os = g2d.getStroke();
		g2d.setStroke(new BasicStroke(5));
		g2d.drawRoundRect(rectx1, recty1, wid1, hit1, 10, 10);
		g2d.setStroke(os);
		graphics.setColor(Color.GREEN);
		/* End HUD Container */
		
		/* MDBs */
		int out = 32;
		for(int i = 0;i < mdbs;i++){
			graphics.drawImage(Entity.getTextures(GlideTextures.class).mdbullet, out, (this.parentEngine.getHeight() - 40), null);
			out = out + 32;
		}
		if(mdbs == 5){
			graphics.drawImage(Entity.getTextures(GlideTextures.class).max, 192, (this.parentEngine.getHeight() - 40), null);
		}
		
		/* CODs */
		out = 257;
		for(int i = 0;i < cods; i++){
			graphics.drawImage(Entity.getTextures(GlideTextures.class).cod, out, (this.parentEngine.getHeight() - 40), null);
			out = out + 32;
		}
		if(cods == 2){
			graphics.drawImage(Entity.getTextures(GlideTextures.class).max_cod, 321, (this.parentEngine.getHeight() - 40), null);
		}
		
		/* Lvl / Score */
		graphics.setFont(new Font("Ariel", Font.BOLD, 18));
		int lvli = level;
		String lvl = "Level: " + lvli;
		String bombDeployed = (this.getController().isBombSpawned()) ? "!!B!!" : "";
		String sco = "Score: " + score;
		
		graphics.setColor(Color.RED);
		int bdx = (this.parentEngine.getWidth() - ((graphics.getFontMetrics().stringWidth(sco) * 3)));
		bdx = this.parentEngine.getWidth() - bdx;
		bdx = this.parentEngine.getWidth() - bdx / 2;
		bdx = bdx - graphics.getFontMetrics().stringWidth(bombDeployed);
		bdx = bdx + graphics.getFontMetrics().stringWidth(bombDeployed)/4;
		graphics.drawChars(bombDeployed.toCharArray(), 0, bombDeployed.toCharArray().length, bdx, this.parentEngine.getHeight() - 87);
		
		graphics.setColor(Color.ORANGE);
		graphics.drawChars(lvl.toCharArray(), 0, lvl.toCharArray().length, this.parentEngine.getWidth() - (graphics.getFontMetrics().stringWidth(lvl) * 2) + 50, this.parentEngine.getHeight() - 67);
		graphics.setColor(Color.GREEN);
		
		graphics.drawChars(sco.toCharArray(), 0, sco.toCharArray().length, this.parentEngine.getWidth() - (graphics.getFontMetrics().stringWidth(sco) * 3), this.parentEngine.getHeight() - 67);
		
		
		/* Bomb Counter */
		String c = String.valueOf(boc); 
		
		graphics.drawChars(c.toCharArray(), 0, c.toCharArray().length, 32, this.parentEngine.getHeight() - 67);
		graphics.drawImage(Entity.getTextures(GlideTextures.class).bombsp, 32 + graphics.getFontMetrics().stringWidth(c), this.parentEngine.getHeight() - 92, null);
		
		/* Power Ups */
		int statsoffset = 64 + graphics.getFontMetrics().stringWidth(c);
		graphics.setFont(new Font("Ariel", Font.BOLD, 24));
		if(this.getPlayer().beaming){
			BufferedImage beamer = Entity.getTextures(GlideTextures.class).beam;
			BufferedImage level = Entity.getTextures(GlideTextures.class).powerbar1;
			if(beam == 1){
				level = Entity.getTextures(GlideTextures.class).powerbar5;
			}else if(beam == 2){
				level = Entity.getTextures(GlideTextures.class).powerbar4;
			}else if(beam == 3){
				level = Entity.getTextures(GlideTextures.class).powerbar3;
			}else if(beam == 4){
				level = Entity.getTextures(GlideTextures.class).powerbar2;
			}else if(beam == 5){
				level = Entity.getTextures(GlideTextures.class).powerbar1;
			}
			
			graphics.drawImage(beamer, 32 + statsoffset, this.parentEngine.getHeight() - 92, null);
			graphics.drawImage(level, 32 + 34 + statsoffset, this.parentEngine.getHeight() - 92, null);
		}else{
			BufferedImage beamer = Entity.getTextures(GlideTextures.class).grayscale_beam;
			graphics.drawImage(beamer, 32 + statsoffset, this.parentEngine.getHeight() - 92, null);
		}
		
		if(this.isPlasmaActive && this.getPlayer().plasma){
			BufferedImage shielded = Entity.getTextures(GlideTextures.class).plasma;
			BufferedImage level = Entity.getTextures(GlideTextures.class).powerbar1;
			if(shield == 1){
				level = Entity.getTextures(GlideTextures.class).powerbar5;
			}else if(shield == 2){
				level = Entity.getTextures(GlideTextures.class).powerbar4;
			}else if(shield == 3){
				level = Entity.getTextures(GlideTextures.class).powerbar3;
			}else if(shield == 4){
				level = Entity.getTextures(GlideTextures.class).powerbar2;
			}else if(shield == 5){
				level = Entity.getTextures(GlideTextures.class).powerbar1;
			}
			
			graphics.drawImage(shielded, 100 + statsoffset, this.parentEngine.getHeight() - 92, null);
			graphics.drawImage(level, 134 + statsoffset, this.parentEngine.getHeight() - 92, null);
		}else{
			BufferedImage shielded = Entity.getTextures(GlideTextures.class).grayscale_sheild;
			graphics.drawImage(shielded, 100 + statsoffset, this.parentEngine.getHeight() - 92, null);
		}
		
		
		/* status */
		//if(isStatus()){
		//	int bz = getController().b.size() + getController().eb.size() + getController().mdb.size();
		//	String status = "MOS: " + (getController().meteors.size() + getController().small_meteors.size())  + " DOS: "+getController().drops.size()+" - BOS: "+bz+" - EOS: " + getController().e.size() + " - TPS: " + getTps() + " -  FPS: " + getFps();
		//	g.setFont(new Font("Ariel", Font.BOLD, 10));
		//	g.setColor(Color.ORANGE);
		//	g.drawChars(status.toCharArray(), 0, status.toCharArray().length, ((Glide.WIDTH * Glide.SCALE)) - (g.getFontMetrics().stringWidth(status)) - 40, ((Glide.HEIGHT * Glide.SCALE)) + (g.getFontMetrics().getDescent()) - 22);
		//}
		
		/*//////////////// MENUS / DIALOGS ////////////////////*/
		
		/* pause menu */
		if(isPaused()){
			String pause = "Press 'Escape' to resume";
			String pause2 = "Press 'q' to return to Main Menu";
			String sounds = "Press 'z' to " + ((this.parentEngine.enableSounds) ? "disable" : "enable") + " sounds";
			String music = "Press 'c' to " + ((this.parentEngine.enableMusic) ? "disable" : "enable") + " music";
			
			graphics.setFont(new Font("Ariel", Font.BOLD, 36));
			graphics.drawChars(pause.toCharArray(), 0, pause.toCharArray().length, (this.parentEngine.getWidth() / 2) - (graphics.getFontMetrics().stringWidth(pause) / 2), (this.parentEngine.getHeight() / 2) + (graphics.getFontMetrics().getDescent() - 38));
			graphics.drawChars(pause2.toCharArray(), 0, pause2.toCharArray().length, (this.parentEngine.getWidth() / 2) - (graphics.getFontMetrics().stringWidth(pause2) / 2), (this.parentEngine.getHeight() / 2) + (graphics.getFontMetrics().getDescent()));
			graphics.setFont(new Font("Ariel", Font.BOLD, 18));
			graphics.drawChars(sounds.toCharArray(), 0, sounds.toCharArray().length, (this.parentEngine.getWidth() / 4) - (graphics.getFontMetrics().stringWidth(sounds) / 4) - 20, (this.parentEngine.getHeight() / 2) + (graphics.getFontMetrics().getDescent() + 100));
			graphics.drawChars(music.toCharArray(), 0, music.toCharArray().length, (this.parentEngine.getWidth() - this.parentEngine.getWidth() / 4) - (graphics.getFontMetrics().stringWidth(music) - (graphics.getFontMetrics().stringWidth(pause2) / 4)), (this.parentEngine.getHeight() / 2) + (graphics.getFontMetrics().getDescent() + 100));
			graphics.setFont(new Font("Ariel", Font.BOLD, 24));
		}
		
		/* lost */
		if(lost()){
			graphics.setFont(new Font("Ariel", Font.BOLD, 12));
			String lose = "Game Over!";
			String lose2 = "Score: " + score;
			String lose3 = "Press 'Enter' to start over";
			String lose4 = "Press 'q' to return to Main Menu";
			String lose5 = "Level: " + lvli;
			String lose6 = "Difficulty: " + this.parentEngine.difficulty.toString();
			//Lose border
			int wid = (graphics.getFontMetrics().stringWidth(lose4) + 4);
			int hit = (graphics.getFontMetrics().getDescent()) + 52 + 26 + 118 + 4 + 26;
			int rectx = (this.parentEngine.getWidth() / 2) - (graphics.getFontMetrics().stringWidth(lose) / 2) + 60;
			int recty = (this.parentEngine.getHeight() / 2) + (graphics.getFontMetrics().getDescent() - 118) - 20;
			graphics.setColor(Color.ORANGE);
			graphics.drawRoundRect(wid, hit, rectx, recty, 10, 10);
			
			
			graphics.setFont(new Font("Ariel", Font.BOLD, 42));
			graphics.setColor(Color.RED);
			graphics.drawChars(lose.toCharArray(), 0, lose.toCharArray().length, (this.parentEngine.getWidth() / 2) - (graphics.getFontMetrics().stringWidth(lose) / 2), (this.parentEngine.getHeight() / 2) + (graphics.getFontMetrics().getDescent() - 118));
			graphics.setFont(new Font("Ariel", Font.BOLD, 24));
			graphics.setColor(Color.ORANGE);
			graphics.drawChars(lose2.toCharArray(), 0, lose2.toCharArray().length, (this.parentEngine.getWidth() / 2) - (graphics.getFontMetrics().stringWidth(lose2) / 2), (this.parentEngine.getHeight() / 2) + (graphics.getFontMetrics().getDescent() - 52 + 10));
			
			graphics.drawChars(lose5.toCharArray(), 0, lose5.toCharArray().length, (this.parentEngine.getWidth() / 2) - (graphics.getFontMetrics().stringWidth(lose5) / 2), (this.parentEngine.getHeight() / 2) + (graphics.getFontMetrics().getDescent() - 26 + 10));
			graphics.drawChars(lose6.toCharArray(), 0, lose6.toCharArray().length, (this.parentEngine.getWidth() / 2) - (graphics.getFontMetrics().stringWidth(lose6) / 2), (this.parentEngine.getHeight() / 2) + (graphics.getFontMetrics().getDescent() + 10));
			graphics.drawChars(lose3.toCharArray(), 0, lose3.toCharArray().length, (this.parentEngine.getWidth() / 2) - (graphics.getFontMetrics().stringWidth(lose3) / 2), (this.parentEngine.getHeight() / 2) + (graphics.getFontMetrics().getDescent() + 26 + 10));
			graphics.drawChars(lose4.toCharArray(), 0, lose4.toCharArray().length, (this.parentEngine.getWidth() / 2) - (graphics.getFontMetrics().stringWidth(lose4) / 2), (this.parentEngine.getHeight() / 2) + (graphics.getFontMetrics().getDescent() + 52 + 10));
			graphics.setFont(new Font("Ariel", Font.BOLD, 24));
		}
		
		/* won */
		if(won()){
			graphics.setFont(new Font("Ariel", Font.BOLD, 12));
			String lose = "You win!";
			String lose2 = "Score: " + score;
			String lose3 = "Press 'Enter' to start over";
			String lose4 = "Press 'q' to return to Main Menu";
			String lose5 = "Level: " + lvli;
			String lose6 = "Difficulty: " + this.parentEngine.difficulty.toString();
			//Lose border
			int wid = (graphics.getFontMetrics().stringWidth(lose4) + 4);
			int hit = (graphics.getFontMetrics().getDescent()) + 52 + 26 + 118 + 4 + 26;
			int rectx = (this.parentEngine.getWidth() / 2) - (graphics.getFontMetrics().stringWidth(lose) / 2) + 60;
			int recty = (this.parentEngine.getHeight() / 2) + (graphics.getFontMetrics().getDescent() - 118) - 20;
			graphics.setColor(Color.ORANGE);
			graphics.drawRoundRect(wid, hit, rectx, recty, 10, 10);
			
			
			graphics.setFont(new Font("Ariel", Font.BOLD, 42));
			graphics.setColor(Color.GREEN);
			graphics.drawChars(lose.toCharArray(), 0, lose.toCharArray().length, (this.parentEngine.getWidth() / 2) - (graphics.getFontMetrics().stringWidth(lose) / 2), (this.parentEngine.getHeight() / 2) + (graphics.getFontMetrics().getDescent() - 118));
			graphics.setFont(new Font("Ariel", Font.BOLD, 24));
			graphics.setColor(Color.ORANGE);
			graphics.drawChars(lose2.toCharArray(), 0, lose2.toCharArray().length, (this.parentEngine.getWidth() / 2) - (graphics.getFontMetrics().stringWidth(lose2) / 2), (this.parentEngine.getHeight() / 2) + (graphics.getFontMetrics().getDescent() - 52 + 10));
			
			graphics.drawChars(lose5.toCharArray(), 0, lose5.toCharArray().length, (this.parentEngine.getWidth() / 2) - (graphics.getFontMetrics().stringWidth(lose5) / 2), (this.parentEngine.getHeight() / 2) + (graphics.getFontMetrics().getDescent() - 26 + 10));
			graphics.drawChars(lose6.toCharArray(), 0, lose6.toCharArray().length, (this.parentEngine.getWidth() / 2) - (graphics.getFontMetrics().stringWidth(lose6) / 2), (this.parentEngine.getHeight() / 2) + (graphics.getFontMetrics().getDescent() + 10));
			graphics.drawChars(lose3.toCharArray(), 0, lose3.toCharArray().length, (this.parentEngine.getWidth() / 2) - (graphics.getFontMetrics().stringWidth(lose3) / 2), (this.parentEngine.getHeight() / 2) + (graphics.getFontMetrics().getDescent() + 26 + 10));
			graphics.drawChars(lose4.toCharArray(), 0, lose4.toCharArray().length, (this.parentEngine.getWidth() / 2) - (graphics.getFontMetrics().stringWidth(lose4) / 2), (this.parentEngine.getHeight() / 2) + (graphics.getFontMetrics().getDescent() + 52 + 10));
			graphics.setFont(new Font("Ariel", Font.BOLD, 24));
		}
		
		/* cheat menu */
		if(cheating()){
			
			String Title = "Enter Cheat Code";
			String message = "Press 'enter' to submit cheat. Press 'escape' to return to game.";
			graphics.setFont(new Font("Ariel", Font.BOLD, 24));
			
			graphics.setColor(Color.ORANGE);
			graphics.drawChars(Title.toCharArray(), 0, Title.toCharArray().length, (this.parentEngine.getWidth() / 2) - (graphics.getFontMetrics().stringWidth(Title) / 2), (this.parentEngine.getHeight() / 2) + (graphics.getFontMetrics().getDescent() - 40));
			graphics.setColor(Color.GREEN);
			graphics.setFont(new Font("Ariel", Font.BOLD, 12));
			graphics.drawChars(message.toCharArray(), 0, message.toCharArray().length, (this.parentEngine.getWidth() / 2) - (graphics.getFontMetrics().stringWidth(message) / 2), (this.parentEngine.getHeight() / 2) + (graphics.getFontMetrics().getDescent() - 20));
			graphics.setColor(Color.RED);
			graphics.drawChars(cheatError.toCharArray(), 0, cheatError.toCharArray().length, (this.parentEngine.getWidth() / 2) - (graphics.getFontMetrics().stringWidth(cheatError) / 2), (this.parentEngine.getHeight() / 2) + (graphics.getFontMetrics().getDescent()));
			graphics.setFont(new Font("Ariel", Font.BOLD, 12));
			graphics.setColor(Color.YELLOW);
			graphics.drawChars(cheatString.toCharArray(), 0, cheatString.toCharArray().length, (this.parentEngine.getWidth() / 2) - (graphics.getFontMetrics().stringWidth(cheatString) / 2), (this.parentEngine.getHeight() / 2) + (graphics.getFontMetrics().getDescent() + 20));
		}
		
		graphics.setColor(Color.RED);
		
		if(this.isCircling){
			graphics.setColor(Color.GREEN);
			circle = new Ellipse2D.Float(circle_x, circle_y, circle_width, circle_height);
			((Graphics2D)graphics).draw(circle);
			graphics.setColor(Color.RED);
		}

	}
	
	@Override
	public void onKeyPressed(KeyEvent e){
		int key = e.getKeyCode();
		if(cntdwn_done){
			if(key == KeyEvent.VK_UP){
				if(!isPaused() && !lost() && !cheating()){
					this.getPlayer().velocity.y = -getPlayer().speed;
				}
			}else if(key == KeyEvent.VK_LEFT){
				if(!isPaused() && !lost() && !cheating()){
					this.getPlayer().velocity.x = -getPlayer().speed;
				}
			}else if(key == KeyEvent.VK_DOWN){
				if(!isPaused() && !lost() && !cheating()){
					this.getPlayer().velocity.y = getPlayer().speed;
				}
			}else if(key == KeyEvent.VK_RIGHT){
				if(!isPaused() && !lost() && !cheating()){	
					this.getPlayer().velocity.x = getPlayer().speed;
				}
			}else if(key == KeyEvent.VK_Z){
				if(!isPaused() && !lost() && !cheating()){	
					this.getPlayer().velocity.x = -Player.boostSpeed;
				}else if(isPaused() && !lost() && ! cheating()){
					if(this.parentEngine.enableSounds){
						this.parentEngine.muteSounds();
					}else{
						this.parentEngine.unmuteSounds();
					}
				}
			}else if(key == KeyEvent.VK_C){ 
				if(!isPaused() && !lost() && !cheating()){	
					this.getPlayer().velocity.x = Player.boostSpeed;
				}else if(isPaused() && !lost() && ! cheating()){
					if(this.parentEngine.enableMusic){
						this.parentEngine.sounds.s_backgroundmusic.pause();
					}else{
						this.parentEngine.sounds.s_backgroundmusic.play();
					}
				}
			}else if(key == KeyEvent.VK_SPACE && !getPlayer().shooting){
				if(!isPaused() && !lost() && !cheating()){
					if(!getPlayer().beaming){
						this.controller.spawnEntity(new Bullet(player.position.plusY(-32), this));
						getPlayer().shooting = true;
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
						this.parentEngine.backgroundRenderer.setSpeed(1);
						this.parentEngine.setScreen(new MainMenu(this.parentEngine));
				}
			}else if(key == KeyEvent.VK_X){
				if(!won() && !lost() && !isPaused() && !cheating())
				if(mdbs > 0){
					getController().shootMDB(this.getPlayer().position);
					mdbs--;
				}
			}else if(key == KeyEvent.VK_ENTER){
				if((lost() || won()) && !cheating()){
					this.getController().deSpawnAllEntities();
					stopCircling();
					getHealthBar().health = 8;
					this.score = 0;
					mdbs = 5;
					cods = 2;
					setPlayer(new Player(new Vector((this.parentEngine.getWidth() / 2) - 16, this.parentEngine.getHeight() - 104), this));
					boc = 0;
					level = 1;
					curlvl = 120;
					restartAfterLost();
				}else if(!cheating() && !won() && !lost() && !isPaused()){
					cheat();
				}else if(cheating()){
					if(cheatString.equalsIgnoreCase("20120614")){
						cheatString = "";
						if(this.parentEngine.cheats.beam_cheat){
							cheatError = "De-Activated Beam Cheat!";
							this.parentEngine.cheats.beam_cheat = false;
						}else{
							cheatError = "Activated Beam Cheat!";
							this.parentEngine.cheats.beam_cheat = true;
						}
					}else if(cheatString.equalsIgnoreCase("19951122")){
						cheatString = "";
						if(this.parentEngine.cheats.shield_cheat){
							cheatError = "De-Activated Shield Cheat!";
							this.parentEngine.cheats.shield_cheat = false;
						}else{
							cheatError = "Activated Shield Cheat!";
							this.parentEngine.cheats.shield_cheat = true;
						}
					}else if(cheatString.equalsIgnoreCase("11232013")){
						cheatString = "";
						if(this.parentEngine.cheats.health_cheat){
							cheatError = "Deactivated Health Cheat!";
							this.parentEngine.cheats.health_cheat = false;
						}else{
							cheatError = "Activated Health Cheat!";
							this.parentEngine.cheats.health_cheat = true;
						}
					}else if(cheatString.equalsIgnoreCase("26435")){
						cheatString = "";
						if(this.parentEngine.cheats.mdb_cheat){
							cheatError = "Deactivated MDB Cheat!";
							this.parentEngine.cheats.mdb_cheat = false;
						}else{
							cheatError = "Activated MDB Cheat!";
							this.parentEngine.cheats.mdb_cheat = true;
						}
					}else if(cheatString.equalsIgnoreCase("4568353669")){
						cheatString="";
						if(this.parentEngine.cheats.cod_cheat){
							cheatError = "Deactivated Sweeper-Detonator Cheat!";
							this.parentEngine.cheats.cod_cheat = false;
						}else{
							cheatError = "Activated Sweeper-Detonator Cheat!";
							this.parentEngine.cheats.cod_cheat = true;
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
				if(cods > 0 && !this.isCircling){
					startCircling(this.getPlayer().position);
					cods -=1;
				}
			}
		}
	}
	
	@Override
	public void onKeyReleased(KeyEvent e){
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_UP){
			if(!isPaused()  && !lost() && !cheating()){
				this.getPlayer().velocity.y = 0;
			}
		}else if(key == KeyEvent.VK_LEFT){
			if(!isPaused()  && !lost() && !cheating()){
				this.getPlayer().velocity.x = 0;
			}
		}else if(key == KeyEvent.VK_DOWN){
			if(!isPaused()  && !lost() && !cheating()){
				this.getPlayer().velocity.y = 0;
			}
		}else if(key == KeyEvent.VK_RIGHT){
			if(!isPaused()  && !lost() && !cheating()){
				this.getPlayer().velocity.x = 0;
			}
		}else if(key == KeyEvent.VK_Z){
			if(!isPaused() && !lost() && !cheating()){	
				this.getPlayer().velocity.x = 0;
			}
		}else if(key == KeyEvent.VK_C){ 
			if(!isPaused() && !lost() && !cheating()){	
				this.getPlayer().velocity.x = 0;
			}
		}else if(key == KeyEvent.VK_SPACE){
			if(!isPaused()  && !lost() && !cheating()){
				if(!getPlayer().beaming){
					getPlayer().shooting = false;
				}
			}
		}
	}
	
	public int getScore()
	{
		return this.score;
	}
	
	public void setScore(int score)
	{
		this.score = score;
	}
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player p) {
		this.player = p;
	}
	
	public HealthBar getHealthBar() {
		return healthBar;
	}

	public void setHealthBar(HealthBar healthBar) {
		this.healthBar = healthBar;
	}

	public SinglePlayerGameController getController() {
		return (SinglePlayerGameController)this.controller;
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
		this.parentEngine.sounds.s_gameover.play(this.parentEngine);
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

	public void stopCircling() {
		this.isCircling = false;
		this.circle_width = 0;
		this.circle_height = 0;
	}
	
	public void startCircling(Vector startPosition){
		this.stopCircling();
		this.pcx = startPosition.x;
		this.pcy = startPosition.y;
		circle_width = 0;
		circle_height = 0;
		circle_x = (this.parentEngine.getWidth() / 2) - (circle_width / 2);
		circle_y = (this.parentEngine.getHeight() / 2) - (circle_height / 2);
		this.isCircling = true;
	}
	
	private int updateLevel(int in){
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
}
