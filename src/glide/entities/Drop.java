package glide.entities;

import glide.Game;
import glide.Glide;

public class Drop extends Entity{
	public static final int TYPE_HEALTHPACK = 8;
	public static final int TYPE_BEAM = 9;
	public static final int TYPE_DIAMOND = 10;
	public Drop(double x, double y, Game game, int Type) {
		super(x, y, game);
		this.setType(Type);
		if(Type == Drop.TYPE_HEALTHPACK){
			this.setEntityImage(game.getTextures().healthpack);
		}else if(Type == Drop.TYPE_BEAM){
			this.setEntityImage(game.getTextures().beam);
		}else if(Type == Drop.TYPE_DIAMOND){
			this.setEntityImage(game.getTextures().diamond);
		}
	}
	
	int dropc = 0;
	@Override 
	public void tick(){
		dropc ++;
		if(getType() == TYPE_DIAMOND){
			if(dropc > 180){
				Glide.game.getController().removeDrop(this);
				dropc = 0;
			}
		}else if(getType() == TYPE_BEAM){
			if(dropc > 240){
				Glide.game.getController().removeDrop(this);
				dropc = 0;
			}
		}else if(getType() == TYPE_HEALTHPACK){
			if(dropc > 520){
				Glide.game.getController().removeDrop(this);
				dropc = 0;
			}
		}
	}
}
