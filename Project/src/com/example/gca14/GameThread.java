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
	
	public static float worldY;
	
	/** Surface the game is drawn on. */
	private GameSurface gameSurface;
	private SurfaceHolder surfaceHolder;
	
	public static Player player;
	
	public static Vector<GameObject> objects;
	
	public static int stage;
	
	public GameThread(SurfaceHolder holder, GameSurface surface){
		surfaceHolder = holder;
		gameSurface = surface;
		player = new Player();
		worldY = 0;
		startTime = System.currentTimeMillis();
		
		objects = new Vector<GameObject>();
		stage = 0;
	}
	
	private boolean running;
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	
	/**
	 * Return scale of world.
	 * @return Float scale value of world.
	 */
	public static float getScale(){
		return 100/player.width;
	}
	
	/**
	 * Returns world width.
	 * This value is the screen width scaled.
	 * @return Width of game world.
	 */
	public static int getWorldWidth(){
		return (int) (width);
	}
	
	/**
	 * Returns world height.
	 * This value is the screen height scaled.
	 * @return Height of game world.
	 */
	public static int getWorldHeight(){
		return (int) (height);
	}
	
	/**
	 * Get Y position of world.
	 * @return
	 */
	public static int getWorldY(){
		return (int)(System.currentTimeMillis()-startTime)/1000;
	}
	
	@SuppressLint("WrongCall")
	@Override
	/**
	 * Main game loop.
	 */
	public void run() {
		Canvas gameCanvas = null;
		running = true;
		
		for(int i = 0; i < 10; i++){
			objects.add(new Asteroid((int) (Math.random() * getWorldWidth()), (int) (Math.random() *(getWorldY() + getWorldHeight() + 100))));
		}
		
		//Game loop.
		while (running) {
			
			if(player.y > getWorldY() + getWorldHeight()){
				stage++;
				player.y = 0;
				if(stage == 1)
					gameSurface.loadBackground(R.drawable.bg_clouds);
				else
					gameSurface.loadBackground(R.drawable.bg_ground);
			}
			
			player.x -= tiltX*2;
			
			player.update();
			
			//Create player collision rect.
			Rect playerRect = new Rect((int)player.x, (int)player.y, (int)player.x + (int)player.width, (int)player.y + (int)player.height);
			//Check for player collision with game objects.
			for(int i = 0; i < objects.size(); i++){
				GameObject object = objects.get(i);
				Rect objectRect = new Rect((int)object.x, (int)object.y, (int)object.x + (int)object.width, (int)object.y + (int)object.height);
				if(playerRect.intersect(objectRect)){
					if(object instanceof Asteroid){
						player.increaseSize();
						object.destroy = true;
					}else{
						player.decreaseSize();
						object.destroy = true;
					}
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
			
			//Add new game objects if there aren't enough.
			if(objects.size() < 15){
				if(stage == 0)
					objects.add(new Asteroid((int) (Math.random()*getWorldWidth()), getWorldY() + getWorldHeight() + 100));
				else if(stage == 1)
					objects.add(new Cloud((int) (Math.random()*getWorldWidth()), getWorldY() + getWorldHeight() + 100));
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
