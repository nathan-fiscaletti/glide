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

import javax.swing.JApplet;
import javax.swing.JFrame;


public class GlideApplet extends JApplet{
	/* Dimensions for the game */
	public static final int WIDTH = 400; 
	public static final int HEIGHT = WIDTH / 12 * 9;
	public static final int SCALE = 2;
	
	/* Game Properties */
	public static final String TITLE = "Glide v0.7a";
	
	
	public static Game game;
	public static MainMenu mm;
	public static HTPMenu htp;
	public static OptionsMenu op;
	public static boolean audio = true; 
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
	
	public static GlideApplet frame;
	
	public void init(){
		frame = this;
		GlideSystem.isApplet = true;
		mm = new MainMenu();
		mm.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		mm.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		mm.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		
		add(mm);
		setVisible(true);
		
		mm.start();
		
		GlideApplet.backgroundmusic.setVolume(-5.0f);
		GlideApplet.backgroundmusic.loop();
		
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

		// Create a new blank cursor.
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");

		// Set the blank cursor to the JFrame.
		frame.getContentPane().setCursor(blankCursor);
		
	}

}
