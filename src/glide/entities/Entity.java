package glide.entities;

import glide.Game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class Entity {
	public static enum Type {
		//Drop Types
		HEALTHPACK,BEAM,DIAMOND,PLASMA,MDB,DIAMOND2,DIAMOND3,COD,
		//Entity Types
		PLAYER,ENEMY,METEORBIG,METEORSMALL,BULLET,MULTIDIRECTIONALBULLET,HEALTHBAR,ENEMYBULLET,PLASMAPLAYER;
	}
	
	private double x;
	private double y;
	private Entity.Type type;
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
	
	public Entity.Type getType() {
		return type;
	}
	public void setType(Entity.Type type) {
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
