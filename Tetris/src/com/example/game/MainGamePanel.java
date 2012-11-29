package com.example.game;

import java.util.Random;
import java.util.Vector;

import com.example.game.model.Gold;
import com.example.game.model.ColisionDetector;
import com.example.game.model.GameObject;
import com.example.game.model.Obstacle;
import com.example.game.model.Spaceship;
import com.example.game.model.Wector;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.*;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.game.R;

public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {

	private static Random generator = new Random();
	private MainThread thread;
	private Spaceship spaceship;
	private Vector<Spaceship> objects = new Vector<Spaceship>();
	private int count = -1;
	

	public MainGamePanel(Context context) {
		super(context);
		getHolder().addCallback(this);
		setFocusable(true);
		thread = new MainThread(getHolder(), this);
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO Auto-generated method stub
	}

	public void surfaceCreated(SurfaceHolder holder) {
		//spaceship = new Spaceship(BitmapFactory.decodeResource(getResources(), R.drawable.food), new Wector((getWidth() / 2) - 20, 50));
		createObjects();
		thread.setRunning(true);
		thread.start();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
			}
		}
	}

	private void createObjects() {
		objects.add(new Spaceship(BitmapFactory.decodeResource(getResources(), R.drawable.food), new Wector((getWidth()/2)-20, 50)));	
		count++;
		//object = new Spaceship(BitmapFactory.decodeResource(getResources(), R.drawable.food), new Wector((getWidth()/2)-20, 50));
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
			objects.get(count).handleActionMove((int) event.getX(), getWidth());
		}
		if (event.getAction() == MotionEvent.ACTION_UP) {
			objects.get(count).handleActionUP();
		}
		return true;
	}

	protected void render(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		//spaceship.draw(canvas);
		for(Spaceship obj : objects)
			obj.draw(canvas);
		//objects.get(count).draw(canvas);
	}

	public void update() {
		//spaceship.borders(getWidth(), getHeight());
		//spaceship.update();
		objects.get(count).borders(getWidth(), getHeight());
		//objects.get(count).update();
		updateObjects();
		if(objects.get(count).getPosition().y == getHeight()-50){
			//destroyObjects();
			createObjects();
		}
		//spaceship.update(System.currentTimeMillis(), getWidth());
		//updateObjects();
		//checkCollisions();
		//handleColisions();
		//destroyObjects();
		//createObjects();
	}

	/*public void checkCollisions() {
		for (Spaceship obstacle : objects) {
			if (ColisionDetector.isCollision(objects.get(count).getBoundingRect(), obstacle.getBoundingRect())) {
				obstacle.setIsTouched(true);
			}
		}
	}*/

	/*public void handleColisions() {
		for (int i = 0; i < objects.size();) {
			Spaceship obstacle = objects.get(i);
			if (obstacle.getIsTouched()) {
				if (obstacle instanceof Gold) {
					// TODO
				} else if (obstacle instanceof Obstacle) {
					// TODO
				}
				objects.remove(i);
			} else {
				i++;
			}
		}
	}*/

	public void destroyObjects() {
		for (int i = 0; i < objects.size();) {
			if (objects.elementAt(i).getPosition().y >= getHeight() - 30) {
				objects.remove(i);
			} else {
				i++;
			}
		}
	}

	public void updateObjects() {
		for (Spaceship obstacle : objects) {
			obstacle.update();
		}
	}
}
