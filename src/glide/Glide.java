package glide;





import glide.soundsystem.Sound;
import glide.soundsystem.Sound2;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;


public class Glide {
	/* Dimensions for the game */
	public static final int WIDTH = 400; 
	public static final int HEIGHT = WIDTH / 12 * 9;
	public static final int SCALE = 2;
	
	/* Game Properties */
	public static final String TITLE = "Glide v0.9.11a";
	
	
	public static JFrame frame = new JFrame(TITLE);
	public static Game game;
	public static MainMenu mm;
	public static HTPMenu htp;
	public static OptionsMenu op;
	public static ControlsMenu cm;
	public static boolean sounds = true;
	public static boolean music = true;
	public static boolean fullscreen = true;
	
	/* sounds */
	public static final Sound explosion = new Sound("/sounds/explode.wav");
	public static final Sound hurt = new Sound("/sounds/hurt.wav");
	public static final Sound pickup = new Sound("/sounds/pickup.wav");
	public static final Sound shoot = new Sound("/sounds/shoot.wav");
	public static final Sound select = new Sound("/sounds/select.wav");
	public static final Sound enter = new Sound("/sounds/enter.wav");
	public static final Sound gameover = new Sound("/sounds/gameover.wav");
	public static final Sound dropdeath = new Sound("/sounds/dropexplode.wav");
	public static final Sound2 backgroundmusic = new Sound2("/sounds/cr.wav");
	
	
	
	public static void main(String[] args){
		if(args.length > 0){
			if(args[0].equalsIgnoreCase("-windowed")){
				fullscreen = false;
			}
		}	
		if(fullscreen){
			final Choice c = new Choice();
			Runnable r = new Runnable(){
				public void run(){
					c.init();
				}
			};
			Thread ct = new Thread(r);
			ct.start();
			while(!c.cont){
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		mm = new MainMenu();
		mm.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		mm.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		mm.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		frame = new JFrame(TITLE);
		frame.add(mm);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		mm.start();
		
		if(fullscreen){
			GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice vc = env.getDefaultScreenDevice();
			DisplayMode dm = new DisplayMode(800, 600, 16, DisplayMode.REFRESH_RATE_UNKNOWN);
			vc.setFullScreenWindow(frame);
			if(dm != null){
				try{
					vc.setDisplayMode(dm);
				}catch(Exception ex){
					
				}
			}
		}
		
		Glide.backgroundmusic.setVolume(-5.0f);
		Glide.backgroundmusic.loop();
		
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

		// Create a new blank cursor.
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");

		// Set the blank cursor to the JFrame.
		frame.getContentPane().setCursor(blankCursor);
		
	}

}
