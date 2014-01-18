package com.example.gca14;

import java.util.Vector;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameThread extends Thread{
	
	public static float tiltX;
	public static float tiltY;
	
	public static long startTime;
	
	private GameSurface gameSurface;
	private SurfaceHolder surfaceHolder;
	
	public static Asteroid player;
	
	public Vector<GameObject> objects;
	
	public GameThread(SurfaceHolder holder, GameSurface surface){
		surfaceHolder = holder;
		gameSurface = surface;
		player = new Asteroid();
		startTime = System.currentTimeMillis();
		
		for(int i = 0; i < 10; i++){
			objects.add(new Asteroid());
		}
	}
	
	private boolean running;
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	@SuppressLint("WrongCall")
	@Override
	public void run() {
		Canvas gameCanvas = null;
		running = true;
		while (running) {
			player.x -= tiltX;
			for(int i = 0; i < objects.size(); i++){
				((Asteroid)objects.get(i)).update();
			}
			
			gameCanvas = surfaceHolder.lockCanvas();
			if(gameCanvas != null){
				synchronized (surfaceHolder) {
					gameSurface.onDraw(gameCanvas);
				}
				surfaceHolder.unlockCanvasAndPost(gameCanvas);
			}
		}
	}
}
