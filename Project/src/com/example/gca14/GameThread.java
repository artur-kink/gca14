package com.example.gca14;

import java.util.Vector;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Rect;
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
			objects.add(new Asteroid(100, 600));
		}
	}
	
	private boolean running;
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	public float getScale(){
		return 100/player.width;
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
			if(player.x < 0)
				player.x = 0;
			else if(player.x > gameSurface.getWidth() - 100)
				player.x = gameSurface.getWidth() - 100;
			
			player.update();
			
			//Create player collision rect.
			Rect playerRect = new Rect((int)player.x, (int)player.y, (int)player.x + (int)player.width, (int)player.y + (int)player.height);
			//Check for player collision with game objects.
			for(int i = 0; i < objects.size(); i++){
				GameObject object = objects.get(i);
				Rect objectRect = new Rect((int)object.x, (int)object.y, (int)object.x + (int)object.width, (int)object.y + (int)object.height);
				if(playerRect.intersect(objectRect)){
					player.width += 10;
					player.height += 10;
					player.yVelocity += 0.5;
					object.destroy = true;
				}
			}
			
			//Update game objects.
			for(int i = 0; i < objects.size(); i++){
				objects.get(i).update();
				
				//If game object is destroyed, remove it from list.
				if(objects.get(i).destroy){
					objects.remove(i);
					i--;
				}
			}
			
			//Add new game objects if there arent enough.
			if(objects.size() < 10){
				objects.add(new Asteroid(100, (int) (player.y + 1000)));
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
