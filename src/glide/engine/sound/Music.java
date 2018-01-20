package glide.engine.sound;

import glide.game.Glide;

public class Music {
	public kuusisto.tinysound.Music music;
	
	public Music(String url)
	{
		this.music = kuusisto.tinysound.TinySound.loadMusic(url);
	}
	
	public void play()
	{
		if (Glide.music) {
			music.play(true, 0.25f);
		}
	}
	
	public void pause()
	{
		music.pause();
	}
	
}
