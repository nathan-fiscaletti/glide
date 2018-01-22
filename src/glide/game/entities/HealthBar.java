package glide.game.entities;

import glide.game.GlideEngine;

import jtwod.engine.drawable.Entity;
import jtwod.engine.Screen;
import jtwod.engine.metrics.Vector;

public final class HealthBar extends Entity<GlideEngine> {
	public int health = 8;
	
	public HealthBar(Vector position, Screen<GlideEngine> screen) {
		super(position, screen);
		this.setRenderedSprite(this.getParentEngine().getTextureGroup().getTexture("healthbar8"));
	}
	
	@Override
	public final void updateSprite()
	{
		this.health = (health > 8) ? 8 : ((health < 1) ? 1 : health);
		
		switch (health) {
			case 8 : this.setRenderedSprite(this.getParentEngine().getTextureGroup().getTexture("healthbar1")); break;
			case 7 : this.setRenderedSprite(this.getParentEngine().getTextureGroup().getTexture("healthbar2")); break;
			case 6 : this.setRenderedSprite(this.getParentEngine().getTextureGroup().getTexture("healthbar3")); break;
			case 5 : this.setRenderedSprite(this.getParentEngine().getTextureGroup().getTexture("healthbar4")); break;
			case 4 : this.setRenderedSprite(this.getParentEngine().getTextureGroup().getTexture("healthbar5")); break;
			case 3 : this.setRenderedSprite(this.getParentEngine().getTextureGroup().getTexture("healthbar6")); break;
			case 2 : this.setRenderedSprite(this.getParentEngine().getTextureGroup().getTexture("healthbar7")); break;
			case 1 : this.setRenderedSprite(this.getParentEngine().getTextureGroup().getTexture("healthbar8")); break;
			
			default : this.setRenderedSprite(this.getParentEngine().getTextureGroup().getTexture("healthbar8"));
		}
	}
}
