package glide.game.entities;

import glide.game.GlideEngine;

import jtwod.engine.drawable.Entity;
import jtwod.engine.Scene;
import jtwod.engine.metrics.Vector;

public final class Plasma extends Entity<GlideEngine>
{
    public Plasma(Vector position, Scene<GlideEngine> screen) {
        super(position, screen);
        this.setRenderedTexture(this.getParentEngine().getTextureGroup().getTexture("plasmaplayer"));
    }
}
