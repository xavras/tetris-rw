package com.example.game.model;

import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

public class Tetrion {
	public final static int BLOCK_T = 0;
	public final static int BLOCK_J = 1;
	public final static int BLOCK_L = 2;
	public final static int BLOCK_Z = 3;
	public final static int BLOCK_S = 4;
	public final static int BLOCK_O = 5;
	public final static int BLOCK_I = 6;
	
	public Block[][] tet = new Block[4][4];//(1;1) is center
	public int type;
	public int coord[] = {0,-3};
	
	private int[][] gen = {
			{1,0,2,1,1,2},//T
			{0,1,2,1,2,2},//J
			{0,1,2,1,2,0},//L
			{0,1,0,2,1,0},//Z
			{0,0,0,1,1,2},//S
			{1,2,2,1,2,2},//O
			{0,1,2,1,3,1},//I
	};
	
	public Tetrion(int type, int color)
	{
		coord[0] = 5;
		
		tet[1][1] = new Block(1+coord[0], 1+coord[1], color);//ten sie zawsze zapali

		for(int i=0; i<3; i++)
		{
			int xg = gen[type][i*2];
			int yg = gen[type][i*2+1];
			tet[xg][yg] = new Block(xg+coord[0], yg+coord[1], color);
		}
		
		this.type = type;
	}
	
	public Tetrion()
	{
		
	}
	
	public void update()
	{
		move(0, 1);
	}
	
	public void draw(Canvas canvas, RectF area)
	{
		for(int i=0; i<4; i++)
			for(int j=0; j<4; j++)
				if(tet[i][j] != null) tet[i][j].draw(canvas, area);
	}
	
	public boolean checkBorders(int moveX)
	{
		boolean ret = true;
		for(int i=0; i<4; i++)
			for(int j=0; j<4; j++)
			{
				if(tet[i][j] != null)
				{
					int xx = moveX+tet[i][j].coord[0];
					if((xx > 9) || (xx < 0))
					{
						ret = false;
						break;
					}
				}
			}
					
		return ret;
	}
	
	public boolean checkCollision(int objX, int objY)
	{
		for(int i=0; i<4; i++)
			for(int j=0; j<4; j++)
				if(tet[i][j] != null)
				{
					if( (objX == tet[i][j].coord[0]) && (objY == tet[i][j].coord[1]))
						return true;
				}
		return false;
	}
	
	public boolean isCollisionWithGround()
	{
		for(int i=0; i<4; i++)
			for(int j=0; j<4; j++)
				if(tet[i][j] != null)
				{
					if(tet[i][j].coord[1] >= 20)
						return true;
				}
		return false;
	}
	
	public void move(int x, int y)
	{
		for(int i=0; i<4; i++)
			for(int j=0; j<4; j++)
				if(tet[i][j] != null)
				{
					tet[i][j].coord[0]+=x;
					tet[i][j].coord[1]+=y;
				}
		coord[0]+=x;
		coord[1]+=y;
	}
	
	public boolean isCollisionWithBorders()
	{
		for(int i=0; i<4; i++)
			for(int j=0; j<4; j++)
				if(tet[i][j] != null)
				{
					if((tet[i][j].coord[0] < 0) || (tet[i][j].coord[0] >= 10))
						return true;
				}
		return false;
	}
	
	public void rotate()
	{
		if(type != BLOCK_O)//klocka "O" nie obracamy
		{
			//obrot rogow
			Block tmp = tet[0][0];
			
			tet[0][0] = tet[2][0];
			if(tet[0][0] != null) tet[0][0].move(-2, 0);
			
			tet[2][0] = tet[2][2];
			if(tet[2][0] != null) tet[2][0].move(0, -2);
			
			tet[2][2] = tet[0][2];
			if(tet[2][2] != null) tet[2][2].move(2, 0);
			
			tet[0][2] = tmp;//tmp = [0][0]
			if(tet[0][2] != null) tet[0][2].move(0, 2);
			
			//obrot bokow
			tmp = tet[1][0];
			tet[1][0] = tet[2][1];
			if(tet[1][0] != null) tet[1][0].move(-1, -1);
			
			tet[2][1] = tet[1][2];
			if(tet[2][1] != null) tet[2][1].move(1, -1);
			
			tet[1][2] = tet[0][1];
			if(tet[1][2] != null) tet[1][2].move(1, 1);
			
			tet[0][1] = tmp;//tmp = [1][0]
			if(tet[0][1] != null) tet[0][1].move(-1, 1);
		
			//obrot dlugiego klocka
			if(type == BLOCK_I)
			{
				tmp = tet[3][1];
				
				tet[3][1] = tet[1][3];
				if(tet[3][1] != null) tet[3][1].move(2, -2);
				
				tet[1][3] = tmp;//tmp = [3][1]
				if(tet[1][3] != null) tet[1][3].move(-2, 2);
			}
		}
	}
	
	public boolean isCoordSet(int x, int y)
	{
		for(int i=0; i<4; i++)
			for(int j=0; j<4; j++)
				if(tet[i][j] != null)
				{
					if((tet[i][j].coord[0] == x) && (tet[i][j].coord[1] == y))
						return true;
				}
		return false;
	}
}
