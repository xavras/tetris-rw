package com.example.game.model;

import java.util.Random;

import android.graphics.Bitmap;

public class Obstacle extends GameObject {

	private static Random generator = new Random();

	public Obstacle(Bitmap bitmap, Wector wector) {
		super(bitmap, wector);
		speed.y = generator.nextInt(5) + 3;
	}
}
