package glide.game.entities;

import glide.game.GlideEngine;

import jtwod.engine.drawable.Entity;
import jtwod.engine.Screen;
import jtwod.engine.metrics.Vector;

public final class MultiDirectionalBullet extends Entity<GlideEngine>
{

    private int speed = 5;

    public MultiDirectionalBullet(Vector position, Screen<GlideEngine> screen, int tofro) {
        super(position, screen);
        this.setRenderedSprite(this.getParentEngine().getTextureGroup().getTexture("mdbullet"));

        // Set the velocity for the entity.
        if(tofro == 1){
            this.setVelocity(this.getVelocity().plus(speed, speed));
        }else if(tofro == 2){
            this.setVelocity(this.getVelocity().plus(speed, -speed));
        }else if(tofro == 3){
            this.setVelocity(this.getVelocity().plus(-speed, speed));
        }else if(tofro == 4){
            this.setVelocity(this.getVelocity().plus(-speed, -speed));
        }else if(tofro == 5){
            this.setVelocity(this.getVelocity().plusX(speed));
        }else if(tofro == 6){
            this.setVelocity(this.getVelocity().plusX(-speed));
        }else if(tofro == 7){
            this.setVelocity(this.getVelocity().plusY(speed));
        }else if(tofro == 8){
            this.setVelocity(this.getVelocity().plusY(-speed));
        }
    }

    @Override
    public final void onCollide(Entity<GlideEngine> collidedWith)
    {
        // Handle Collision with Meteor
        if (collidedWith instanceof Meteor) {
            ((Meteor)collidedWith).kill(false);
            this.getParentEngine().sounds.s_explosion.play(this.getParentEngine());
        }

        // Handle Collision with Small Meteor
        else if (collidedWith instanceof SmallMeteor) {
            ((SmallMeteor)collidedWith).kill();
            this.getParentEngine().sounds.s_explosion.play(this.getParentEngine());
        }

        // Handle Collision with Enemy
        else if (collidedWith instanceof Enemy) {
            Enemy enemy = (Enemy)collidedWith;
            if (! enemy.isDead) {
                if (enemy.isBomb) {
                    enemy.kill(false);
                } else {
                    enemy.kill();
                }

                this.getParentEngine().sounds.s_explosion.play(this.getParentEngine());
            }
        }
    }
}
