package glide.game.entities;

import glide.game.GlideEngine;

import jtwod.engine.drawable.Entity;
import jtwod.engine.Screen;
import jtwod.engine.metrics.Vector;

public final class Drop extends Entity<GlideEngine>
{
    /**
     * Entity Type Definitions
     */
    public static enum Type {
        HEALTHPACK, BEAM, DIAMOND, DIAMOND2, DIAMOND3, PLASMA, MDB, COD,
    }

    public boolean isDead = false;
    protected int lifeLived = 0;
    protected int deathtick = 0;

    public Drop.Type dropType;

    public Drop(Vector position, Screen<GlideEngine> screen, Drop.Type type) {

        super(position, screen);
        this.dropType = type;

        switch(this.dropType) {
            case HEALTHPACK : this.setRenderedSprite(this.getParentEngine().getTextureGroup().getTexture("healthpack")); break;
            case BEAM       : this.setRenderedSprite(this.getParentEngine().getTextureGroup().getTexture("beam")); break;
            case DIAMOND    : this.setRenderedSprite(this.getParentEngine().getTextureGroup().getTexture("diamond")); break;
            case DIAMOND2   : this.setRenderedSprite(this.getParentEngine().getTextureGroup().getTexture("diamond2")); break;
            case DIAMOND3   : this.setRenderedSprite(this.getParentEngine().getTextureGroup().getTexture("diamond3")); break;
            case PLASMA     : this.setRenderedSprite(this.getParentEngine().getTextureGroup().getTexture("plasma")); break;
            case MDB        : this.setRenderedSprite(this.getParentEngine().getTextureGroup().getTexture("mdppickup")); break;
            case COD        : this.setRenderedSprite(this.getParentEngine().getTextureGroup().getTexture("cod_pickup")); break;

            default         : break;
        }

        // Configure the death of the entity.
        this.setShouldPlayDeathAnimation(true);
        switch (this.dropType) {
            case DIAMOND :
            case DIAMOND2 :
            case DIAMOND3 :
                this.killAfter(180);
                break;
            case COD :
                this.killAfter(180);
                break;
            case BEAM :
            case PLASMA :
            case MDB :
                this.killAfter(240);
                break;
            case HEALTHPACK:
                this.killAfter(520);
                break;
            default:
                this.killAfter(1);
                break;
        }
    }

    @Override
    public final void onDeath()
    {
        this.getParentEngine().sounds.s_dropdeath.play(this.getParentEngine());
    }
}
