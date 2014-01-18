package com.example.gca14;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

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
	
	private Paint paint;
	
	public Asteroid(int tx, int ty){
		destroy = false;
		x = tx;
		y = ty;
		xVelocity = (float) (Math.random()*5);
		yVelocity = (float) (Math.random()-1);
		
		rotation = 0;
		rotationSpeed = (float) Math.random()-1;
		rotationSpeed *= 5;
		
		width = 100;
		height = 100;
		
		paint = new Paint();
	}
	
	public void update(){
		x += xVelocity;
		y += yVelocity;
		
		rotation += rotationSpeed;
		
		if(y + height < GameThread.getWorldY()){
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
		paint.setColor(0xFFFF0000);
		Matrix m = new Matrix();
		m.preScale(0.5f, 0.5f);
		m.postRotate(rotation, 0.5f*GameSurface.asteroids[0].getWidth()/2, 0.5f*GameSurface.asteroids[0].getHeight()/2);
		m.postTranslate(x, y);
		canvas.drawBitmap(GameSurface.asteroids[0], m, paint);
		
		if(GameThread.debug)
			canvas.drawText("(" + x + "x" + y + ")", x, y - 10, paint);
	}
}
