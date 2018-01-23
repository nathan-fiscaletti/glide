package glide.game.entities;

import glide.game.GlideEngine;

import jtwod.engine.drawable.Entity;
import jtwod.engine.Scene;
import jtwod.engine.metrics.Vector;

public final class EnemyBullet extends Entity<GlideEngine> {

    int speed = 10;

    public EnemyBullet(Vector position, Scene<GlideEngine> screen, int orientation){
        super(position, screen);
        this.setVelocity(new Vector(0, speed));

        switch (orientation) {
            case 0 :
                this.setRenderedTexture(this.getParentEngine().getTextureGroup().getTexture("enemybullet"));
                break;
            case 1 :
                this.setRenderedTexture(this.getParentEngine().getTextureGroup().getTexture("enemybulletleft"));
                this.setVelocity(this.getVelocity().plusX(-(speed/2)));
                break;
            case 2 :
                this.setRenderedTexture(this.getParentEngine().getTextureGroup().getTexture("enemybulletright"));
                this.setRenderedTexture(this.getParentEngine().getTextureGroup().getTexture("enemybulletright"));
                this.setVelocity(this.getVelocity().plusX(speed/2));
                break;
        }
    }
}
