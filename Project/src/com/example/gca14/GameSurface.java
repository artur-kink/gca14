package com.example.gca14;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Surface view on which game is drawn.
 */
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
	/**
	 * GameSurface draw method. Game is drawn in this method.
	 */
	protected void onDraw(Canvas canvas) {
		// fills the canvas with black
		canvas.drawColor(Color.BLACK);
		Rect pos = new Rect(GameThread.player.x, GameThread.player.y,
				GameThread.player.x + 100, GameThread.player.y + 100);
		Paint paint = new Paint();
		paint.setColor(0xFFFFFFFF);
		canvas.drawRect(pos, paint);
		
		paint.setColor(0xFFFF0000);
		for(int i = 0; i < GameThread.objects.size(); i++){
			pos = new Rect(GameThread.objects.get(i).x, GameThread.objects.get(i).y,
					GameThread.objects.get(i).x + 100, GameThread.objects.get(i).y + 100);
			canvas.drawRect(pos, paint);
		}
		
		paint.setColor(0xFFFFFFFF);
		paint.setTextSize(20);
		canvas.drawText("Time: " + (System.currentTimeMillis() - GameThread.startTime), 1, 20, paint);
		canvas.drawText("x: " + GameThread.player.x + " y: " + GameThread.player.y, 1, 40, paint);
	}
	
}
