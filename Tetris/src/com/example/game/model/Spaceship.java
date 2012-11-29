package com.example.game.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Spaceship {
	protected Wector position;
	protected Wector speed = new Wector(0, 20);
	protected int tmp;
	protected Bitmap bitmap;

	public Spaceship(Bitmap bitmap, Wector position) {
		this.bitmap = bitmap;
		this.position = position;
	}
	
	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap, position.x, position.y, null);
	}

	public void handleActionMove(int eventX, int canvasWidth) {
		if (eventX < 50) {
			speed = new Wector(-10, 20);
		} else if (eventX > canvasWidth - 50) {
			speed = new Wector(10, 20);
		}
	}

	public void handleActionUP() {
		speed = new Wector(0, 20);
	}

	public void borders(int canvasWidth, int canvasHeight) {
		tmp = canvasHeight - 50;
		if (position.x > canvasWidth - bitmap.getWidth()) {
			position.x = canvasWidth - bitmap.getWidth();
		}
		if (position.x < 0) {
			position.x = 0;
		}
		if(position.y > canvasHeight - 50){
			position.y = canvasHeight - 50;
			speed = new Wector(0,0);
		}
	}

	public void update() {
		if(position.y != tmp)
			position = position.add(speed);
	}

	public Wector getPosition() {
		return position;
	}
}
