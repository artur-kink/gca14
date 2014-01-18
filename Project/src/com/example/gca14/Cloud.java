package com.example.gca14;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Cloud extends GameObject {
	private Paint paint;
	
	float xVelocity;
	
	public Cloud(int tx, int ty){
		x = tx;
		y = ty;
		height = 10;
		width = 200;
		paint = new Paint();
		xVelocity = (float) (Math.random()*3);
	}
	
	@Override
	public void update() {
		x += xVelocity;
		
		if(y + height < GameThread.getWorldY())
			destroy = true;
		
		if(x + width < 0)
			destroy = true;
		else if(x > GameThread.getWorldWidth())
			destroy = true;
	}

	@Override
	public void draw(Canvas canvas) {
		paint.setColor(0xFF0000FF);
		Rect pos = new Rect((int)x, (int)y, (int)x + (int)width, (int)y + (int)height);
		canvas.drawRect(pos, paint);
		
		if(GameThread.debug)
			canvas.drawText("(" + x + "x" + y + ")", x, y - 10, paint);
	}

}
