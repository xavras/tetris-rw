package com.example.game.model;

import com.example.game.MainGamePanel;

import android.graphics.*;

public class Block{
	
	public int[] coord;
	public int color;
	public static Bitmap bitmap;
	//public float speed = 1.0f; //speed up to down
	
	public static final int BLUE = 0;
	public static final int GREEN = 1;
	public static final int RED= 2;
	public static final int CYAN = 3;
	public static final int MAGENTA = 4;
	public static final int YELLOW = 5;
	public static final int WHITE = 6;
	
	public Block()
	{
		coord = new int[]{0, 0};//identity matrix
		color = Color.GREEN;
	}
	
	public Block(int x, int y, int color)
	{
		coord = new int[]{x, y};
		switch(color)
		{
			case BLUE:
				this.color = Color.BLUE;
				break;
			case GREEN:
				this.color = Color.GREEN;
				break;
			case RED:
				this.color = Color.RED;
				break;
			case CYAN:
				this.color = Color.CYAN;
				break;
			case MAGENTA:
				this.color = Color.MAGENTA;
				break;
			case YELLOW:
				this.color = Color.YELLOW;
				break;
			case WHITE:
				this.color = Color.WHITE;
				break;
			default:
				this.color = Color.WHITE;	
		}
	}
	
	public void draw(Canvas canvas, RectF area)
	{
		Paint paint = new Paint();
		float Width = area.width();
		float Height = area.height();
		float wspX = Width/MainGamePanel.boardWidth;
		float wspY = Height/MainGamePanel.boardHeight;
		paint.setColor(color);
		canvas.drawRect(
				area.left+coord[0]*wspX, 
				area.top+coord[1]*wspY, 
				area.left+(coord[0]+1)*wspX, 
				area.top+(coord[1]+1)*wspY,paint);
		paint.setAlpha(128);
		Rect src = new Rect(0,0,bitmap.getWidth(), bitmap.getHeight());
		RectF dst = new RectF(
				area.left+coord[0]*wspX, 
				area.top+coord[1]*wspY, 
				area.left+(coord[0]+1)*wspX, 
				area.top+(coord[1]+1)*wspY);
		canvas.drawBitmap(bitmap, src, dst, paint);
		//canvas.drawRect(0,0,100,100,paint);
	}
	
	public void update()//bo se spada
	{
		coord[1] = coord[1]+1;
	}
	
	public void touchAction(int x, int y, int screenWidth, int screenHeight)
	{
		float wspX = screenWidth/MainGamePanel.boardWidth;
		float wspY = screenHeight/MainGamePanel.boardHeight;
		
		int xm = (int)(x/wspX);
		if((xm < coord[0]) && (coord[0] !=0))
			coord[0]--;
		else if((xm > coord[0]) && (coord[0] != MainGamePanel.boardHeight-1))
			coord[0]++;
	}
	
	public Block clone()
	{
		Block ret = new Block(coord[0], coord[1], color);
		ret.color = color;
		
		return ret;
	}
	
	public void move(int x, int y)
	{
		coord[0]+=x;
		coord[1]+=y;
	}
}
