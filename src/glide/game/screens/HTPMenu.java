package glide.game.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import glide.game.GlideEngine;

import jtwod.engine.Scene;
import jtwod.engine.graphics.Texture;


public final class HTPMenu extends Scene<GlideEngine>
{

    public HTPMenu(GlideEngine engine) {
        super("HTPMenu", engine);
    }

    /**
     * Generated Serial Version UID
     */
    private static final long serialVersionUID = -4093553489357496142L;

    @Override
    protected final void renderFrame(Graphics graphics){

        Font f = new Font("Ariel", Font.BOLD, 16);
        graphics.setFont(f);
        graphics.setColor(Color.ORANGE);

        Texture ht = new Texture("/images/htp.png");

        graphics.drawImage(ht.asBufferedImage(), (this.getParentEngine().getWindowSize().getWidth() / 2) - (ht.getWidth() / 2), 250, this);


        /* Menu Items */

        //Back
        ///
        f = new Font("Ariel", Font.BOLD, 24);
        graphics.setFont(f);
        graphics.setColor(Color.GREEN);
        String sco3 = "Back";
        int w3 = graphics.getFontMetrics().stringWidth(sco3);
        graphics.drawChars(sco3.toCharArray(), 0, sco3.toCharArray().length, (this.getParentEngine().getWindowSize().getWidth() / 2) - (w3 / 2), (this.getParentEngine().getWindowSize().getHeight() / 2) + (graphics.getFontMetrics().getDescent() + this.getParentEngine().logoRenderer.getLogoHeight()));
        ///

    }

    @Override
    protected final void onKeyPressed(KeyEvent e){
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_ENTER){
            this.getParentEngine().sounds.s_enter.play(this.getParentEngine());
            this.getParentEngine().setScreen(new MainMenu(this.getParentEngine()));
        }
    }

    @Override
    protected final void onKeyReleased(KeyEvent e){
        // Don't Implement
    }

}
