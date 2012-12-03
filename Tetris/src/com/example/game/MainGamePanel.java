package com.example.game;

import java.util.Random;
import java.util.Vector;

import com.example.game.model.Block;
import com.example.game.model.Board;
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
import android.graphics.Paint;
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
	Board board;
	Block block;
	

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
		board = new Board();
		block = new Block(generator.nextInt(10), 0, Color.RED);
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
		/*if (/*event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
			objects.get(count).handleActionMove((int) event.getX(), getWidth());
		}
		if (event.getAction() == MotionEvent.ACTION_UP) {
			objects.get(count).handleActionUP();
		}*/
		int x1 = 0, y1, x2 = 0, y2, x = 0, y;
		switch(event.getAction()){
		case MotionEvent.ACTION_MOVE:
			x = (int) event.getX();
			objects.get(count).handleActionMove(x, getWidth());
		case MotionEvent.ACTION_UP:
			x2 = (int) event.getX();
			y2 = (int) event.getY();
			int X = this.getWidth();
			int Y = this.getHeight();
			//objects.get(count).handleActionUP();
			block.touchAction(x2, y2, X, Y);
			break;
		case MotionEvent.ACTION_DOWN:
			x1 = (int) event.getX();
			
		}		
		
		return true;
	}

	protected void render(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		//spaceship.draw(canvas);
		//for(Spaceship obj : objects)
		//	obj.draw(canvas);
		//objects.get(count).draw(canvas);
		block.draw(canvas);
		board.draw(canvas);
		//Paint paint = new Paint();
		//paint.setColor(Color.WHITE);
		//canvas.drawRect(0, 0, 100, 100, paint);
	}

	public void update() {
		//spaceship.borders(getWidth(), getHeight());
		//spaceship.update();
		//objects.get(count).borders(getWidth(), getHeight());
		//objects.get(count).update();
		//updateObjects();
		//if(objects.get(count).getPosition().y == getHeight()-50){
			//destroyObjects();
		//	createObjects();
		//}
		//spaceship.update(System.currentTimeMillis(), getWidth());
		//updateObjects();
		//checkCollisions();
		//handleColisions();
		//destroyObjects();
		//createObjects();
		block.update();
		board.update();
		detectCollision();
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
	
	private void detectCollision()
	{
		if(block.coord[1] >= 20)
		{
			board.board[block.coord[0]][20-1] = block;
			block = new Block(generator.nextInt(10), 0, Color.RED);
		}
		else if(board.board[block.coord[0]][block.coord[1]] != null)
		{
			board.board[block.coord[0]][block.coord[1]-1] = block;
			block = new Block(generator.nextInt(10), 0, Color.RED);
		}
	}	
}
