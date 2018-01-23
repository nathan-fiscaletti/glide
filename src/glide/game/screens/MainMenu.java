package glide.game.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import glide.game.GlideEngine;

import jtwod.engine.Scene;

public final class MainMenu extends Scene<GlideEngine> {

    public MainMenu(GlideEngine engine) {
        super("MainMenu", engine);
    }

    /**
     * Generated Serial Version UID
     */
    private static final long serialVersionUID = -4093553489357496142L;

    private int selected = 1;

    @Override
    protected final void renderFrame(Graphics graphics){
        Font f = new Font("Ariel", Font.BOLD, 24);
        graphics.setFont(f);

        /* Logo */
        f = new Font("Ariel", Font.BOLD, 12);
        graphics.setFont(f);
        graphics.setColor(Color.GREEN);
        graphics.drawChars(this.getParentEngine().version.toCharArray(), 0, this.getParentEngine().version.length(), (this.getParentEngine().getWindowSize().getWidth() / 2) - (graphics.getFontMetrics().stringWidth(this.getParentEngine().version) / 2), 130 + this.getParentEngine().logoRenderer.getLogoHeight());

        /* Updater */
        graphics.setColor(this.getParentEngine().updater.update_color);
        graphics.drawChars(this.getParentEngine().updater.update.toCharArray(), 0, this.getParentEngine().updater.update.length(), (this.getParentEngine().getWindowSize().getWidth() / 2) - (graphics.getFontMetrics().stringWidth(this.getParentEngine().updater.update) / 2), 152 + this.getParentEngine().logoRenderer.getLogoHeight());
        graphics.drawChars(this.getParentEngine().updater.update2.toCharArray(), 0, this.getParentEngine().updater.update2.length(), (this.getParentEngine().getWindowSize().getWidth() / 2) - (graphics.getFontMetrics().stringWidth(this.getParentEngine().updater.update2) / 2), 174 + this.getParentEngine().logoRenderer.getLogoHeight());
        /* End Updater */

        graphics.setColor(Color.GREEN);
        f = new Font("Ariel", Font.BOLD, 24);
        graphics.setFont(f);

        /* Menu Items */

        //Play
        /////
        graphics.setColor((selected == 1) ? Color.GREEN : Color.DARK_GRAY);
        String sco = "Play";
        int w = graphics.getFontMetrics().stringWidth(sco);
        graphics.drawChars(sco.toCharArray(), 0, sco.toCharArray().length, (this.getParentEngine().getWindowSize().getWidth() / 2) - (w / 2), (this.getParentEngine().getWindowSize().getHeight() / 2) + (graphics.getFontMetrics().getDescent() - 49 + this.getParentEngine().logoRenderer.getLogoHeight()));
        ////


        //How to play
        ///
        graphics.setColor((selected == 9001) ? Color.GREEN : Color.DARK_GRAY);
        String sco2 = "Multiplayer (Coming soon)";
        int w2 = graphics.getFontMetrics().stringWidth(sco2);
        graphics.drawChars(sco2.toCharArray(), 0, sco2.toCharArray().length, (this.getParentEngine().getWindowSize().getWidth() / 2) - (w2 / 2), (this.getParentEngine().getWindowSize().getHeight() / 2) + (graphics.getFontMetrics().getDescent() - 25 + this.getParentEngine().logoRenderer.getLogoHeight()));
        ///

        //How to play
        ///
        graphics.setColor((selected == 2) ? Color.GREEN : Color.DARK_GRAY);
        sco2 = "How To Play";
        w2 = graphics.getFontMetrics().stringWidth(sco2);
        graphics.drawChars(sco2.toCharArray(), 0, sco2.toCharArray().length, (this.getParentEngine().getWindowSize().getWidth() / 2) - (w2 / 2), (this.getParentEngine().getWindowSize().getHeight() / 2) + (graphics.getFontMetrics().getDescent() - 25 + this.getParentEngine().logoRenderer.getLogoHeight() + 25));
        ///

        //Options
        ///
        graphics.setColor((selected == 3) ? Color.GREEN : Color.DARK_GRAY);
        sco2 = "Options";
        w2 = graphics.getFontMetrics().stringWidth(sco2);
        graphics.drawChars(sco2.toCharArray(), 0, sco2.toCharArray().length, (this.getParentEngine().getWindowSize().getWidth() / 2) - (w2 / 2), (this.getParentEngine().getWindowSize().getHeight() / 2) + (graphics.getFontMetrics().getDescent() + this.getParentEngine().logoRenderer.getLogoHeight() + 25));
        ///

        //Exit
        ///
        graphics.setColor((selected == 4) ? Color.GREEN : Color.DARK_GRAY);
        String sco3 = "Exit";
        int w3 = graphics.getFontMetrics().stringWidth(sco3);
        graphics.drawChars(sco3.toCharArray(), 0, sco3.toCharArray().length, (this.getParentEngine().getWindowSize().getWidth() / 2) - (w3 / 2), (this.getParentEngine().getWindowSize().getHeight() / 2) + (graphics.getFontMetrics().getDescent() + this.getParentEngine().logoRenderer.getLogoHeight() + 25 + 25));
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
            if(selected == 1){
                this.getParentEngine().sounds.s_enter.play(this.getParentEngine());
                this.getParentEngine().setScreen(new SinglePlayerGame(this.getParentEngine()));
            }else if(selected == 2){
                this.getParentEngine().sounds.s_enter.play(this.getParentEngine());
                this.getParentEngine().setScreen(new HTPMenu(this.getParentEngine()));
            }else if(selected == 3){
                this.getParentEngine().sounds.s_enter.play(this.getParentEngine());
                this.getParentEngine().setScreen(new OptionsMenu(this.getParentEngine()));
            }else if(selected == 4){
                System.exit(0);
            }
        }
    }

    @Override
    protected final void onKeyReleased(KeyEvent e){
        // Don't implement
    }

}
