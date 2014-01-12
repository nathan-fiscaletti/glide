package glide.entities;

import glide.SinglePlayerGame;
import glide.Glide;

public class Drop extends Entity{
	
	
	
	private boolean dead = false;
	public Drop(double x, double y, SinglePlayerGame game, Entity.Type type) {
		super(x, y, game);
		this.setType(type);
		if(type == Entity.Type.HEALTHPACK){
			this.setEntityImage(game.getTextures().healthpack);
		}else if(type == Entity.Type.BEAM){
			this.setEntityImage(game.getTextures().beam);
		}else if(type == Entity.Type.DIAMOND){
			this.setEntityImage(game.getTextures().diamond);
		}else if(type == Entity.Type.PLASMA){
			this.setEntityImage(game.getTextures().plasma);
		}else if(type == Entity.Type.MDB){
			this.setEntityImage(game.getTextures().mdppickup);
		}else if(type == Entity.Type.DIAMOND2){
			this.setEntityImage(game.getTextures().diamond2);
		}else if(type == Entity.Type.DIAMOND3){
			this.setEntityImage(game.getTextures().diamond3);
		}else if(type == Entity.Type.COD){
			this.setEntityImage(game.getTextures().cod_pickup);
		}
	}
	
	int dropc = 0;
	int deathtick = 0;
	@Override 
	public void tick(){
		dropc ++;
		if(isDead()){
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
				Glide.dropdeath.play();
				this.game.getController().removeDrop(this);
			}
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
