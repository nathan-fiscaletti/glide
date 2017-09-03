package glide.entities;

import glide.SinglePlayerGame;

import java.awt.image.BufferedImage;

public class Drop extends Entity{
	
	private boolean dead = false;
	public Drop(double x, double y, SinglePlayerGame game, Entity.Type type) {
		super(x, y, game);
		this.setType(type);
	}
	
	@Override
	public BufferedImage getEntityImage()
	{
		// TODO: Implement death animation
		switch(this.getType()) {
			case HEALTHPACK : return game.getTextures().healthpack;
			case BEAM       : return game.getTextures().beam;
			case DIAMOND    : return game.getTextures().diamond;
			case DIAMOND2   : return game.getTextures().diamond2;
			case DIAMOND3   : return game.getTextures().diamond3;
			case PLASMA     : return game.getTextures().plasma;
			case MDB        : return game.getTextures().mdppickup;
			case COD        : return game.getTextures().cod_pickup;
			
			default         : return null;
		}
	}
	
	int dropc = 0;
	int deathtick = 0;
	@Override 
	public void tick(){
		dropc ++;
		if(isDead()){
			// TODO: Implement death animation
			/**
			if(deathtick < 5){
				this.setEntityImage(this.game.getTextures().des1);
				deathtick ++;
			}else if(deathtick >= 5 && deathtick < 10){
				this.setEntityImage(this.game.getTextures().des2);
				deathtick ++;
			}else if(deathtick >= 10 && deathtick < 15){
				this.setEntityImage(this.game.getTextures().des3);
				deathtick ++;
			}else if(deathtick == 15){
				Glide.s_dropdeath.play();
				this.game.getController().removeDrop(this);
			}
			**/
			this.game.getController().removeDrop(this);
		}else{
			if(getType() == Entity.Type.DIAMOND || getType() == Entity.Type.COD){
				if(dropc > 180){
					die();
				}
			}else if(getType() == Entity.Type.BEAM || getType() == Entity.Type.PLASMA || getType() == Entity.Type.MDB){
				if(dropc > 240){
					die();
				}
			}else if(getType() == Entity.Type.HEALTHPACK){
				if(dropc > 520){
					die();
				}
			}
		}
	}
	
	public void die(){
		setDead(true);
	}
	
	public boolean isDead() {
		return dead;
	}
	public void setDead(boolean dead) {
		this.dead = dead;
	}
}
