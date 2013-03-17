package glide;



import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

public class Glide {
	/* Dimensions for the game */
	public static final int WIDTH = 400; 
	public static final int HEIGHT = WIDTH / 12 * 9;
	public static final int SCALE = 2;
	
	/* Game Properties */
	public static final String TITLE = "Glide v0.3a";
	
	
	public static JFrame frame = new JFrame(TITLE);
	public static Game game;
	public static MainMenu mm;
	public static HTPMenu htp;
	public static boolean fullscreen = true;
	public static void main(String[] args){
		if(args.length > 0){
			if(args[0].equalsIgnoreCase("-windowed")){
				fullscreen = false;
			}else{
				
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
		
	}

}
