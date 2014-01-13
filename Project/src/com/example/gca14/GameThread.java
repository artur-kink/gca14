package com.example.gca14;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread{
	
	private GameSurface gameSurface;
	private SurfaceHolder surfaceHolder;
	
	public GameThread(SurfaceHolder holder, GameSurface surface){
		surfaceHolder = holder;
		gameSurface = surface;
	}
	
	// flag to hold game state
	private boolean running;
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	@Override
	public void run() {
		Canvas gameCanvas = null;
		running = true;
		while (running) {
			gameCanvas = surfaceHolder.lockCanvas();
			gameSurface.draw(gameCanvas);
			surfaceHolder.unlockCanvasAndPost(gameCanvas);
		}
	}
}
