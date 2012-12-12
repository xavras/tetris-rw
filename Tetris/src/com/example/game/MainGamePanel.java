package com.example.game;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import com.example.game.model.Block;
import com.example.game.model.Board;

import com.example.game.model.Tetrion;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.*;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.game.R;

public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {

	private static Random generator = new Random();
	public MainThread thread;
	Board board;
	Tetrion tet;
	public static RectF mainArea;
	public static int score = 0;
	public static int actualLevelScore = 0;
	boolean isMove = false;
	SoundPool sp, sp1;
	int flip, coins;
	Context mycontext;
	int MAX_SCORE = 50;
	public int level = 1;
	boolean isGameOver = false;
	public static int mode = 0;
	public static int boardWidth = 10;
	public static int boardHeight = 20;
	
	public long clearLineAnimationMaxTime = 500;
	public long clearLineAnimationTime = 0;
	public static boolean clearLineAnimation = false;
	public ArrayList<Point> clearLineAnimationLines;
	public long time_now = 0;
	public long time_last = 0;
	
	public MainGamePanel(Context context) throws IOException {
		super(context);
		getHolder().addCallback(this);
		setFocusable(true);
		thread = new MainThread(getHolder(), this);
		sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
		sp1 = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
		flip = sp.load(context, R.raw.flip, 1);
		coins = sp1.load(context, R.raw.coins, 1);
		
		
		clearLineAnimationLines = new ArrayList<Point>();
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO Auto-generated method stub
	}

	public void surfaceCreated(SurfaceHolder holder) {		
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.klocek);
		
		if(board == null) board = new Board(boardWidth, boardHeight);
		if(tet == null) createTetrion();
		Block.bitmap = bitmap;
		
		score = 0;
		actualLevelScore = 0;

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



	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		int x = (int) event.getX();
		int y = (int) event.getY();
		float y_rotate = getHeight()*0.1f;
		float x_rotate = getWidth()*0.5f;
		
		switch(event.getAction()){
		case MotionEvent.ACTION_MOVE:
			if((y < mainArea.bottom))
			{
				touchActionMove(x, y);
			}
			break;
		
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
		
		Paint paint = new Paint();
		paint.setColor(Color.GRAY);
		
		canvas.drawRect(mainArea, paint);
		tet.draw(canvas, mainArea);
		board.draw(canvas, mainArea);
		
		drawScoreArea(canvas);
	}

	public boolean update() {
		
		boolean ret = true;
		tet.update();
		board.update();
		if(isCollisionWithGround(tet) || isCollisionWithBoard(tet))
		{
			ret = board.addTetrion(tet);
			if(ret == false) {
				isGameOver = true;
				
				if(mode == 0)
				{
					/*** HIGH SCORE *****/
					if(/*score > s.players[4].score*/ score > Scores.players[0].score){
						Intent in = new Intent();
						in.setClass(this.getContext(), AddScore.class);
						this.getContext().startActivity(in);
					}
					else{
						Intent in = new Intent();
						in.setClass(this.getContext(),Scores.class);
						this.getContext().startActivity(in);
					}
					
					thread.setRunning(false);
					/********************/
				}
				else//tryb nieskoñczony
				{
					resetGame();
				}

			}
			createTetrion();
		}
		
		//czyszczenie linii
		/*for(;;)
		{
			int num = board.checkFullLine();
			if(num != -1)
			{
				clearLineAnimation = true;//utworzenie animacji
				clearLineAnimationLines.add(new Point(num,0));
				time_last = System.currentTimeMillis();
				
				Vibrator v = (Vibrator)getContext().getSystemService(Context.VIBRATOR_SERVICE);
				v.vibrate(500);
				sp1.play(coins, 1, 1, 0, 0, 1);
			}
			else break;
		}*/
		clearLineAnimationLines = board.checkFullLines();
		if(clearLineAnimationLines.size() != 0)//wlaczamy animacje
		{
			clearLineAnimation = true;//utworzenie animacji
			time_last = System.currentTimeMillis();
			
			Vibrator v = (Vibrator)getContext().getSystemService(Context.VIBRATOR_SERVICE);
			v.vibrate(500);
			sp1.play(coins, 1, 1, 0, 0, 1);
		}
		
		
		if(actualLevelScore >= MAX_SCORE)
		{
			ret = false;
			level++;
			actualLevelScore = 0;
		}
		
		if(mode == 1)//tryb nieskoñczony
			ret = true;
		
		return ret;
	}
	
	public void drawScoreArea(Canvas canvas)
	{
		float y0 = (canvas.getHeight()-mainArea.bottom)*0.8f+mainArea.bottom;
		float x0 = (canvas.getHeight()-mainArea.bottom)*0.2f;
		
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		canvas.drawRect(0, mainArea.bottom, canvas.getWidth(), canvas.getHeight(), paint);
		
		paint.setColor(Color.WHITE);
		paint.setTextSize((canvas.getHeight() - mainArea.bottom)*0.8f);
		paint.setAntiAlias(true);
		
		canvas.drawText(""+score, x0, y0, paint);
		
	}

	
	
	private void createTetrion()
	{
		tet = new Tetrion(generator.nextInt(7), generator.nextInt(6));
	}
	
	public void touchAction(int x, int y)
	{
		float wspX = mainArea.width()/boardWidth;
		float wspY = mainArea.height()/boardHeight;
		
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
	
	public void touchActionRotate()
	{
		Tetrion tmp = cloneTetrion();
		tmp.rotate();//obrot
		if(!isCollisionWithBorders(tmp) && !isCollisionWithBoard(tmp) && !isCollisionWithGround(tmp))
			tet = tmp;	
	}
	
	public void touchActionMove(int x, int y)
	{
		float wspX = mainArea.width()/boardWidth;
		float wspY = mainArea.height()/boardHeight;
		
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
			Tetrion tet_tmp = cloneTetrion();
			tet_tmp.update();
			if(isCollisionWithGround(tet_tmp) || isCollisionWithBoard(tet_tmp))
			{
				break;
			}
			else
			{
				tet = tet_tmp;
			}
		}
	}
	
	private boolean isTetrionMovePossible(int x, int y)
	{
		for(int i=0; i<boardWidth; i++)
			for(int j=0; j<boardHeight; j++)
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
		for(int i=0; i<boardWidth; i++)
			for(int j=0; j<boardHeight; j++)
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
	
	public void resetGame()
	{
		board = new Board(boardWidth, boardHeight);
		createTetrion();
		isGameOver = false;
		actualLevelScore = 0;
	}
	
	public void generateBoardOffset(int size)
	{
		for(int i=0; i<size; i++)
		{
			for(int j=0; j<boardWidth; j++)
			{
				board.board[j][boardHeight-i-1]= new Block(j, boardHeight-i-1, Block.WHITE);
			}
		}
		
		Random random = new Random();
		for(int i=0; i<size; i++)
		{
			for(int j=0; j<5; j++)
			{
				board.board[random.nextInt(boardWidth)][boardHeight-i-1] = null;
			}
		}
	}
	
	public void clearLineAnimation(Canvas canvas)
	{
		time_now = System.currentTimeMillis();
		if(time_now < time_last)//przeskok MAX_VALUE
		{
			clearLineAnimationTime = (Long.MAX_VALUE - time_last) + time_now;
		}
		else
		{
			clearLineAnimationTime = time_now - time_last;
		}
		
		if(clearLineAnimationTime >= clearLineAnimationMaxTime)//koniec animacji
		{
			
			for(int i=0; i<clearLineAnimationLines.size(); i++)
			{
				board.clearLine(clearLineAnimationLines.get(i).x);
				score+=10;
				actualLevelScore+=10;
			}
			clearLineAnimation = false;
			clearLineAnimationTime = 0;
			clearLineAnimationLines.clear();
		}
		else//RYSOWANIE animacji
		{
			float wspX = mainArea.width()/boardWidth;
			float wspY = mainArea.height()/boardHeight;
			
			Paint paint = new Paint();
			paint.setColor(Color.WHITE);
			paint.setAlpha((int)((float)clearLineAnimationTime/(float)clearLineAnimationMaxTime*255.0f));
			
			for(int i=0; i<clearLineAnimationLines.size(); i++)
			{
				canvas.drawRect(
						0, 
						clearLineAnimationLines.get(i).x*wspY, 
						mainArea.width(), 
						(clearLineAnimationLines.get(i).x+1)*wspY, 
						paint);
			}
		}
	}
}
