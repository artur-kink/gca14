package com.example.gca14;

import android.graphics.Canvas;

/**
 * Abstract base class for all game objects.
 */
public abstract class GameObject {
	
	/** Destroy flag. If true object should be destroyed. */
	public boolean destroy;
	
	/** X Position. */
	public int x;
	/** Y Position. */
	public int y;

	/** All game objects must be updateable. */
	public abstract void update();
	
	/** Game objects can be drawn on canvas. */
	public abstract void draw(Canvas canvas);
}
