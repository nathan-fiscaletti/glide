package glide.game.renderers;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import two.d.engine.Engine;
import two.d.engine.Renderer;
import two.d.engine.Vector;
import two.d.engine.graphics.BufferedImageLoader;

public class BackgroundRenderer extends Renderer {

	private Engine parentEngine;
	
	private BufferedImage blackImage;
	private BufferedImage backgroundOne = null;
	private BufferedImage backgroundTwo = null;
	
	private Vector backgroundOnePosition = Vector.Zero();
	private Vector backgroundTwoPosition = Vector.Zero();
	
	private int speed = 1;
	
	public BackgroundRenderer(String backgroundImagePath, Engine engine)
	{
		this.blackImage = Renderer.blackImage(engine);
		try{
			backgroundOne = BufferedImageLoader.load(backgroundImagePath);
			backgroundTwo = backgroundOne;
			backgroundTwoPosition.plusY(-backgroundOne.getHeight());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		this.parentEngine = engine;
	}
	
	@Override
	public void render(Graphics g, Canvas canvas) {
		g.drawImage(this.blackImage, 0, 0, parentEngine.getWidth(), parentEngine.getHeight(), canvas);
		
		if(backgroundOnePosition.y >= Vector.Max(parentEngine).y){
			backgroundOnePosition.y = backgroundTwoPosition.y - backgroundTwo.getHeight();
		}
		
		if(backgroundTwoPosition.y >= Vector.Max(parentEngine).y){
			backgroundTwoPosition.y = backgroundOnePosition.y - backgroundTwo.getHeight();
		}
		
		g.drawImage(backgroundOne, 0, (int)backgroundOnePosition.y, canvas);
		g.drawImage(backgroundTwo, 0, backgroundTwoPosition.y, canvas);
	}
	
	@Override
	public void update()
	{
		backgroundOnePosition = backgroundOnePosition.plusY(speed);
		backgroundTwoPosition = backgroundTwoPosition.plusY(speed);
	}
	
	public void setSpeed(int speed)
	{
		this.speed = speed;
	}

}
