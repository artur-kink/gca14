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

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requesting to turn the title OFF
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// making it full screen
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// set our MainGamePanel as the View
		setContentView(new GameSurface(this));
		
		((SensorManager)getSystemService(Context.SENSOR_SERVICE)).registerListener(
			     new SensorEventListener() {    
			        @Override  
			        public void onSensorChanged(SensorEvent event) {
			        	GameThread.tiltX = event.values[0];
			        	GameThread.tiltY = event.values[1];
			        }
			        
			        @Override  
			        public void onAccuracyChanged(Sensor sensor, int accuracy) {
			        	
			        }
			    },
			    ((SensorManager)getSystemService(Context.SENSOR_SERVICE))
			    .getSensorList(Sensor.TYPE_ACCELEROMETER).get(0),   
			     SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}