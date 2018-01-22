package glide.game.drawables;

import java.awt.Graphics;

import glide.game.GlideEngine;
import jtwod.engine.Drawable;
import jtwod.engine.Screen;
import jtwod.engine.graphics.Texture;
import jtwod.engine.metrics.Vector;

public final class BackgroundDrawable extends Drawable<GlideEngine> {

    private Texture blackTexture;
    private Texture backgroundOne = null;
    private Texture backgroundTwo = null;

    private Vector backgroundOnePosition = Vector.Zero();
    private Vector backgroundTwoPosition = Vector.Zero();

    private int speed = 1;

    public BackgroundDrawable(String backgroundImagePath, GlideEngine engine)
    {
        super(engine);

        this.blackTexture = Texture.blackTexture(engine.getWindowSize());

        try{
            backgroundOne = new Texture(backgroundImagePath);
            backgroundTwo = backgroundOne;
            backgroundTwoPosition.plusY(-backgroundOne.getHeight());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void render(Graphics g, Screen<GlideEngine> canvas) {
        g.drawImage(this.blackTexture.asBufferedImage(), 0, 0, this.getParentEngine().getWindowSize().getWidth(), this.getParentEngine().getWindowSize().getHeight(), canvas);

        if(backgroundOnePosition.getY() >= Vector.Max(this.getParentEngine()).getY()){
            backgroundOnePosition.setY(backgroundTwoPosition.getY() - backgroundTwo.getHeight());
        }

        if(backgroundTwoPosition.getY() >= Vector.Max(this.getParentEngine()).getY()){
            backgroundTwoPosition.setY(backgroundOnePosition.getY() - backgroundTwo.getHeight());
        }

        g.drawImage(backgroundOne.asBufferedImage(), 0, (int)backgroundOnePosition.getY(), canvas);
        g.drawImage(backgroundTwo.asBufferedImage(), 0, backgroundTwoPosition.getY(), canvas);
    }

    @Override
    public void update()
    {
        backgroundOnePosition = backgroundOnePosition.plusY(speed);
        backgroundTwoPosition = backgroundTwoPosition.plusY(speed);
    }

    public void setSpeed(int speed)
    {
        this.speed = speed;
    }

}
