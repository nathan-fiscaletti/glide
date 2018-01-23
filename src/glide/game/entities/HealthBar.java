package glide.game.entities;

import glide.game.GlideEngine;

import jtwod.engine.drawable.Entity;
import jtwod.engine.Scene;
import jtwod.engine.metrics.Vector;

public final class HealthBar extends Entity<GlideEngine> {
	public int health = 8;
	
	public HealthBar(Vector position, Scene<GlideEngine> screen) {
		super(position, screen);
		this.setRenderedTexture(this.getParentEngine().getTextureGroup().getTexture("healthbar8"));
	}
	
	@Override
	public final void updateSprite()
	{
		this.health = (health > 8) ? 8 : ((health < 1) ? 1 : health);
		
		switch (health) {
			case 8 : this.setRenderedTexture(this.getParentEngine().getTextureGroup().getTexture("healthbar1")); break;
			case 7 : this.setRenderedTexture(this.getParentEngine().getTextureGroup().getTexture("healthbar2")); break;
			case 6 : this.setRenderedTexture(this.getParentEngine().getTextureGroup().getTexture("healthbar3")); break;
			case 5 : this.setRenderedTexture(this.getParentEngine().getTextureGroup().getTexture("healthbar4")); break;
			case 4 : this.setRenderedTexture(this.getParentEngine().getTextureGroup().getTexture("healthbar5")); break;
			case 3 : this.setRenderedTexture(this.getParentEngine().getTextureGroup().getTexture("healthbar6")); break;
			case 2 : this.setRenderedTexture(this.getParentEngine().getTextureGroup().getTexture("healthbar7")); break;
			case 1 : this.setRenderedTexture(this.getParentEngine().getTextureGroup().getTexture("healthbar8")); break;
			
			default : this.setRenderedTexture(this.getParentEngine().getTextureGroup().getTexture("healthbar8"));
		}
	}
}
