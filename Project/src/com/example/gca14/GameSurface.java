package com.example.gca14;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Surface view on which game is drawn.
 */
public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {

	private GameThread thread;
	private Paint paint;
	
	public GameSurface(Context context) {
		super(context);
		
		getHolder().addCallback(this);
		
		paint = new Paint();
		paint.setTextSize(20);
		paint.setColor(0xFFFFFFFF);
		
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
		canvas.translate(0, - GameThread.player.y);
		
		GameThread.player.draw(canvas);
		
		for(int i = 0; i < GameThread.objects.size(); i++){
			GameThread.objects.get(i).draw(canvas);
		}
		
		//Reset canvas offset.
		canvas.translate(0, GameThread.player.y);
		canvas.drawText("Time: " + (System.currentTimeMillis() - GameThread.startTime), 1, 20, paint);
		canvas.drawText("x: " + GameThread.player.x + " y: " + GameThread.player.y, 1, 40, paint);
	}
	
}
