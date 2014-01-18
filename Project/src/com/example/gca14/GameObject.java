package com.example.gca14;

import android.graphics.Canvas;

/**
 * Abstract base class for all game objects.
 */
public abstract class GameObject {
	
	/** Destroy flag. If true object should be destroyed. */
	public boolean destroy;
	
	/** X Position. */
	public float x;
	/** Y Position. */
	public float y;

	/** Object width. */
	public float width;
	
	/** Object height. */
	public float height;
	
	/** All game objects must be updateable. */
	public abstract void update();
	
	/** Game objects can be drawn on canvas. */
	public abstract void draw(Canvas canvas);
}
