package glide.soundsystem;

import java.applet.Applet;
import java.applet.AudioClip;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound2 {
	private Clip clip;
	
	public Sound2(String filename){
		try{
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Sound.class.getResource(filename));
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
		}catch(Exception e){
			
		}
	}
	
	public void play(){
		try{
			new Thread(){
				public void run(){
					clip.start();
					while(clip.isRunning()){
						
					}
					clip.stop();
				}
			}.start();
		}catch(Exception e){
			
		}
	}
	
	public void loop(){
		try{
			new Thread(){
				public void run(){
					clip.loop(Clip.LOOP_CONTINUOUSLY);
				}
			}.start();
		}catch(Exception e){
			
		}
	}
	
	public void setVolume(float i){
		((FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(i);
	}
}
