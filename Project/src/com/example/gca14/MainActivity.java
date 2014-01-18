package com.example.gca14;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

/**
 * The main activity.
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Remove title.
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//Set as full screen and set main view to GameSurface.
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(new GameSurface(this));
		
		//Register accelerator to record tilt of screen.
		((SensorManager)getSystemService(Context.SENSOR_SERVICE)).registerListener(
			     new SensorEventListener() {    
			        @Override  
			        public void onSensorChanged(SensorEvent event) {
			        	//Update tilt values in game thread.
			        	GameThread.tiltX = event.values[0];
			        	GameThread.tiltY = event.values[1];
			        }
			        
			        @Override  
			        public void onAccuracyChanged(Sensor sensor, int accuracy) {
			        	//Not applicable.
			        }
			    }, ((SensorManager)getSystemService(Context.SENSOR_SERVICE))
			    .getSensorList(Sensor.TYPE_ACCELEROMETER).get(0), SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}