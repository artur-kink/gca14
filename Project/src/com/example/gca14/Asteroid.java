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
	
	public Asteroid(int tx, int ty){
		x = tx;
		y = ty;
		xVelocity = 0;
		yVelocity = 2;
	}
	
	public void update(){
		x += xVelocity;
		y += yVelocity;
	}
	
	public void draw(Canvas canvas){
		Paint paint = new Paint();
		paint.setColor(0xFFFF0000);
		Rect pos = new Rect(x, y, x + 100, y + 100);
		canvas.drawRect(pos, paint);
		canvas.drawText("(" + x + "x" + y + ")", x, y - 10, paint);
	}
}
