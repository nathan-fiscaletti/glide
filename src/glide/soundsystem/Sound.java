package glide.soundsystem;

import glide.Glide;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {
	private AudioClip clip;
	
	public Sound(String filename){
		try{
			clip = Applet.newAudioClip(Sound.class.getResource(filename));
		}catch(Exception e){
			
		}
	}
	
	public void play(){
		try{
			new Thread(){
				public void run(){
					if(Glide.sounds){
						clip.play();
					}
				}
			}.start();
		}catch(Exception e){
			
		}
	}
	
	public void loop(){
		try{
			new Thread(){
				public void run(){
					clip.loop();
				}
			}.start();
		}catch(Exception e){
			
		}
	}
}
