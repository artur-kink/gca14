package com.example.gca14;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;


/**
 * Player's asteroid class.
 */
public class Player extends GameObject {

	public Player(){
		x = y = 0;
	}
	
	@Override
	public void update() {
		
	}

	@Override
	public void draw(Canvas canvas) {
		Rect pos = new Rect(x, y, x + 100, y + 100);
		Paint paint = new Paint();
		paint.setColor(0xFFFFFFFF);
		canvas.drawRect(pos, paint);
	}
	
}
