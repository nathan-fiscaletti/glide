package glide.game.entities;

import glide.engine.Entity;
import glide.engine.Screen;
import glide.engine.Vector;
import glide.game.Glide;

public class Drop extends Entity 
{
	
	/**
	 * Entity Type Definitions
	 */
	public static enum Type {
		HEALTHPACK, BEAM, DIAMOND, DIAMOND2, DIAMOND3, PLASMA, MDB, COD,
	}
	
	public boolean isDead = false;
	protected int lifeLived = 0;
	protected int deathtick = 0;
	
	public Drop.Type dropType;
	
	public Drop(Vector position, Screen screen, Drop.Type type) {
		
		super(position, screen);
		this.dropType = type;
		
		switch(this.dropType) {
			case HEALTHPACK : this.renderedSprite = Entity.getTextures().healthpack; break;
			case BEAM       : this.renderedSprite = Entity.getTextures().beam; break;
			case DIAMOND    : this.renderedSprite = Entity.getTextures().diamond; break;
			case DIAMOND2   : this.renderedSprite = Entity.getTextures().diamond2; break;
			case DIAMOND3   : this.renderedSprite = Entity.getTextures().diamond3; break;
			case PLASMA     : this.renderedSprite = Entity.getTextures().plasma; break;
			case MDB        : this.renderedSprite = Entity.getTextures().mdppickup; break;
			case COD        : this.renderedSprite = Entity.getTextures().cod_pickup; break;
			
			default         : break;
		}
		
		// Configure the death of the entity.
		this.playDeathAnimation = true;
		switch (this.dropType) {
			case DIAMOND :
			case DIAMOND2 :
			case DIAMOND3 :
				this.killAfter = 180;
				break;
			case COD :
				this.killAfter = 180;
				break;
			case BEAM :
			case PLASMA :
			case MDB :
				this.killAfter = 240;
				break;
			case HEALTHPACK:
				this.killAfter = 520;
				break;
			default:
				this.killAfter = 1;
				break;
		}
	}
	
	@Override
	public final void onDeath()
	{
		Glide.s_dropdeath.play();
	}
}
