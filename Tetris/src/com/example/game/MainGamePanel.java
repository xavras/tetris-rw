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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
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
	public static RectF mainArea;
	public int score = 0;
	boolean isMove = false;
	SoundPool sp, sp1;
	int flip, coins;
	Context mycontext;
	
	public MainGamePanel(Context context) {
		super(context);
		getHolder().addCallback(this);
		setFocusable(true);
		thread = new MainThread(getHolder(), this);
		sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
		sp1 = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
		flip = sp.load(context, R.raw.flip, 1);
		coins = sp1.load(context, R.raw.coins, 1);
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO Auto-generated method stub
	}

	public void surfaceCreated(SurfaceHolder holder) {
		//spaceship = new Spaceship(BitmapFactory.decodeResource(getResources(), R.drawable.food), new Wector((getWidth() / 2) - 20, 50));
		createObjects();
		
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.klocek);
		
		board = new Board();
		createTetrion();
		Block.bitmap = bitmap;
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
		int x = (int) event.getX();
		int y = (int) event.getY();
		float y_rotate = getHeight()*0.1f;
		float x_rotate = getWidth()*0.5f;
		
		switch(event.getAction()){
		case MotionEvent.ACTION_MOVE:
			if((y < mainArea.bottom))
			{
				touchActionMove(x, y);
				//isMove = true;
			}
			break;
		/*case MotionEvent.ACTION_UP:
			if((y < mainArea.bottom) && (!isMove))
			{
				touchActionRotate();
			}
			isMove = false;
			break;*/
		case MotionEvent.ACTION_DOWN:
			if(y >= mainArea.bottom)
			{
				if(x <= x_rotate) touchActionSpeedUp();
			
				else if(x > x_rotate)
				{
					touchActionRotate();
					sp.play(flip, 1, 1, 0, 0, 1);
				}
			}
			break;
		}		
		
		return true;
	}

	protected void render(Canvas canvas) {
		
		if(mainArea == null)
		{
			mainArea = new RectF(
					canvas.getWidth()*0.0f,
					canvas.getHeight()*0.0f,
					canvas.getWidth()*1.0f, 
					canvas.getHeight()*0.9f);
		}
		
		//canvas.clipRect(mainArea);
		//canvas.drawColor(Color.GRAY);
		Paint paint = new Paint();
		paint.setColor(Color.GRAY);
		
		canvas.drawRect(mainArea, paint);
		//spaceship.draw(canvas);
		//for(Spaceship obj : objects)
		//	obj.draw(canvas);
		//objects.get(count).draw(canvas);
		tet.draw(canvas, mainArea);
		board.draw(canvas, mainArea);
		
		drawScoreArea(canvas);
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
				sp1.play(coins, 1, 1, 0, 0, 1);
				score+=10;
			}
			else break;
		}
	}
	
	public void drawScoreArea(Canvas canvas)
	{
		float y0 = (canvas.getHeight()-mainArea.bottom)*0.8f+mainArea.bottom;
		float x0 = (canvas.getHeight()-mainArea.bottom)*0.2f;
		
		//canvas.clipRect(0, mainArea.bottom, canvas.getWidth(), canvas.getHeight());
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		canvas.drawRect(0, mainArea.bottom, canvas.getWidth(), canvas.getHeight(), paint);
		
		paint.setColor(Color.WHITE);
		paint.setTextSize((canvas.getHeight() - mainArea.bottom)*0.8f);
		paint.setAntiAlias(true);
		
		canvas.drawText(""+score, x0, y0, paint);
		
		//canvas.clipRect(0, 0, canvas.getWidth(), canvas.getHeight());
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
		tet = new Tetrion(generator.nextInt(7), generator.nextInt(6));
	}
	
	public void touchAction(int x, int y)
	{
		float wspX = mainArea.width()/10;
		float wspY = mainArea.height()/20;
		
		int xm = (int)((x-mainArea.left)/wspX);//pole gdzie zostalo klikniete
		int ym = (int)((y-mainArea.top)/wspY);
		
		/*if((tet.isCoordSet(xm, ym)) )//|| ((xm >= tet.coord[0])&&(xm <= tet.coord[0]+2)&&(ym >= tet.coord[1])&&(ym <= tet.coord[1]+2))
		{//zostala kliknieta figura (do obrotu)
			Tetrion tmp = cloneTetrion();
			tmp.rotate();//obrot
			if(!isCollisionWithBorders(tmp) && !isCollisionWithBoard(tmp) && !isCollisionWithGround(tmp))
				tet = tmp;	
		}
		else */
		if(xm < tet.coord[0]+1)//klikniecie po lewej stronie
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
	
	public void touchActionRotate()
	{
		Tetrion tmp = cloneTetrion();
		tmp.rotate();//obrot
		if(!isCollisionWithBorders(tmp) && !isCollisionWithBoard(tmp) && !isCollisionWithGround(tmp))
			tet = tmp;	
	}
	
	public void touchActionMove(int x, int y)
	{
		float wspX = mainArea.width()/10;
		float wspY = mainArea.height()/20;
		
		int xm = (int)((x-mainArea.left)/wspX);//pole gdzie zostalo klikniete
		int ym = (int)((y-mainArea.top)/wspY);
		
		if(xm < tet.coord[0]+1)//klikniecie po lewej stronie
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
	
	public void touchActionSpeedUp()
	{
		for(int i=0; i<3; i++)
		{
			tet.update();
			if(isCollisionWithGround(tet) || isCollisionWithBoard(tet))
			{
				board.addTetrion(tet);
				createTetrion();
				break;
			}
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
