package com.example.gca14;

import android.R;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class AudioPlayer {
	public static Context context;
	
	private static SoundPool soundPool;
	public static int hit1;
	public static int hit2;
	public static int hit3;
	public static int hitcloud;
	
	
	public static void initSounds() {
	     soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
	     hit1 = soundPool.load(context, com.example.gca14.R.raw.hit1, 1);
	     hit2 = soundPool.load(context, com.example.gca14.R.raw.hit2, 1);
	     hit3 = soundPool.load(context, com.example.gca14.R.raw.hit3, 1);
	     hitcloud = soundPool.load(context, com.example.gca14.R.raw.hitcloud, 1);
	}
	          
	public static void playSound(int id) {
	    /* Updated: The next 4 lines calculate the current volume in a scale of 0.0 to 1.0 */
	    AudioManager mgr = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
	    float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
	    float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);    
	    float volume = streamVolumeCurrent / streamVolumeMax;
	    
	    /* Play the sound with the correct volume */
	    soundPool.play(id, volume, volume, 1, 0, 1f);     
	}
}
