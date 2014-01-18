package com.example.gca14;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {

	private GameThread thread;
	
	public GameSurface(Context context) {
		super(context);
		
		getHolder().addCallback(this);
		
		thread = new GameThread(getHolder(), this);
		setFocusable(true);
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// fills the canvas with black
		canvas.drawColor(Color.BLACK);
		Rect pos = new Rect(GameThread.player.x, 1, GameThread.player.x + 100, 100);
		Paint paint = new Paint();
		paint.setColor(0xFFFFFFFF);
		canvas.drawRect(pos, paint);
		
		paint.setTextSize(20);
		canvas.drawText("" + (System.currentTimeMillis() - GameThread.startTime), 1, 20, paint);
	}
	
}
