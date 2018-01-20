package glide.game.entities;

import glide.engine.Entity;
import glide.engine.Screen;
import glide.engine.Vector;

public class HealthBar extends Entity{
	public int health = 8;
	
	public HealthBar(Vector position, Screen screen) {
		super(position, screen);
		this.renderedSprite = Entity.getTextures().healthbar8;
	}
	
	@Override
	public final void updateSprite()
	{
		this.health = (health > 8) ? 8 : ((health < 1) ? 1 : health);
		
		switch (health) {
			case 8 : this.renderedSprite = Entity.getTextures().healthbar1; break;
			case 7 : this.renderedSprite = Entity.getTextures().healthbar2; break;
			case 6 : this.renderedSprite = Entity.getTextures().healthbar3; break;
			case 5 : this.renderedSprite = Entity.getTextures().healthbar4; break;
			case 4 : this.renderedSprite = Entity.getTextures().healthbar5; break;
			case 3 : this.renderedSprite = Entity.getTextures().healthbar6; break;
			case 2 : this.renderedSprite = Entity.getTextures().healthbar7; break;
			case 1 : this.renderedSprite = Entity.getTextures().healthbar8; break;
			
			default : this.renderedSprite = Entity.getTextures().healthbar8;
		}
	}
}
