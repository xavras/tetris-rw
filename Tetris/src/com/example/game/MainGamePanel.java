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
import com.example.game.model.Tetrion;
import com.example.game.model.Wector;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.*;
import android.view.KeyEvent;
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
	Tetrion tet;
	
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
		tet = new Tetrion(generator.nextInt(7), Color.RED);
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
			touchAction(x2, y2, X, Y);
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
		tet.draw(canvas);
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
		tet.update();
		board.update();
		if(isCollisionWithGround(tet) || isCollisionWithBoard(tet))
		{
			board.addTetrion(tet);
			createTetrion();
		}
		
		//czyszczenie linii
		for(;;)
		{
			int num = board.checkFullLine();
			if(num != -1)
			{
				board.clearLine(num);
			}
			else break;
		}
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
	
	/*private void detectCollision()
	{
		for(int i=0; i<10; i++)
			for(int j=0; j<20; j++)
				if(board.board[i][j] != null)
				{
					if(tet.checkCollision(i, j))
					{
						board.addTetrion(tet);
						createTetrion();
						return;
					}
				}
		
		if(tet.checkGround())
		{
			board.addTetrion(tet);
			createTetrion();
			return;
		}
	}*/
	
	private void createTetrion()
	{
		tet = new Tetrion(generator.nextInt(7), Color.BLUE);
	}
	
	public void touchAction(int x, int y, int screenWidth, int screenHeight)
	{
		float wspX = screenWidth/10;
		float wspY = screenHeight/20;
		
		int xm = (int)(x/wspX);//pole gdzie zostalo klikniete
		int ym = (int)(y/wspY);
		
		if((tet.isCoordSet(xm, ym)) )/*|| ((xm >= tet.coord[0])&&(xm <= tet.coord[0]+2)&&(ym >= tet.coord[1])&&(ym <= tet.coord[1]+2))*/
		{//zostala kliknieta figura (do obrotu)
			Tetrion tmp = cloneTetrion();
			tmp.rotate();//obrot
			if(!isCollisionWithBorders(tmp) && !isCollisionWithBoard(tmp) && !isCollisionWithGround(tmp))
				tet = tmp;	
		}
		else if(xm < tet.coord[0]+1)//klikniecie po lewej stronie
		{
			Tetrion tmp = cloneTetrion();
			tmp.move(-1, 0);//przesuwamy w lewo
			if(!isCollisionWithBorders(tmp) && !isCollisionWithBoard(tmp))
				tet = tmp;
		}
		else if(xm > tet.coord[0]+1)//klikniecie po prawej stronie
		{
			Tetrion tmp = cloneTetrion();
			tmp.move(+1, 0);
			if(!isCollisionWithBorders(tmp) && !isCollisionWithBoard(tmp))
				tet = tmp;
		}
	}
	
	private boolean isTetrionMovePossible(int x, int y)
	{
		for(int i=0; i<10; i++)
			for(int j=0; j<20; j++)
				if(board.board[i][j] != null)
				{
					if(tet.checkCollision(i-x, j-y) == true) return false;
				}
		return true;
	}
	
	private Tetrion cloneTetrion()
	{
		Tetrion tr = new Tetrion();
		
		tr.coord[0] = tet.coord[0];
		tr.coord[1] = tet.coord[1];
		
		tr.type = tet.type;
		
		for(int i=0; i<4; i++)
			for(int j=0; j<4; j++)
				if(tet.tet[i][j] != null)
				{
					tr.tet[i][j] = tet.tet[i][j].clone();
				}
		
		return tr;
	}
	
	private boolean isCollisionWithBoard(Tetrion tetr)
	{
		for(int i=0; i<10; i++)
			for(int j=0; j<20; j++)
				if(board.board[i][j] != null)
				{
					if(tetr.checkCollision(i, j))
					{
						return true;
					}
				}
		return false;
	}
	
	private boolean isCollisionWithGround(Tetrion tetr)
	{
		return tetr.isCollisionWithGround();
	}
	
	private boolean isCollisionWithBorders(Tetrion tetr)
	{
		return tetr.isCollisionWithBorders();
	}
}
