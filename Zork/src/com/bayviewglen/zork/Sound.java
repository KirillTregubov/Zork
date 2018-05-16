package com.bayviewglen.zork;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	static private Clip clip;
	public Sound(String fileLocation) {
		// specify the sound to play
		// (assuming the sound can be played by the audio system)
		// from a wave File
		try {
			File file = new File(fileLocation);
			if (file.exists()) {
				AudioInputStream sound = AudioSystem.getAudioInputStream(file);
				// load the sound into memory (a Clip)
				clip = AudioSystem.getClip();
				clip.open(sound);
			}
			else {
				throw new RuntimeException("Sound: file not found: " + fileLocation);
			}
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
			throw new RuntimeException("Sound: Malformed URL: " + e);
		}
		catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
			throw new RuntimeException("Sound: Unsupported Audio File: " + e);
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Sound: Input/Output Error: " + e);
		}
		catch (LineUnavailableException e) {
			e.printStackTrace();
			throw new RuntimeException("Sound: Line Unavailable Exception Error: " + e);
		}

		
	}
	public void play(){ // plays the sound clip doesn't loop it
		//clip.setFramePosition(0);  this is for rewinding after using pause command but I don't think we need to rewind because close automatically rewinds
		clip.start();
	}
	public void loop(){ // play the clip and loops it 
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	public static void stop(){ //stops all sound
		clip.close();
	}
	public void pause() {// will pause the music and when resumed will continue from where it left off
		clip.stop(); 
		
	}
}