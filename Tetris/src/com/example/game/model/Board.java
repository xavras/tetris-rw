package com.example.game.model;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;

public class Board {
	public int Width = 10;
	public int Height = 20;
	
	public Block[][] board;
	
	public Board(int width, int height)
	{
		Width = width;
		Height = height;
		board = new Block[Width][Height];
		
		for(int i=0; i<width; i++)
			for(int j=0; j<height; j++)
				board[i][j] = null;
	}
	
	public void draw(Canvas canvas, RectF area)
	{
		for(int i=0; i<Width; i++)
			for(int j=0; j<Height; j++)
			{
				if(board[i][j] != null)
				{
					board[i][j].draw(canvas, area);
				}
			}
	}
	
	public void update()//TODO wykrywanie calego rzedu
	{
	}
	
	public boolean addTetrion(Tetrion tet)
	{
		for(int i=0; i<4; i++)
			for(int j=0; j<4; j++)
				if(tet.tet[i][j] != null)
				{
					int x = tet.tet[i][j].coord[0];
					int y = tet.tet[i][j].coord[1]-1;
					if(y < 0) return false;
					board[x][y] = tet.tet[i][j];
					board[x][y].move(0, -1);
				}
		return true;
	}
	
	public int checkFullLine()
	{
		boolean full = false;
		for(int j=0; j<Height; j++)
		{
			full = true;
			for(int i=0; i<Width; i++)
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
	
	public ArrayList<Point> checkFullLines()
	{
		ArrayList<Point> ret = new ArrayList<Point>();
		boolean full = false;
		for(int j=0; j<Height; j++)
		{
			full = true;
			for(int i=0; i<Width; i++)
			{
				if(board[i][j] == null) full = false;
			}
			if(full == true)
			{
				ret.add(new Point(j, 0));
			}
		}
		
		return ret;
	}
	
	public void clearLine(int num)
	{
		for(int j=num; j>0; j--)
		{
			for(int i=0; i<Width; i++)
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
