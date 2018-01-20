package glide.game.entities;

import glide.game.GlideEngine;
import glide.game.GlideTextures;
import two.d.engine.Entity;
import two.d.engine.Screen;
import two.d.engine.Vector;

public class HealthBar extends Entity<GlideEngine> {
	public int health = 8;
	
	public HealthBar(Vector position, Screen<GlideEngine> screen) {
		super(position, screen);
		this.renderedSprite = Entity.getTextures(GlideTextures.class).healthbar8;
	}
	
	@Override
	public final void updateSprite()
	{
		this.health = (health > 8) ? 8 : ((health < 1) ? 1 : health);
		
		switch (health) {
			case 8 : this.renderedSprite = Entity.getTextures(GlideTextures.class).healthbar1; break;
			case 7 : this.renderedSprite = Entity.getTextures(GlideTextures.class).healthbar2; break;
			case 6 : this.renderedSprite = Entity.getTextures(GlideTextures.class).healthbar3; break;
			case 5 : this.renderedSprite = Entity.getTextures(GlideTextures.class).healthbar4; break;
			case 4 : this.renderedSprite = Entity.getTextures(GlideTextures.class).healthbar5; break;
			case 3 : this.renderedSprite = Entity.getTextures(GlideTextures.class).healthbar6; break;
			case 2 : this.renderedSprite = Entity.getTextures(GlideTextures.class).healthbar7; break;
			case 1 : this.renderedSprite = Entity.getTextures(GlideTextures.class).healthbar8; break;
			
			default : this.renderedSprite = Entity.getTextures(GlideTextures.class).healthbar8;
		}
	}
}
