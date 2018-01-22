package glide.game.entities;

import glide.game.GlideEngine;
import glide.game.screens.SinglePlayerGame;

import jtwod.engine.drawable.Entity;
import jtwod.engine.Screen;
import jtwod.engine.metrics.Vector;

public final class EnemyBullet extends Entity<GlideEngine> {

    int speed = 10;

    public EnemyBullet(Vector position, Screen<GlideEngine> screen, int orientation){
        super(position, screen);
        this.setVelocity(new Vector(0, speed));

        switch (orientation) {
            case 0 :
                this.setRenderedSprite(this.getParentEngine().getTextureGroup().getTexture("enemybullet"));
                break;
            case 1 :
                this.setRenderedSprite(this.getParentEngine().getTextureGroup().getTexture("enemybulletleft"));
                this.setVelocity(this.getVelocity().plusX(-(speed/2)));
                break;
            case 2 :
                this.setRenderedSprite(this.getParentEngine().getTextureGroup().getTexture("enemybulletright"));
                this.setRenderedSprite(this.getParentEngine().getTextureGroup().getTexture("enemybulletright"));
                this.setVelocity(this.getVelocity().plusX(speed/2));
                break;
        }
    }

    private SinglePlayerGame getGame()
    {
        return (SinglePlayerGame)this.getParentScreen();
    }
}
