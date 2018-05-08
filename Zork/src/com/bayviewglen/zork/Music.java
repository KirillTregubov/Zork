package com.bayviewglen.zork;
import java.io.*;
import sun.audio.*;
public class Music{
	static AudioStream BGM;
	public static void playMusic (String fileLocation) {
		{       
			AudioPlayer MGP = AudioPlayer.player;
			AudioData MD;

			ContinuousAudioDataStream loop = null;

			try
			{
				InputStream test = new FileInputStream(fileLocation + "music1.wav");
				BGM = new AudioStream(test);
				
				AudioPlayer.player.start(BGM);

			}
			catch(FileNotFoundException e){
				System.out.print(e.toString());
			}
			catch(IOException error)
			{
				System.out.print(error.toString());
			}



		}

	}
	public static void stopMusic() {
		AudioPlayer.player.stop(BGM);
	}
	
}
