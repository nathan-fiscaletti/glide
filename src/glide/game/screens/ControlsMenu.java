package glide.game.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import glide.game.GlideEngine;

import jtwod.engine.Scene;
import jtwod.engine.graphics.Texture;

public final class ControlsMenu extends Scene<GlideEngine> {

    /**
     * Create the menu.
     * @param engine
     */
    public ControlsMenu(GlideEngine engine) {
        super("ControlsMenu", engine);
    }

    /**
     * Generated Serial Version UID
     */
    private static final long serialVersionUID = -4093553489357496142L;

    /* Game Properties */
    private int selected = 1;

    @Override
    protected final void renderFrame(Graphics graphics){
        Font f = new Font("Ariel", Font.BOLD, 24);
        graphics.setFont(f);
        graphics.setColor(Color.ORANGE);

        String op = "Controls";
        f = new Font("Ariel", Font.BOLD, 32);
        graphics.setFont(f);
        graphics.setColor(Color.ORANGE);
        int w = graphics.getFontMetrics().stringWidth(op);
        graphics.drawChars(op.toCharArray(), 0, op.toCharArray().length, (this.getParentEngine().getWindowSize().getWidth() / 2) - (w / 2), (this.getParentEngine().getWindowSize().getHeight() / 2) + (graphics.getFontMetrics().getDescent() - 130 + this.getParentEngine().logoRenderer.getLogoHeight()));


        /* Menu Items */
        f = new Font("Ariel", Font.BOLD, 24);
        graphics.setFont(f);

        Texture controlls = new Texture("/images/keys/controlls.png");
        graphics.drawImage(controlls.asBufferedImage(), (this.getParentEngine().getWindowSize().getWidth() / 2) - (controlls.getWidth() / 2), 120 + this.getParentEngine().logoRenderer.getLogoHeight() + 24 + 40, this);
        //Exit
        ///
        graphics.setColor(Color.GREEN);
        String sco3 = "Back";
        int w3 = graphics.getFontMetrics().stringWidth(sco3);
        graphics.drawChars(sco3.toCharArray(), 0, sco3.toCharArray().length, (this.getParentEngine().getWindowSize().getWidth() / 2) - (w3 / 2), (controlls.getHeight() + 24 + 18 + 140 + this.getParentEngine().logoRenderer.getLogoHeight() + 40));
        ///
    }

    @Override
    protected final void onKeyPressed(KeyEvent e){
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_UP){
            if(selected == 1){
                selected = 4;
            }else if(selected == 2){
                selected = 1;
            }else if(selected == 3){
                selected = 2;
            }else if(selected == 4){
                selected = 3;
            }
            this.getParentEngine().sounds.s_select.play(this.getParentEngine());
        }else if (key == KeyEvent.VK_DOWN){
            if(selected == 1){
                selected = 2;
            }else if(selected == 2){
                selected = 3;
            }else if(selected == 3){
                selected = 4;
            }else if(selected == 4){
                selected = 1;
            }
            this.getParentEngine().sounds.s_select.play(this.getParentEngine());
        }else if(key == KeyEvent.VK_ENTER){
            this.getParentEngine().sounds.s_enter.play(this.getParentEngine());
            this.getParentEngine().setScreen(new OptionsMenu(this.getParentEngine()));
        }
    }

    @Override
    protected final void onKeyReleased(KeyEvent e){
        // Don't Implemented
    }
}
