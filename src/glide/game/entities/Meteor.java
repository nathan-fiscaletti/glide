package glide.game.entities;

import glide.game.Difficulty;
import glide.game.GlideEngine;

import jtwod.engine.drawable.Entity;
import jtwod.engine.Screen;
import jtwod.engine.metrics.Vector;

public final class Meteor extends Entity<GlideEngine>
{
    protected int speed = 1 + (int)(Math.random()*3);
    protected int xVelocityMultiplier = (this.random.nextBoolean()) ? -1 : 1;
    protected int meteorType = 1;
    protected int changeDirectionTick = 0;
    protected boolean forceSmall = false;

    public Meteor(Vector position, Screen<GlideEngine> screen) {
        super(position, screen);
        meteorType = random.nextInt(7);

        this.setVelocity(this.getVelocity().plusY(speed));

        // Should the entity be small or large?
        // When we want it to be small, we simply
        // kill this meteor which will spawn
        // a group of smaller ones.
        if(!random.nextBoolean()) {
            this.kill(true);
        } else {
            // Tell the meteor to play the death animation when it dies.
            this.setShouldPlayDeathAnimation(true);

            // Initialize the sprite for the entity.
            switch(meteorType) {
                case 1 : this.setRenderedSprite(this.getParentEngine().getTextureGroup().getTexture("meteor1")); break;
                case 2 : this.setRenderedSprite(this.getParentEngine().getTextureGroup().getTexture("meteor2")); break;
                case 3 : this.setRenderedSprite(this.getParentEngine().getTextureGroup().getTexture("meteor3")); break;
                case 4 : this.setRenderedSprite(this.getParentEngine().getTextureGroup().getTexture("meteor4")); break;
                case 5 : this.setRenderedSprite(this.getParentEngine().getTextureGroup().getTexture("meteor5")); break;
                case 6 : this.setRenderedSprite(this.getParentEngine().getTextureGroup().getTexture("meteor6")); break;

                default: this.setRenderedSprite(this.getParentEngine().getTextureGroup().getTexture("meteor1"));
            }
        }
    }

    @Override
    public final void onCollide(Entity<GlideEngine> collidedWith)
    {
        // Handle a Meteor colliding with another meteor.
        if (collidedWith instanceof Meteor) {
            if ((Meteor)collidedWith != this && ! collidedWith.isDead()) {
                this.kill(true);
                ((Meteor)collidedWith).kill(true);
                this.getParentEngine().sounds.s_explosion.play(this.getParentEngine());
            }
        }
    }

    @Override
    public final void update()
    {
        // If the difficulty is anything besides EASY,
        // move the meteor left or right as well.
        if(this.getParentEngine().difficulty != Difficulty.Easy){

            // Move the meteor either left or right.
            this.setVelocity(this.getVelocity().setX(speed*xVelocityMultiplier));

            // If you set the difficulty to expert, the meteors
            // should change their X velocity randomly every 60 ticks.
            if (this.getParentEngine().difficulty == Difficulty.Expert) {

                // After every 60 ticks, we need to
                // change the left or right toggle.
                if (this.changeDirectionTick == 60) {
                    this.xVelocityMultiplier = (this.random.nextBoolean()) ? -1 : 1;
                }

                // Update how many ticks it's been
                // since we last changed the
                // left or right toggle.
                this.changeDirectionTick += (this.changeDirectionTick == 60) ? -60 : 1;
            }
        }
    }

    @Override
    public final void onDeath()
    {
        // Generate the child meteors when a meteor is destroyed
        if(this.random.nextBoolean() || this.forceSmall){

            // Generate between 0 and 5 meteors
            int smallMeteorCount = this.random.nextInt(5);

            // Give the small meteors a spread of 70 pixels
            int spread = 70;
            for (int i = 0;i<smallMeteorCount;i++) {
                SmallMeteor small_meteor = new SmallMeteor(this.getPosition(), this.getParentScreen());
                small_meteor.setPosition(small_meteor.getPosition().plusX((this.random.nextBoolean() ? -this.random.nextInt(spread) : +this.random.nextInt(spread))));
                small_meteor.setPosition(small_meteor.getPosition().plusY((this.random.nextBoolean() ? -this.random.nextInt(spread) : +this.random.nextInt(spread))));
                this.getParentScreen().getController().spawnEntity(small_meteor);
            }
        }
    }

    public final void kill(boolean forceSmall){
        this.forceSmall = true;
        this.kill();
    }

}
