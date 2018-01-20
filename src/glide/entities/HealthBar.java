package glide.entities;

import glide.engine.Entity;
import glide.engine.Vector;
import glide.game.screens.SinglePlayerGame;

public class HealthBar extends Entity{
	public int health = 8;
	
	public HealthBar(Vector position, SinglePlayerGame attachedGame) {
		super(position, attachedGame);
		this.renderedSprite = this.attachedGame.getTextures().healthbar8;
	}
	
	@Override
	public final void updateSprite()
	{
		this.health = (health > 8) ? 8 : ((health < 1) ? 1 : health);
		
		switch (health) {
			case 8 : this.renderedSprite = this.attachedGame.getTextures().healthbar1; break;
			case 7 : this.renderedSprite = this.attachedGame.getTextures().healthbar2; break;
			case 6 : this.renderedSprite = this.attachedGame.getTextures().healthbar3; break;
			case 5 : this.renderedSprite = this.attachedGame.getTextures().healthbar4; break;
			case 4 : this.renderedSprite = this.attachedGame.getTextures().healthbar5; break;
			case 3 : this.renderedSprite = this.attachedGame.getTextures().healthbar6; break;
			case 2 : this.renderedSprite = this.attachedGame.getTextures().healthbar7; break;
			case 1 : this.renderedSprite = this.attachedGame.getTextures().healthbar8; break;
			
			default : this.renderedSprite = this.attachedGame.getTextures().healthbar8;
		}
	}
}
