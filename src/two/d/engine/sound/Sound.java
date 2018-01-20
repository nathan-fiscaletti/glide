package two.d.engine.sound;

import two.d.engine.Engine;

public class Sound {
	private kuusisto.tinysound.Sound sound;
	
	public Sound(String url)
	{
		this.sound = kuusisto.tinysound.TinySound.loadSound(url);
	}
	
	public void play(Engine engine)
	{
		if (engine.enableSounds) {
			sound.play();
		}
	}
}
