package glide.game.entities;

import glide.game.GlideEngine;

import jtwod.engine.drawable.Entity;
import jtwod.engine.Screen;
import jtwod.engine.metrics.Vector;

public final class Bullet extends Entity<GlideEngine> {

    private int speed = 20;

    /**
     * Create a new Bullet entity.
     *
     * @param position
     * @param screen
     */
    public Bullet(Vector position, Screen<GlideEngine> screen)
    {
        super(position, screen);

        // Set the sprite.
        this.setRenderedSprite(this.getParentEngine().getTextureGroup().getTexture("bullet"));

        // Play the shoot sound.
        this.getParentEngine().sounds.s_shoot.play(this.getParentEngine());

        // Set the entities velocity
        this.setVelocity(new Vector(0, -speed));
    }

    @Override
    public final void onCollide(Entity<GlideEngine> collidedWith)
    {
        // Handle Collision with Meteor
        if (collidedWith instanceof Meteor) {
            this.kill();
            ((Meteor)collidedWith).kill(false);
            this.getParentEngine().sounds.s_explosion.play(this.getParentEngine());
        }

        // Handle Collision with Small Meteor
        else if (collidedWith instanceof SmallMeteor) {
            ((SmallMeteor)collidedWith).kill();
            this.getParentEngine().sounds.s_explosion.play(this.getParentEngine());
        }

        // Handle collision with Enemy
        else if (collidedWith instanceof Enemy) {
            Enemy enemy = (Enemy)collidedWith;
            if (! enemy.isDead) {
                if (enemy.lives == 1) {
                    if (enemy.isBomb) {
                        enemy.kill(false);
                    } else {
                        enemy.kill();
                    }
                }else{
                    enemy.lives --;
                }

                this.kill();
                this.getParentEngine().sounds.s_explosion.play(this.getParentEngine());
            }
        }
    }
}
