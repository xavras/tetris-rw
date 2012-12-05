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
		for(int i=0; i<10; i++)
			for(int j=0; j<20; j++)
			{
				if(board[i][j] != null)
				{
					board[i][j].draw(canvas);
				}
			}
	}
	
	public void update()//TODO wykrywanie calego rzedu
	{
	}
	
	public void addTetrion(Tetrion tet)
	{
		for(int i=0; i<4; i++)
			for(int j=0; j<4; j++)
				if(tet.tet[i][j] != null)
				{
					int x = tet.tet[i][j].coord[0];
					int y = tet.tet[i][j].coord[1]-1;
					board[x][y] = tet.tet[i][j];
					board[x][y].move(0, -1);
				}
	}
	
	public int checkFullLine()
	{
		boolean full = false;
		for(int j=0; j<20; j++)
		{
			full = true;
			for(int i=0; i<10; i++)
			{
				if(board[i][j] == null) full = false;
			}
			if(full == true)
			{
				return j;
			}
		}
		
		return -1;//nothing was finded
	}
	
	public void clearLine(int num)
	{
		for(int j=num; j>0; j--)
		{
			for(int i=0; i<10; i++)
			{
				board[i][j] = board[i][j-1];
				
				if(board[i][j] != null)
					board[i][j].move(0, 1);
			}
		}
		
		for(int i=0; i<1; i++)
		{
			board[i][0] = null;
		}
	}

}
