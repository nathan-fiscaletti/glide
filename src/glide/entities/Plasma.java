package glide.entities;

import glide.engine.Entity;
import glide.engine.Vector;
import glide.game.screens.SinglePlayerGame;

public class Plasma extends Entity{
	public Plasma(Vector position, SinglePlayerGame attachedGame) {
		super(position, attachedGame);
		this.renderedSprite = this.attachedGame.getTextures().plasmaplayer;
	}
}
