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
	
	public static final boolean debug = false;
	
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
	
	public static final int asteroids = 10;
	public static final int clouds = 4;
	
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
	
	public void setStage(int s){
		stage = s;
		player.y = 0;
		objects.clear();
		
		if(stage == 0){
			gameSurface.loadBackground(R.drawable.bg_space);
			for(int i = 0; i < asteroids; i++)
				objects.add(new Asteroid((int) (Math.random()*getWorldWidth()), (int) (Math.random() * getWorldHeight())));
		}
		else if(stage == 1){
			gameSurface.loadBackground(R.drawable.bg_clouds);
			for(int i = 0; i < clouds; i++)
				objects.add(new Cloud((int) (Math.random()*getWorldWidth()), 500 + (int) (Math.random() * getWorldHeight())));
		}
		else{
			gameSurface.loadBackground(R.drawable.bg_ground);
		}
	}
	
	@SuppressLint("WrongCall")
	@Override
	/**
	 * Main game loop.
	 */
	public void run() {
		Canvas gameCanvas = null;
		running = true;
		
		setStage(0);
		
		//Game loop.
		while (running) {
			
			if(player.y > getWorldHeight()){
				setStage(stage+1);
			}
			
			player.x -= tiltX*5;
			player.rVelocity = tiltX*-2;
			
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
			if(stage == 0){
				while(objects.size() < asteroids){
					objects.add(new Asteroid((int) (Math.random()*getWorldWidth()), getWorldHeight() + 100));
				}
			}else if(stage == 1){
				while(objects.size() < clouds){
					objects.add(new Cloud((int) (Math.random()*getWorldWidth()), getWorldHeight() + 100));
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
