package com.example.gca14;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

/**
 * Asteroid.
 */
public class Asteroid extends GameObject {
	
	/** Horizontal velocity of asteroid. */
	private float xVelocity;
	/** Vertical velocity of asteroid. */
	private float yVelocity;
	
	private float rotationSpeed;
	private float rotation;
	
	public boolean redAsteroid;
	private int asteroidImage;
	
	private Paint paint;
	
	public float scale;
	
	public Asteroid(int tx, int ty){
		destroy = false;
		x = tx;
		y = ty;
		xVelocity = (float) ((Math.random() - 0.5f)*5);
		yVelocity = (float) (Math.random()-2.5)*15;
		
		rotation = 0;
		rotationSpeed = (float) Math.random() - 0.5f;
		rotationSpeed *= 5;
		
		asteroidImage = (int) Math.round(Math.random() * (GameSurface.numAsteroids - 1));
		
		width = (float) (GameSurface.asteroids[asteroidImage].getWidth()*0.5f);
		
		scale = width/GameSurface.asteroids[asteroidImage].getWidth();
		height = GameSurface.asteroids[asteroidImage].getHeight()*scale;
		
		if(Math.random() > 0.8){
			redAsteroid = true;
		}
		
		paint = new Paint();
	}
	
	public void update(){
		x += xVelocity;
		y += yVelocity;
		
		rotation += rotationSpeed;
		
		if(y + height < 0){
			destroy = true;
			return;
		}
		
		if(x >= GameThread.getWorldWidth() && x > 0){
			xVelocity = (float) (Math.random()*-5);
		}else if(x <= 0 && x < 0){
			xVelocity = (float) (Math.random()*5);
		}
	}
	
	public void draw(Canvas canvas){
		
		Matrix m = new Matrix();
		m.preScale(scale, scale);
		m.postRotate(rotation, scale*GameSurface.asteroids[asteroidImage].getWidth()/2,
				scale*GameSurface.asteroids[asteroidImage].getHeight()/2);
		m.postTranslate(x, y);
		if(redAsteroid){
			canvas.drawBitmap(GameSurface.redAsteroids[asteroidImage], m, paint);
		}else{
			canvas.drawBitmap(GameSurface.asteroids[asteroidImage], m, paint);
		}
		
		if(GameThread.debug){
			paint.setColor(0xFFFF0000);
			canvas.drawText("(" + x + "x" + y + ")", x, y - 10, paint);
		}
	}
}
