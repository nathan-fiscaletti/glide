package glide;





import glide.soundsystem.Sound;
import glide.soundsystem.Sound2;
import glide.spritehandles.BufferedImageLoader;
import glide.versioning.Version;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class Glide {
	/* Dimensions for the game */
	public static final int WIDTH = 400; 
	public static final int HEIGHT = WIDTH / 12 * 9;
	public static final int SCALE = 2;
	
	/* Game Properties */
	public static final String version = Version.getAppVersion() + " [Build: " + Version.getAppBuild() + "]";
	public static String TITLE = "Glide - " + version;
	
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
	
	/* Updater */
	public static String update = "You are running the latest version!";
	public static String update2 = "";
	public static Color update_color = Color.GREEN;
	public static boolean checkedForUpdate = false;
	
	/* Loading Status */
	public static JLabel loading = new JLabel("Loading.. ");
	public static LayoutManager correctLayout = null;
	
	/* Scrolling Background */
	public static BufferedImage background = null;
	public static BufferedImage background2 = null;
	public static float b1y = 0;
	public static float b2y = 0;
	public static int backgroundSpeed = 1;
	
	
	
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
					e.printStackTrace();
				}
			}
		}
		BufferedImageLoader loader = new BufferedImageLoader();
		try{
			background = loader.loadImage("/images/mm_b.png");
			background2 = background;
			b2y = -background.getHeight();
		}catch(Exception e){
			
		}
		
		mm = new MainMenu();
		mm.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		mm.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		mm.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		
		
		
		frame = new JFrame(TITLE + " - Loading..");
		frame.setBackground(Color.BLACK);
		frame.add(mm);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setIconImage(new ImageIcon(Glide.class.getClass().getResource("/images/icon.png")).getImage());
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
				}catch(Exception ex){}
			}
		}else{
			
			
			
		}
		
		Glide.backgroundmusic.setVolume(-25.0f);
		Glide.backgroundmusic.loop();
		
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

		// Create a new blank cursor.
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");

		// Set the blank cursor to the JFrame.
		frame.getContentPane().setCursor(blankCursor);
		
	}
	
	public static void muteMusic(){
		Glide.music = false;
		Glide.backgroundmusic.setVolume(-1000.0f);
	}
	public static void unmuteMusic(){
		Glide.music = true;
		Glide.backgroundmusic.setVolume(-25.0f);
	}
	
	public static void muteSounds(){
		Glide.sounds = false;
	}
	public static void unmuteSounds(){
		Glide.sounds = true;
	}

}
