package com.example.gca14;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

/**
 * Audio playing class. Loads and plays audio files.
 */
public class AudioPlayer {
	
	/** Program context. */
	public static Context context;
	
	/** Sound pool containing and playing audio files. */
	private static SoundPool soundPool;
	
	public static int hit1;
	public static int hit2;
	public static int hit3;
	public static int hitcloud;
	public static int die;
	
	/**
	 * Initializes audio and sound player.
	 * Must be called before AudioPlayer can be used.
	 */
	public static void initSounds() {
	     soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
	     hit1 = soundPool.load(context, com.example.gca14.R.raw.hit1, 1);
	     hit2 = soundPool.load(context, com.example.gca14.R.raw.hit2, 1);
	     hit3 = soundPool.load(context, com.example.gca14.R.raw.hit3, 1);
	     hitcloud = soundPool.load(context, com.example.gca14.R.raw.hitcloud, 1);
	     
	     die = soundPool.load(context, com.example.gca14.R.raw.die, 1);
	}
	          
	public static void playSound(int id) {
	    AudioManager mgr = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
	    float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
	    float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);    
	    float volume = streamVolumeCurrent / streamVolumeMax;
	    
	    //Play sound.
	    soundPool.play(id, volume, volume, 1, 0, 1f);     
	}
}
