package glide.game.entities;

import glide.game.GlideEngine;
import glide.game.GlideTextures;
import two.d.engine.Entity;
import two.d.engine.Screen;
import two.d.engine.Vector;

public class Drop extends Entity<GlideEngine>
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
	
	public Drop(Vector position, Screen<GlideEngine> screen, Drop.Type type) {
		
		super(position, screen);
		this.dropType = type;
		
		switch(this.dropType) {
			case HEALTHPACK : this.renderedSprite = Entity.getTextures(GlideTextures.class).healthpack; break;
			case BEAM       : this.renderedSprite = Entity.getTextures(GlideTextures.class).beam; break;
			case DIAMOND    : this.renderedSprite = Entity.getTextures(GlideTextures.class).diamond; break;
			case DIAMOND2   : this.renderedSprite = Entity.getTextures(GlideTextures.class).diamond2; break;
			case DIAMOND3   : this.renderedSprite = Entity.getTextures(GlideTextures.class).diamond3; break;
			case PLASMA     : this.renderedSprite = Entity.getTextures(GlideTextures.class).plasma; break;
			case MDB        : this.renderedSprite = Entity.getTextures(GlideTextures.class).mdppickup; break;
			case COD        : this.renderedSprite = Entity.getTextures(GlideTextures.class).cod_pickup; break;
			
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
		this.parentEngine.sounds.s_dropdeath.play(parentEngine);
	}
}
