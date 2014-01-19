package com.example.gca14;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

public class Cloud extends GameObject {
	private Paint paint;
	
	float xVelocity;
	
	public boolean collided;
	
	private int cloudImage;
	public float scale;
	
	public Cloud(int tx, int ty){
		collided = false;
		x = tx;
		y = ty;
		
		cloudImage = (int) Math.round(Math.random() * (GameSurface.numClouds - 1));
		
		width = (float) (GameSurface.clouds[cloudImage].getWidth() - 50 - Math.random()*50);
		scale = width/GameSurface.asteroids[cloudImage].getWidth();
		height = GameSurface.clouds[cloudImage].getHeight()*scale;
		
		paint = new Paint();
		xVelocity = 0.01f;
	}
	
	@Override
	public void update() {
		x += xVelocity;
		
		y -= 20;
		
		if(y + height < 0){
			destroy = true;
			return;
		}
		
		if(x + width < 0)
			destroy = true;
		else if(x > GameThread.getWorldWidth())
			destroy = true;
	}

	@Override
	public void draw(Canvas canvas) {
		
		Matrix m = new Matrix();
		m.preScale(scale, scale);
		m.postTranslate(x, y);
		canvas.drawBitmap(GameSurface.clouds[cloudImage], m, paint);
		
		if(GameThread.debug){
			paint.setColor(0xFF0000FF);
			canvas.drawText("(" + x + "x" + y + ")", x, y - 10, paint);
		}
	}
	
	@Override
	public Rect getCollisionRect(){
		Rect rect = super.getCollisionRect();
		rect.left += 2;
		rect.top += 2;
		rect.bottom -= 2;
		rect.right -= 2;
		return rect;
	}

}
