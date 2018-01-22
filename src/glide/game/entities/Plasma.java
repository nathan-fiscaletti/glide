package glide.game.entities;

import glide.game.GlideEngine;

import jtwod.engine.drawable.Entity;
import jtwod.engine.Screen;
import jtwod.engine.metrics.Vector;

public final class Plasma extends Entity<GlideEngine>
{
    public Plasma(Vector position, Screen<GlideEngine> screen) {
        super(position, screen);
        this.setRenderedSprite(this.getParentEngine().getTextureGroup().getTexture("plasmaplayer"));
    }
}
