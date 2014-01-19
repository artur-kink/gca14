package com.example.gca14;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
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
	public static Bitmap smoke[];
	public static Bitmap playerAsteroid;
	public static Bitmap playerFireball[];
	
	public static final int numClouds = 3;
	public static Bitmap clouds[];
	public static Bitmap fireClouds[][];
	
	public static final int numUnicorns = 3;
	public static Bitmap unicorns[];
	public static Bitmap deadUnicorn;
	
	public GameSurface(Context context) {
		super(context);
		
		getHolder().addCallback(this);
		
		AudioPlayer.context = context;
		
		paint = new Paint();
		paint.setTextSize(20);
		paint.setColor(0xFFFFFFFF);
		
		asteroids = new Bitmap[numAsteroids];
		asteroids[0] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroid1);
		asteroids[1] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroid2);
		asteroids[2] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroid3);
		
		redAsteroids = new Bitmap[numAsteroids];
		redAsteroids[0] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroid1_red);
		redAsteroids[1] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroid2_red);
		redAsteroids[2] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroid3_red);
		
		smoke = new Bitmap[numAsteroids];
		smoke[0] = BitmapFactory.decodeResource(getResources(), R.drawable.smoke1);
		smoke[1] = BitmapFactory.decodeResource(getResources(), R.drawable.smoke2);
		smoke[2] = BitmapFactory.decodeResource(getResources(), R.drawable.smoke3);
		
		playerAsteroid = BitmapFactory.decodeResource(getResources(), R.drawable.asteroid_player);
		playerFireball = new Bitmap[2];
		playerFireball[0] = BitmapFactory.decodeResource(getResources(), R.drawable.fireball1);
		playerFireball[1] = BitmapFactory.decodeResource(getResources(), R.drawable.fireball2);
		
		clouds = new Bitmap[numClouds];
		clouds[0] = BitmapFactory.decodeResource(getResources(), R.drawable.cloud1);
		clouds[1] = BitmapFactory.decodeResource(getResources(), R.drawable.cloud2);
		clouds[2] = BitmapFactory.decodeResource(getResources(), R.drawable.cloud3);
		
		fireClouds = new Bitmap[numClouds][2];
		fireClouds[0][0] = BitmapFactory.decodeResource(getResources(), R.drawable.cloud1_fire1);
		fireClouds[1][0] = BitmapFactory.decodeResource(getResources(), R.drawable.cloud2_fire1);
		fireClouds[2][0] = BitmapFactory.decodeResource(getResources(), R.drawable.cloud3_fire1);
		fireClouds[0][1] = BitmapFactory.decodeResource(getResources(), R.drawable.cloud1_fire2);
		fireClouds[1][1] = BitmapFactory.decodeResource(getResources(), R.drawable.cloud2_fire2);
		fireClouds[2][1] = BitmapFactory.decodeResource(getResources(), R.drawable.cloud3_fire2);
		
		unicorns = new Bitmap[numUnicorns];
		unicorns[0] = BitmapFactory.decodeResource(getResources(), R.drawable.unicorn_1);
		unicorns[1] = BitmapFactory.decodeResource(getResources(), R.drawable.unicorn_2);
		unicorns[2] = BitmapFactory.decodeResource(getResources(), R.drawable.unicorn_3);
		deadUnicorn = BitmapFactory.decodeResource(getResources(), R.drawable.unicorn_death);
		
		thread = new GameThread(getHolder(), this);
		
		setFocusable(true);
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		
		//Update GameThread screen and height information.
		GameThread.width = getWidth();
		GameThread.height = getHeight();
		
		thread.setRunning(true);
		if(!thread.isAlive()){
			thread.start();
		}
	}

	public void loadBackground(int id){
		BitmapFactory.Options o=new BitmapFactory.Options();
		o.inSampleSize = 2;
		o.inDither = false;
		o.inPurgeable = true;
		o.inMutable = false;
		bg = BitmapFactory.decodeResource(getResources(), id, o);
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		thread.setRunning(true);
		GameThread.mPlayer.stop();
	}
	
	@Override
	/**
	 * GameSurface draw method. Game is drawn in this method.
	 */
	protected void onDraw(Canvas canvas) {
		
		//Calculate background scale.
		float bgScale = ((float)getHeight())/((float)bg.getHeight());
		if(bg.getWidth()*bgScale < getWidth()){
			bgScale = ((float)getWidth())/((float)bg.getWidth());
		}
		
		paint.setARGB(255, 255, 255, 255);
		
		canvas.save();
		canvas.scale(bgScale, bgScale);
		canvas.drawBitmap(bg, 0, 0, paint);
		canvas.restore();
		
		if(GameThread.scoreScreen){
			paint.setTextSize(86);
			int numKilled = 0;
			for(int i = 0; i < GameThread.objects.size(); i++){
				if(((Unicorn)GameThread.objects.get(i)).dead){
					numKilled++;
				}
			}
			canvas.drawText(numKilled + " SLAUGHTERED",
					25, getHeight()/2, paint);
			return;
		}
		
		if(GameThread.startScreen){
			return;
		}
		
		canvas.save();
		
		//Translate canvas to fit current world.
		canvas.translate(0, 0);
		
		GameThread.player.draw(canvas);
		
		for(int i = 0; i < GameThread.objects.size(); i++){
			GameThread.objects.get(i).draw(canvas);
		}
		
		if(GameThread.endOfStage == true || GameThread.startOfStage == true){
			paint.setARGB((int) (255*GameThread.fadeValue), 0, 0, 0);
			canvas.drawRect(new Rect(0, 0, getWidth(), getHeight()), paint);
		}
		
		//Reset canvas offset.
		canvas.restore();
		//canvas.drawText("Time: " + (System.currentTimeMillis() - GameThread.startTime), 1, 20, paint);
		//canvas.drawText("x: " + GameThread.player.x + " y: " + GameThread.player.y, 1, 40, paint);
	}
	
}
