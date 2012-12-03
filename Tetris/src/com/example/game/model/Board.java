package com.example.game.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Board {
	
	public Block[][] board = new Block[10][20];
	
	public Board()
	{
		for(int i=0; i<10; i++)
			for(int j=0; j<20; j++)
				board[i][j] = null;
	}
	
	public void draw(Canvas canvas)
	{
		Paint paint = new Paint();
		float wspX = canvas.getWidth()/10;
		float wspY = canvas.getHeight()/20;
		
		for(int i=0; i<10; i++)
			for(int j=0; j<20; j++)
			{
				if(board[i][j] != null)
				{
					paint.setColor(board[i][j].color);
					canvas.drawRect(i*wspX, j*wspY, (i+1)*wspX, (j+1)*wspY,paint);
				}
			}
	}
	
	public void update()//TODO wykrywanie calego rzedu
	{
	}

}
