package com.example.gca14;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

public class Unicorn extends GameObject {
	private Paint paint;
	public int unicornImage;
	
	private float xVelocity;
	private float walkDistance;
	private float walkedDistance;
	
	private long lastStepIncrease;
	private float stepAmount;
	
	public boolean dead;
	
	public Unicorn(int tx, int ty){
		x = tx;
		y = ty;
		
		dead = false;
		
		walkDistance = 0;
		walkedDistance = 0;
		xVelocity = (int) ((Math.random()-0.5f)*6);
		if(xVelocity == 0)
			xVelocity = 1;
		
		lastStepIncrease = 0;
		
		width = 256;
		height = 154;
		unicornImage = (int) Math.round(Math.random() * (GameSurface.numUnicorns - 1));
		paint = new Paint();
	}
	
	@Override
	public void update() {
		
		if(dead)
			return;
		
		x += xVelocity;
		walkedDistance += xVelocity;
		
		if(Math.abs(walkedDistance) >= Math.abs(walkDistance) || x + width < 0 || x > GameThread.getWorldWidth()){
			walkDistance = (float) (Math.random()*500);
			walkedDistance = 0;
			xVelocity *= -1;
		}
		
		if(System.currentTimeMillis() - lastStepIncrease > 700)
			stepAmount += 0.5f;
		
		
	}

	@Override
	public void draw(Canvas canvas) {
		Matrix m = new Matrix();
		if(xVelocity < 0)
			m.preScale(-1, 1, GameSurface.unicorns[unicornImage].getWidth()/2, 0);
		m.postTranslate(x, (float) (y+ Math.sin(stepAmount)*6));
		
		if(dead){
			canvas.drawBitmap(GameSurface.deadUnicorn, m, paint);
		}else{
			canvas.drawBitmap(GameSurface.unicorns[unicornImage], m, paint);
		}
		
	}

}
