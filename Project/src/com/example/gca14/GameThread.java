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
	
	public static final int asteroids = 7;
	public static final int clouds = 5;
	
	public static boolean endOfStage;
	public static boolean startOfStage;
	public static long stageStartTime;
	public static float fadeValue;
	
	public GameThread(SurfaceHolder holder, GameSurface surface){
		surfaceHolder = holder;
		gameSurface = surface;
		player = new Player();
		worldY = 0;
		startTime = System.currentTimeMillis();
		
		AudioPlayer.initSounds();
		objects = new Vector<GameObject>();
		stage = 0;
		endOfStage = false;
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
		if(stage > 2)
			stage = 0;
		
		endOfStage = false;
		startOfStage = true;
		stageStartTime = System.currentTimeMillis();
		
		if(stage == 0){
			gameSurface.loadBackground(R.drawable.bg_space);
			for(int i = 0; i < asteroids; i++)
				objects.add(new Asteroid((int) (Math.random()*getWorldWidth()), 500 + (int) (Math.random() * getWorldHeight())));
		}
		else if(stage == 1){
			player.drawFireball = true;
			player.yVelocity *= 0.65f;
			player.width *= 0.45f;
			player.updateSize();
			gameSurface.loadBackground(R.drawable.bg_clouds);
			for(int i = 0; i < clouds; i++)
				objects.add(new Cloud((int) (Math.random()*getWorldWidth()), 500 + (int) (Math.random() * getWorldHeight())));
		}
		else{
			player.width *= 2.25f;
			player.yVelocity = 15;
			player.updateSize();
			player.drawFireball = true;
			for(int i = 0; i < 5; i++)
				objects.add(new Unicorn((int) (Math.random()*getWorldWidth()), (int) (getWorldHeight() - (Math.random() * getWorldHeight()*0.24) - GameSurface.unicorns[0].getHeight())));
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
		
		AudioPlayer.playSound(AudioPlayer.exhilarate);
		
		//Game loop.
		while (running) {
			
			if(player.y > getWorldHeight()){
				setStage(stage+1);
			}else if(stage != 2 && player.y > getWorldHeight()*0.75){
				fadeValue = (player.y - getWorldHeight()*0.75f)/(getWorldHeight() - getWorldHeight()*0.75f);
				endOfStage = true;
			}
			
			if(endOfStage){
				player.y += 5;
				
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
				
				continue;
			}else if(startOfStage){
				if(System.currentTimeMillis() - stageStartTime > 3000){
					startOfStage = false;
				}
				player.y = (((float)(System.currentTimeMillis() - stageStartTime))/3000.0f)*100;
				fadeValue = 1.0f - (((float)(System.currentTimeMillis() - stageStartTime))/3000.0f);
				
				//Draw game state.
				gameCanvas = surfaceHolder.lockCanvas();
				if(gameCanvas != null){
					synchronized (surfaceHolder) {
						gameSurface.onDraw(gameCanvas);
					}
					surfaceHolder.unlockCanvasAndPost(gameCanvas);
				}
				
				continue;
			}
			
			player.x -= tiltX*5;
			player.rVelocity = tiltX*-2;
			
			player.update();
			
			//Create player collision rect.
			Rect playerRect = player.getCollisionRect();
			//Check for player collision with game objects.
			for(int i = 0; i < objects.size(); i++){
				GameObject object = objects.get(i);
				Rect objectRect = object.getCollisionRect();
				if(playerRect.intersect(objectRect)){
					if(object instanceof Asteroid){
						
						if(((Asteroid)object).smoke){
							continue;
						}
						
						//Play random hit sfx.
						int rand = (int) (Math.random()*3);
						if(rand < 1){
							AudioPlayer.playSound(AudioPlayer.hit1);
						}else if(rand < 2){
							AudioPlayer.playSound(AudioPlayer.hit2);
						}else{
							AudioPlayer.playSound(AudioPlayer.hit3);
						}
						
						if(((Asteroid)object).redAsteroid){
							player.decreaseSize(5);
						}else{
							player.increaseSize(1);
						}
						((Asteroid) object).smoke = true;
						((Asteroid)object).smokeTime = System.currentTimeMillis();
					}else if(object instanceof Cloud && ((Cloud)object).collided == false){
						AudioPlayer.playSound(AudioPlayer.hitcloud);
						player.decreaseSize(1);
						((Cloud)object).collided = true;
					}else if(object instanceof Unicorn){
						
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
					objects.add(new Cloud((int) (Math.random()*getWorldWidth()) - 75, (int) (getWorldHeight() + 300 * Math.random())));
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
