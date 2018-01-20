package glide.game.renderers;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import glide.engine.Renderer;
import glide.engine.Vector;
import glide.engine.graphics.BufferedImageLoader;
import glide.game.Glide;

public class BackgroundRenderer extends Renderer {

	private BufferedImage blackImage;
	private BufferedImage backgroundOne = null;
	private BufferedImage backgroundTwo = null;
	
	private Vector backgroundOnePosition = Vector.Zero();
	private Vector backgroundTwoPosition = Vector.Zero();
	
	private int speed = 1;
	
	public BackgroundRenderer(String backgroundImagePath)
	{
		this.blackImage = Renderer.blackImage();
		try{
			backgroundOne = BufferedImageLoader.load(backgroundImagePath);
			backgroundTwo = backgroundOne;
			backgroundTwoPosition.plusY(-backgroundOne.getHeight());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void render(Graphics g, Canvas canvas) {
		g.drawImage(this.blackImage, 0, 0, Glide.getWidth(), Glide.getHeight(), canvas);
		
		if(backgroundOnePosition.y >= Vector.Max().y){
			backgroundOnePosition.y = backgroundTwoPosition.y - backgroundTwo.getHeight();
		}
		
		if(backgroundTwoPosition.y >= Vector.Max().y){
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
