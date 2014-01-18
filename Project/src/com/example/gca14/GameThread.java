package com.example.gca14;

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
	
	public GameThread(SurfaceHolder holder, GameSurface surface){
		surfaceHolder = holder;
		gameSurface = surface;
		player = new Asteroid();
		startTime = System.currentTimeMillis();
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
