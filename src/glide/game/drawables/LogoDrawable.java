package glide.game.drawables;

import java.awt.Graphics;

import glide.game.GlideEngine;
import jtwod.engine.Drawable;
import jtwod.engine.Screen;
import jtwod.engine.graphics.Texture;

public final class LogoDrawable extends Drawable<GlideEngine> {

    Texture logo;

    public LogoDrawable(String logoPath, GlideEngine engine)
    {
        super(engine);
        try {
            logo = new Texture(logoPath);
        } catch (Exception e){
            System.err.println("Error: Failed to load Logo.");
        }

        this.setTopMost(true);
    }

    @Override
    public final void render(Graphics g, Screen<GlideEngine> canvas) {
        if (logo != null) {
            g.drawImage(logo.asBufferedImage(), (this.getParentEngine().getWindowSize().getWidth() / 2) - (logo.getWidth() / 2), 120, null);
        }
    }

    @Override
    public final void update() {
        // Don't implement
    }

    public final int getLogoHeight()
    {
        return (this.logo == null) ? 0 : this.logo.getHeight();
    }
}
