package glide.game.entities;

import glide.engine.Entity;
import glide.engine.Screen;
import glide.engine.Vector;

public class Plasma extends Entity{
	public Plasma(Vector position, Screen screen) {
		super(position, screen);
		this.renderedSprite = Entity.getTextures().plasmaplayer;
	}
}
