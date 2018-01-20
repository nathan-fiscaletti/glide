package glide.entities;

import glide.engine.Entity;
import glide.engine.Vector;
import glide.game.Glide;
import glide.game.screens.SinglePlayerGame;

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
	
	public Drop(Vector position, SinglePlayerGame attachedGame, Drop.Type type) {
		
		super(position, attachedGame);
		this.dropType = type;
		
		switch(this.dropType) {
			case HEALTHPACK : this.renderedSprite = this.attachedGame.getTextures().healthpack; break;
			case BEAM       : this.renderedSprite = this.attachedGame.getTextures().beam; break;
			case DIAMOND    : this.renderedSprite = this.attachedGame.getTextures().diamond; break;
			case DIAMOND2   : this.renderedSprite = this.attachedGame.getTextures().diamond2; break;
			case DIAMOND3   : this.renderedSprite = this.attachedGame.getTextures().diamond3; break;
			case PLASMA     : this.renderedSprite = this.attachedGame.getTextures().plasma; break;
			case MDB        : this.renderedSprite = this.attachedGame.getTextures().mdppickup; break;
			case COD        : this.renderedSprite = this.attachedGame.getTextures().cod_pickup; break;
			
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
