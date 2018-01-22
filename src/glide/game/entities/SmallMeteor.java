package glide.game.entities;

import glide.game.Difficulty;
import glide.game.GlideEngine;

import jtwod.engine.drawable.Entity;
import jtwod.engine.Screen;
import jtwod.engine.metrics.Vector;

public final class SmallMeteor extends Entity<GlideEngine>
{

    int speed = 1 + (int)(Math.random()*2);
    int xVelocityMultiplier = (random.nextBoolean()) ? -1 : 1;
    int meteorType = 1;

    int changeDirectionTick = 0;

    public SmallMeteor(Vector position, Screen<GlideEngine> screen) {
        super(position, screen);
        this.meteorType = random.nextInt(7);
        this.setShouldPlayDeathAnimation(true);

        switch (meteorType) {
            case 1 : this.setRenderedSprite(this.getParentEngine().getTextureGroup().getTexture("smallmeteor1")); break;
            case 2 : this.setRenderedSprite(this.getParentEngine().getTextureGroup().getTexture("smallmeteor2")); break;
            case 3 : this.setRenderedSprite(this.getParentEngine().getTextureGroup().getTexture("smallmeteor3")); break;
            case 4 : this.setRenderedSprite(this.getParentEngine().getTextureGroup().getTexture("smallmeteor4")); break;
            case 5 : this.setRenderedSprite(this.getParentEngine().getTextureGroup().getTexture("smallmeteor5")); break;
            case 6 : this.setRenderedSprite(this.getParentEngine().getTextureGroup().getTexture("smallmeteor6")); break;

            default : this.setRenderedSprite(this.getParentEngine().getTextureGroup().getTexture("smallmeteor1"));
        }
    }

    @Override
    public final void update()
    {
        this.move(new Vector(0, speed));

        if(this.getParentEngine().difficulty != Difficulty.Easy){
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
    }

}
