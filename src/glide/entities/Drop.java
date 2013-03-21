package glide.entities;

import glide.Game;
import glide.Glide;

public class Drop extends Entity{
	public static final int TYPE_HEALTHPACK = 8;
	public static final int TYPE_BEAM = 9;
	public static final int TYPE_DIAMOND = 10;
	public static final int TYPE_PLASMA = 12;
	private boolean dead = false;
	public Drop(double x, double y, Game game, int Type) {
		super(x, y, game);
		this.setType(Type);
		if(Type == Drop.TYPE_HEALTHPACK){
			this.setEntityImage(game.getTextures().healthpack);
		}else if(Type == Drop.TYPE_BEAM){
			this.setEntityImage(game.getTextures().beam);
		}else if(Type == Drop.TYPE_DIAMOND){
			this.setEntityImage(game.getTextures().diamond);
		}else if(Type == Drop.TYPE_PLASMA){
			this.setEntityImage(game.getTextures().plasma);
		}
	}
	
	int dropc = 0;
	int deathtick = 0;
	@Override 
	public void tick(){
		dropc ++;
		if(isDead()){
			if(deathtick < 5){
				this.setEntityImage(Glide.game.getTextures().des1);
				deathtick ++;
			}else if(deathtick >= 5 && deathtick < 10){
				this.setEntityImage(Glide.game.getTextures().des2);
				deathtick ++;
			}else if(deathtick >= 10 && deathtick < 15){
				this.setEntityImage(Glide.game.getTextures().des3);
				deathtick ++;
			}else if(deathtick == 15){
				Glide.dropdeath.play();
				Glide.game.getController().removeDrop(this);
			}
		}else{
			if(getType() == TYPE_DIAMOND){
				if(dropc > 180){
					die();
				}
			}else if(getType() == TYPE_BEAM || getType() == TYPE_PLASMA){
				if(dropc > 240){
					die();
				}
			}else if(getType() == TYPE_HEALTHPACK){
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
