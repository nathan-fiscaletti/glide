package glide.game.entities;

import glide.game.GlideEngine;
import glide.game.GlideTextures;
import two.d.engine.Entity;
import two.d.engine.Screen;
import two.d.engine.Vector;

public class Plasma extends Entity<GlideEngine>
{
	public Plasma(Vector position, Screen<GlideEngine> screen) {
		super(position, screen);
		this.renderedSprite = Entity.getTextures(GlideTextures.class).plasmaplayer;
	}
}
