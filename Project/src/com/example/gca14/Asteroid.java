package com.example.gca14;

import android.graphics.Canvas;
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
	
	private Paint paint;
	
	public Asteroid(int tx, int ty){
		destroy = false;
		x = tx;
		y = ty;
		xVelocity = (float) (Math.random()*5);
		yVelocity = (float) (Math.random()*3);
		
		width = 100;
		height = 100;
		
		paint = new Paint();
	}
	
	public void update(){
		x += xVelocity;
		y += yVelocity;
		
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
		Rect pos = new Rect((int)x, (int)y, (int)x + (int)width, (int)y + (int)height);
		canvas.drawRect(pos, paint);
		
		if(GameThread.debug)
			canvas.drawText("(" + x + "x" + y + ")", x, y - 10, paint);
	}
}
