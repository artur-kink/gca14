package com.example.gca14;

import java.util.Vector;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Main game thread.
 * All game logic is managed here.
 */
public class GameThread extends Thread{
	
	public static final boolean debug = true;
	
	/** Current x tilt value of device. */
	public static float tiltX;
	/** Current y tilt value of device. */
	public static float tiltY;
	
	/** Time in millis of when the game was started. */
	public static long startTime;
	
	public static int width;
	public static int height;
	
	/** Surface the game is drawn on. */
	private GameSurface gameSurface;
	private SurfaceHolder surfaceHolder;
	
	public static Player player;
	
	public static Vector<GameObject> objects;
	
	public GameThread(SurfaceHolder holder, GameSurface surface){
		surfaceHolder = holder;
		gameSurface = surface;
		player = new Player();
		startTime = System.currentTimeMillis();
		
		objects = new Vector<GameObject>();
		for(int i = 0; i < 10; i++){
			objects.add(new Asteroid(gameSurface.getMeasuredWidth()/2, 10));
		}
	}
	
	private boolean running;
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	@SuppressLint("WrongCall")
	@Override
	/**
	 * Main game loop.
	 */
	public void run() {
		Canvas gameCanvas = null;
		running = true;
		
		//Game loop.
		while (running) {
			player.x -= tiltX*3;
			player.y += 1;
			if(player.x < 0)
				player.x = 0;
			else if(player.x > gameSurface.getWidth() - 100)
				player.x = gameSurface.getWidth() - 100;
			
			//Update game objects.
			for(int i = 0; i < objects.size(); i++){
				objects.get(i).update();
				
				//If game object is destroyed, remove it from list.
				if(objects.get(i).destroy){
					objects.remove(i);
					i--;
				}
			}
			
			//Draw game state.
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
