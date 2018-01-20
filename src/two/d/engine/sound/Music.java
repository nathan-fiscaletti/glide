package two.d.engine.sound;

public class Music {
	public kuusisto.tinysound.Music music;
	
	public Music(String url)
	{
		this.music = kuusisto.tinysound.TinySound.loadMusic(url);
	}
	
	public void play()
	{
		music.play(true, 0.25f);
	}
	
	public void pause()
	{
		music.pause();
	}
	
}
