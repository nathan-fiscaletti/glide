package glide.game.screens;

import glide.game.GlideEngine;
import jtwod.engine.Screen;
import glide.game.Difficulty;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public final class OptionsMenu extends Screen<GlideEngine> {

    public OptionsMenu(GlideEngine engine) {
        super("OptionsMenu", engine);
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

        String op = "Options";
        f = new Font("Ariel", Font.BOLD, 32);
        graphics.setFont(f);
        graphics.setColor(Color.ORANGE);
        int w = graphics.getFontMetrics().stringWidth(op);
        graphics.drawChars(op.toCharArray(), 0, op.toCharArray().length, (this.getParentEngine().getWindowSize().getWidth() / 2) - (w / 2), (this.getParentEngine().getWindowSize().getHeight() / 2) + (graphics.getFontMetrics().getDescent() - 130 + this.getParentEngine().logoRenderer.getLogoHeight()));


        /* Menu Items */

        f = new Font("Ariel", Font.BOLD, 24);
        graphics.setFont(f);


        //Audio
        /////
        if(selected == 1){
            graphics.setColor(Color.GREEN);
        }else{
            graphics.setColor(Color.DARK_GRAY);
        }
        String sco = "Music - " + ((this.getParentEngine().enableMusic) ? "Enabled" : "Disabled");
        w = graphics.getFontMetrics().stringWidth(sco);
        graphics.drawChars(sco.toCharArray(), 0, sco.toCharArray().length, (this.getParentEngine().getWindowSize().getWidth() / 2) - (w / 2), (this.getParentEngine().getWindowSize().getHeight() / 2) + (graphics.getFontMetrics().getDescent() - 49 + this.getParentEngine().logoRenderer.getLogoHeight()));
        ////
        //

        //Sounds
        /////
        if(selected == 2){
            graphics.setColor(Color.GREEN);
        }else{
            graphics.setColor(Color.DARK_GRAY);
        }
        sco = "Sounds - " + ((! this.getParentEngine().isMuted()) ? "Enabled" : "Disabled");
        w = graphics.getFontMetrics().stringWidth(sco);
        graphics.drawChars(sco.toCharArray(), 0, sco.toCharArray().length, (this.getParentEngine().getWindowSize().getWidth() / 2) - (w / 2), (this.getParentEngine().getWindowSize().getHeight() / 2) + (graphics.getFontMetrics().getDescent() + this.getParentEngine().logoRenderer.getLogoHeight() - 13));
        ////
        //

        //Controls
        /////
        if(selected == 3){
            graphics.setColor(Color.GREEN);
        }else{
            graphics.setColor(Color.DARK_GRAY);
        }
        sco = "Controls";
        w = graphics.getFontMetrics().stringWidth(sco);
        graphics.drawChars(sco.toCharArray(), 0, sco.toCharArray().length, (this.getParentEngine().getWindowSize().getWidth() / 2) - (w / 2), (this.getParentEngine().getWindowSize().getHeight() / 2) + (graphics.getFontMetrics().getDescent() + this.getParentEngine().logoRenderer.getLogoHeight() + 24));
        ////
        //

        //Difficulty
        /////
            if(selected == 4){
                graphics.setColor(Color.GREEN);
            }else{
                graphics.setColor(Color.DARK_GRAY);
            }
            sco = "Difficulty - ";
            String sco2 = this.getParentEngine().difficulty.toString();


            w = graphics.getFontMetrics().stringWidth(sco);
            int w2 = graphics.getFontMetrics().stringWidth(sco2);
            graphics.drawChars(sco.toCharArray(), 0, sco.toCharArray().length, (this.getParentEngine().getWindowSize().getWidth() / 2) - ((w + w2) / 2), (this.getParentEngine().getWindowSize().getHeight() / 2) + (graphics.getFontMetrics().getDescent() + this.getParentEngine().logoRenderer.getLogoHeight() + 59));
            graphics.setColor(Difficulty.getDifficultyColor(this.getParentEngine().difficulty));
            graphics.drawChars(sco2.toCharArray(), 0, sco2.toCharArray().length, (this.getParentEngine().getWindowSize().getWidth() / 2) - ((w + w2) / 2) + w, (this.getParentEngine().getWindowSize().getHeight() / 2) + (graphics.getFontMetrics().getDescent() + this.getParentEngine().logoRenderer.getLogoHeight() + 59));
            graphics.setColor(Color.GREEN);
            ////
            //



        //Back
        ///
        if(selected == 5){
            graphics.setColor(Color.GREEN);
        }else{
            graphics.setColor(Color.DARK_GRAY);
        }
        String sco3 = "Back";
        int w3 = graphics.getFontMetrics().stringWidth(sco3);
        graphics.drawChars(sco3.toCharArray(), 0, sco3.toCharArray().length, (this.getParentEngine().getWindowSize().getWidth() / 2) - (w3 / 2), (this.getParentEngine().getWindowSize().getHeight() / 2) + (graphics.getFontMetrics().getDescent() + this.getParentEngine().logoRenderer.getLogoHeight() + 108));
        ///
    }

    @Override
    protected final void onKeyPressed(KeyEvent e){

        int key = e.getKeyCode();
        if(key == KeyEvent.VK_UP){
            if(selected == 1){
                selected = 5;
            }else if(selected == 2){
                selected = 1;
            }else if(selected == 3){
                selected = 2;
            }else if(selected == 4){
                selected = 3;
            }else if(selected == 5){
                selected = 4;
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
                selected = 5;
            }else if(selected == 5){
                selected = 1;
            }
            this.getParentEngine().sounds.s_select.play(this.getParentEngine());
        }else if(key == KeyEvent.VK_ENTER){
            if(selected == 1){
                if(this.getParentEngine().enableMusic){
                    this.getParentEngine().enableMusic = false;
                    this.getParentEngine().sounds.s_backgroundmusic.pause();
                }else{
                    this.getParentEngine().enableMusic = true;
                    this.getParentEngine().sounds.s_backgroundmusic.play();
                }
            }else if(selected == 2){
                if(! this.getParentEngine().isMuted()){
                    this.getParentEngine().muteSounds();
                }else{
                    this.getParentEngine().unmuteSounds();
                }
            }else if(selected == 3){
                //Controls screen
                this.getParentEngine().sounds.s_enter.play(this.getParentEngine());
                this.getParentEngine().setScreen(new ControlsMenu(this.getParentEngine()));
            }else if(selected == 4){
                this.getParentEngine().sounds.s_enter.play(this.getParentEngine());
                if(this.getParentEngine().difficulty == Difficulty.Easy){
                    this.getParentEngine().difficulty = Difficulty.Normal;
                }else if(this.getParentEngine().difficulty == Difficulty.Normal){
                    this.getParentEngine().difficulty = Difficulty.Hard;
                }else if(this.getParentEngine().difficulty == Difficulty.Hard){
                    this.getParentEngine().difficulty = Difficulty.Expert;
                }else if(this.getParentEngine().difficulty == Difficulty.Expert){
                    this.getParentEngine().difficulty = Difficulty.Easy;
                }
            }else if(selected == 5){
                this.getParentEngine().sounds.s_enter.play(this.getParentEngine());
                this.getParentEngine().setScreen(new MainMenu(this.getParentEngine()));
            }
        }


    }

    @Override
    protected final void onKeyReleased(KeyEvent e){
        // Don't implement
    }
}
