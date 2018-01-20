package two.d.engine;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import kuusisto.tinysound.TinySound;
import two.d.engine.graphics.Textures;

public abstract class Engine {
	
	// Default dimensions
	private int width = 400; 
	private int height = width / 12 * 9;
	private int scale = 2;
	
	// Window properties
	public String windowTitle;
	public Image windowIcon;
	public Screen<?> currentScreen;
	
	private String iconUrl;
	private Textures textures;
	
	public boolean enableSounds = true;
	
	public LinkedList<Renderer> globalRenderers = new LinkedList<Renderer>();
	
	private JFrame windowFrame;
	
	public Engine(String title, String iconUrl, Textures textures)
	{
		this.windowTitle = title;
		this.iconUrl = iconUrl;
		this.textures = textures;
	}
	
	public final void start()
	{
		windowFrame = new JFrame(windowTitle);
		
		this.setTitle(this.windowTitle);
		
		windowFrame.setBackground(Color.BLACK);
		windowFrame.setSize(getWidth(), getHeight());
		windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windowFrame.setResizable(false);
		windowFrame.setLocationRelativeTo(null);
		windowFrame.setIconImage(new ImageIcon(getClass().getResource(iconUrl)).getImage());
		windowFrame.setVisible(true);
		
		/** Tiny Sound by finnkuusisto - https://github.com/finnkuusisto/TinySound */
		TinySound.init();
		
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
		windowFrame.getContentPane().setCursor(blankCursor);
		
		Entity.setTextures(textures);
		
		this.startEngine();
	}
	
	public abstract void startEngine();
	
	public final void setScreen(Screen<?> screen)
	{
		if (currentScreen != null) {
			try {
				currentScreen.stop();
			} catch (Exception e) {
				e.printStackTrace();
			}
			windowFrame.remove(currentScreen);
		}
		
		currentScreen = screen;
		currentScreen.setPreferredSize(new Dimension(getWidth(), getHeight()));
		currentScreen.setMaximumSize(new Dimension(getWidth(), getHeight()));
		currentScreen.setMinimumSize(new Dimension(getWidth(), getHeight()));
		windowFrame.add(currentScreen);
		windowFrame.pack();
		currentScreen.start();
	}
	
	public final int getWidth()
	{
		return this.width * this.scale;
	}
	
	public final int getHeight()
	{
		return this.height * this.scale;
	}
	
	public final void muteSounds(){
		this.enableSounds = false;
	}
	
	public final void unmuteSounds(){
		this.enableSounds = true;
	}
	
	public final void setTitle(String title)
	{
		this.windowFrame.setTitle(title);
	}
	
	public final void setScreenScale(int scale)
	{
		this.scale = (scale * 2);
	}
}
