package glide.game.sounds;

import jtwod.engine.sound.Music;
import jtwod.engine.sound.Sound;

public final class GlideSounds {
    public Sound s_explosion;
    public Sound s_hurt;
    public Sound s_pickup;
    public Sound s_shoot;
    public Sound s_select;
    public Sound s_enter;
    public Sound s_gameover;
    public Sound s_dropdeath;

    public Music s_backgroundmusic;

    public void loadSounds()
    {
        s_explosion = new Sound("/sounds/explode.wav");
        s_hurt = new Sound("/sounds/hurt.wav");
        s_pickup = new Sound("/sounds/pickup.wav");
        s_shoot = new Sound("/sounds/shoot.wav");
        s_select = new Sound("/sounds/select.wav");
        s_enter = new Sound("/sounds/enter.wav");
        s_gameover = new Sound("/sounds/gameover.wav");
        s_dropdeath = new Sound("/sounds/explode.wav");

        s_backgroundmusic = new Music("/sounds/cr.wav");
        s_backgroundmusic.play();
    }
}
