package com.example.gca14;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Asteroid.
 */
public class Asteroid extends GameObject {
	
	private int xVelocity;
	private int yVelocity;
	private Paint paint;
	public Asteroid(int tx, int ty){
		destroy = false;
		x = tx;
		y = ty;
		xVelocity = (int)(Math.random()*5);
		yVelocity = 2;
		paint = new Paint();
	}
	
	public void update(){
		x += xVelocity;
		y += yVelocity;
	}
	
	public void draw(Canvas canvas){
		paint.setColor(0xFFFF0000);
		Rect pos = new Rect(x, y, x + 100, y + 100);
		canvas.drawRect(pos, paint);
		if(GameThread.debug)
			canvas.drawText("(" + x + "x" + y + ")", x, y - 10, paint);
	}
}
