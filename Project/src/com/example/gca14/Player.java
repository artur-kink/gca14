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
	
	public Player(){
		x = y = 0;
		yVelocity = (float) 0.1;
		
		width = GameSurface.playerAsteroid.getWidth();
		
		scale = width/GameSurface.playerAsteroid.getWidth();
		height = GameSurface.playerAsteroid.getHeight()*scale;
		
		rotation = 0;
		rVelocity = 0;
	}
	
	@Override
	public void update() {
		y += yVelocity;
		rotation += rVelocity;
		if(x < 0)
			x = 0;
		else if(x > GameThread.getWorldWidth() - width)
			x = GameThread.getWorldWidth() - width;
		
	}

	public void increaseSize(){
		width += 2;
		yVelocity += 0.05f;
		scale = width/GameSurface.playerAsteroid.getWidth();
		height = GameSurface.playerAsteroid.getHeight()*scale;
	}
	
	public void decreaseSize(){
		width -= 2;
		scale = width/GameSurface.playerAsteroid.getWidth();
		yVelocity -= 0.05f;
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
	}
	
}
