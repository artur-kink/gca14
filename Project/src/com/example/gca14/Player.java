package com.example.gca14;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;


/**
 * Player's asteroid class.
 */
public class Player extends GameObject {

	public float yVelocity;
	public float rotation;
	public float rVelocity;
	public float scale;
	
	private int animation;
	private long lastAnimationUpdate;
	
	public boolean drawFireball;
	
	public Player(){
		x = y = 0;
		yVelocity = (float) 0.1;
		
		width = GameSurface.playerAsteroid.getWidth()*0.5f;
		
		scale = width/GameSurface.playerAsteroid.getWidth();
		height = GameSurface.playerAsteroid.getHeight()*scale;
		
		rotation = 0;
		rVelocity = 0;
		animation = 0;
		drawFireball = true;
	}
	
	@Override
	public void update() {
		y += yVelocity;
		rotation += rVelocity;
		if(x < 0)
			x = 0;
		else if(x > GameThread.getWorldWidth() - width)
			x = GameThread.getWorldWidth() - width;
		
		if(drawFireball){
			if(System.currentTimeMillis() - lastAnimationUpdate > 150){
				animation++;
				if(animation >= 2)
					animation = 0;
				lastAnimationUpdate = System.currentTimeMillis();
			}
			
		}
	}

	public void increaseSize(){
		width += 2;
		yVelocity += 0.05f;
		updateSize();
	}
	
	public void decreaseSize(){
		width -= 2;
		//yVelocity -= 0.05f;
		updateSize();
	}
	
	public void updateSize(){
		scale = width/GameSurface.playerAsteroid.getWidth();
		height = GameSurface.playerAsteroid.getHeight()*scale;
	}
	
	@Override
	public void draw(Canvas canvas) {
		Paint paint = new Paint();
		Matrix m = new Matrix();
		m.preScale(scale, scale);
		m.postRotate(rotation, scale*GameSurface.playerAsteroid.getWidth()/2, scale*GameSurface.playerAsteroid.getHeight()/2);
		m.postTranslate(x, y);
		canvas.drawBitmap(GameSurface.playerAsteroid, m, paint);
		
		m = new Matrix();
		m.preScale(scale*1.95f, scale*1.95f);
		m.postTranslate(x-(scale*1.95f*20), y-(scale*1.95f*160));
		canvas.drawBitmap(GameSurface.playerFireball[animation], m, paint);
	}
	
}
