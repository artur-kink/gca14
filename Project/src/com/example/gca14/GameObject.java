package com.example.gca14;

/**
 * Abstract base class for all game objects.
 */
public abstract class GameObject {
	/** X Position. */
	public int x;
	/** Y Position. */
	public int y;

	/** All game objects must be updateable. */
	public abstract void update();
}
