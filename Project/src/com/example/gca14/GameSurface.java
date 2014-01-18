package com.example.gca14;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Surface view on which game is drawn.
 */
public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {

	private GameThread thread;
	private Paint paint;
	private Bitmap bg;
	
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
		loadBackground(R.drawable.bg_space);
		//Update GameThread screen and height information.
		GameThread.width = getWidth();
		GameThread.height = getHeight();
		
		thread.start();
	}

	public void loadBackground(int id){
		BitmapFactory.Options o=new BitmapFactory.Options();
		o.inSampleSize = 1;
		o.inDither = true;
		o.inPurgeable = true;
		o.inMutable = false;
		bg = BitmapFactory.decodeResource(getResources(), id, o);
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
	}
	
	@Override
	/**
	 * GameSurface draw method. Game is drawn in this method.
	 */
	protected void onDraw(Canvas canvas) {
		//Clear canvas.
		canvas.drawBitmap(bg, 0, 0, paint);
		canvas.save();
		
		//Scale and translate canvas to fit current world.
		canvas.scale(GameThread.getScale(), GameThread.getScale());
		canvas.translate(0, - GameThread.getWorldY());
		
		
		GameThread.player.draw(canvas);
		
		for(int i = 0; i < GameThread.objects.size(); i++){
			GameThread.objects.get(i).draw(canvas);
		}
		
		//Reset canvas scale and offset.
		canvas.restore();
		canvas.drawText("Time: " + (System.currentTimeMillis() - GameThread.startTime), 1, 20, paint);
		canvas.drawText("x: " + GameThread.player.x + " y: " + GameThread.player.y, 1, 40, paint);
	}
	
}
