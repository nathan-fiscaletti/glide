package glide.game.drawables;

import java.awt.Graphics;

import glide.game.GlideEngine;
import jtwod.engine.Drawable;
import jtwod.engine.Scene;
import jtwod.engine.drawable.Image;
import jtwod.engine.graphics.Texture;
import jtwod.engine.metrics.Vector;

public final class BackgroundDrawable extends Drawable<GlideEngine> {

    private Texture blackTexture;
    private Image<GlideEngine> backgroundOne = null;
    private Image<GlideEngine> backgroundTwo = null;

    private int speed = 1;

    public BackgroundDrawable(String backgroundImagePath, GlideEngine engine)
    {
        super(engine);

        this.blackTexture = Texture.blackTexture(engine.getWindowSize());

        try{
            backgroundOne = new Image<>(
                new Texture(backgroundImagePath),
                Vector.Zero(),
                engine
            );
            
            backgroundTwo = new Image<>(
                new Texture(backgroundImagePath),
                Vector.Zero().plusY(
                    -backgroundOne.getSize().getHeight()
                ),
                engine
            );
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void render(Graphics graphics, Scene<GlideEngine> screen) {
        graphics.drawImage(
            this.blackTexture.asBufferedImage(),
            0,
            0,
            this.getParentEngine().getWindowSize().getWidth(),
            this.getParentEngine().getWindowSize().getHeight(),
            screen
        );
        
        backgroundOne.render(graphics, screen);
        backgroundTwo.render(graphics, screen);
    }

    @Override
    public void update()
    {
        if(backgroundOne.getPosition().getY() >= Vector.Max(this.getParentEngine()).getY()){
            backgroundOne.setPosition(Vector.Zero().setY(backgroundTwo.getPosition().getY() - backgroundTwo.getSize().getHeight()));
        }

        if(backgroundTwo.getPosition().getY() >= Vector.Max(this.getParentEngine()).getY()){
            backgroundTwo.setPosition(Vector.Zero().setY(backgroundOne.getPosition().getY() - backgroundOne.getSize().getHeight()));
        }
        
        backgroundOne.move(Vector.Zero().plusY(speed));
        backgroundTwo.move(Vector.Zero().plusY(speed));
    }

    public void setSpeed(int speed)
    {
        this.speed = speed;
    }

}
