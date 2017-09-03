package glide.entities;

import glide.SinglePlayerGame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;


public class Entity {
	
	//Static enumerators
	public static enum Type {
		//Drop Types
		HEALTHPACK,BEAM,DIAMOND,PLASMA,MDB,DIAMOND2,DIAMOND3,COD,
		//Entity Types
		PLAYER,ENEMY,METEORBIG,METEORSMALL,BULLET,MULTIDIRECTIONALBULLET,HEALTHBAR,ENEMYBULLET,PLASMAPLAYER;
	}
	
	
	
	//Declares
	private double x;
	private double y;
	private Entity.Type type;
	protected SinglePlayerGame game;
	private BufferedImage entityImage;
	protected Random random;
	
	
	//Constructor
	public Entity(double x, double y, SinglePlayerGame game){
		this.x = x;
		this.y = y;
		this.game = game;
		this.random = new Random();
	}
	
	
	
	//Render & tick
	public void tick(){}
	
	public void render(Graphics g){
		g.drawImage(this.getEntityImage(), (int)getX(), (int)getY(), null);
	}
	
	
	
	/* Getters and setters */
	public Entity.Type getType() {
		return this.type;
	}
	public void setType(Entity.Type type) {
		this.type = type;
	}
	public double getX() {
		return this.x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return this.y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public SinglePlayerGame getGame() {
		return this.game;
	}
	public void setGame(SinglePlayerGame game) {
		this.game = game;
	}
	public BufferedImage getEntityImage() {
		return this.entityImage;
	}

}
