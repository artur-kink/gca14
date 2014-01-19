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
	
	public static final int numAsteroids = 3;
	public static Bitmap asteroids[];
	public static Bitmap redAsteroids[];
	public static Bitmap playerAsteroid;
	public static Bitmap playerFireball[];
	
	public static final int numClouds = 3;
	public static Bitmap clouds[];
	
	public GameSurface(Context context) {
		super(context);
		
		getHolder().addCallback(this);
		
		paint = new Paint();
		paint.setTextSize(20);
		paint.setColor(0xFFFFFFFF);
		
		setFocusable(true);
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		
		asteroids = new Bitmap[numAsteroids];
		asteroids[0] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroid1);
		asteroids[1] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroid2);
		asteroids[2] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroid3);
		
		redAsteroids = new Bitmap[numAsteroids];
		redAsteroids[0] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroid1_red);
		redAsteroids[1] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroid2_red);
		redAsteroids[2] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroid3_red);
		
		playerAsteroid = BitmapFactory.decodeResource(getResources(), R.drawable.asteroid_player);
		playerFireball = new Bitmap[2];
		playerFireball[0] = BitmapFactory.decodeResource(getResources(), R.drawable.fireball1);
		playerFireball[1] = BitmapFactory.decodeResource(getResources(), R.drawable.fireball2);
		
		clouds = new Bitmap[numClouds];
		clouds[0] = BitmapFactory.decodeResource(getResources(), R.drawable.cloud1);
		clouds[1] = BitmapFactory.decodeResource(getResources(), R.drawable.cloud2);
		clouds[2] = BitmapFactory.decodeResource(getResources(), R.drawable.cloud3);
		
		thread = new GameThread(getHolder(), this);
		//Update GameThread screen and height information.
		GameThread.width = getWidth();
		GameThread.height = getHeight();
		
		thread.start();
	}

	public void loadBackground(int id){
		BitmapFactory.Options o=new BitmapFactory.Options();
		//o.inSampleSize = 1;
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
		float bgScale = ((float)getWidth())/((float)bg.getWidth());
		canvas.save();
		canvas.scale(bgScale, bgScale);
		canvas.drawBitmap(bg, 0, 0, paint);
		canvas.restore();
		canvas.save();
		
		//Translate canvas to fit current world.
		canvas.translate(0, 0);
		
		GameThread.player.draw(canvas);
		
		for(int i = 0; i < GameThread.objects.size(); i++){
			GameThread.objects.get(i).draw(canvas);
		}
		
		//Reset canvas offset.
		canvas.restore();
		canvas.drawText("Time: " + (System.currentTimeMillis() - GameThread.startTime), 1, 20, paint);
		canvas.drawText("x: " + GameThread.player.x + " y: " + GameThread.player.y, 1, 40, paint);
	}
	
}
