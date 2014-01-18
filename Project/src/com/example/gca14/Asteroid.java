package com.example.gca14;

/**
 * Asteroid.
 */
public class Asteroid extends GameObject {
	
	private int xVelocity;
	private int yVelocity;
	
	public Asteroid(int tx, int ty){
		x = tx;
		y = ty;
		xVelocity = 0;
		yVelocity = 1;
	}
	
	public void update(){
		x += xVelocity;
		y += yVelocity;
	}
}
