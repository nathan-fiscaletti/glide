package glide.entities;

import glide.Game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class Entity {
	public static final int TYPE_PLAYER = 1;
	public static final int TYPE_ENEMY = 2;
	public static final int TYPE_METEORBIG = 3;
	public static final int TYPE_METEORSMALL = 4;
	
	public static final int TYPE_BULLET = 6;
	public static final int TYPE_HEALTHBAR = 7;
	
	private double x;
	private double y;
	private int type;
	protected Game game;
	private BufferedImage entityImage;
	public Entity(double x, double y, Game game){
		this.x = x;
		this.y = y;
		this.game = game;
	}
	
	public void tick(){
		
	}
	
	public void render(Graphics g){
		g.drawImage(getEntityImage(), (int)getX(), (int)getY(), null);
	}
	
	//////////////////////////////////////////////
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public BufferedImage getEntityImage() {
		return entityImage;
	}

	public void setEntityImage(BufferedImage entityImage) {
		this.entityImage = entityImage;
	}
	
	
}
