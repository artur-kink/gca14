package com.example.gca14;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;


/**
 * Player's asteroid class.
 */
public class Player extends GameObject {

	public float yVelocity;
	
	public Player(){
		x = y = 0;
		yVelocity = 1;
		width = 100;
		height = 100;
	}
	
	@Override
	public void update() {
		y += yVelocity;
	}

	@Override
	public void draw(Canvas canvas) {
		Rect pos = new Rect((int)x, (int)y, (int)x + (int)width, (int)y + (int)height);
		Paint paint = new Paint();
		paint.setColor(0xFFFFFFFF);
		canvas.drawRect(pos, paint);
	}
	
}
