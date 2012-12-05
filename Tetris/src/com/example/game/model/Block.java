package com.example.game.model;

import android.graphics.*;

public class Block{
	
	public int[] coord;
	public int color;
	//public float speed = 1.0f; //speed up to down
	
	public Block()
	{
		coord = new int[]{0, 0};//identity matrix
		color = Color.GREEN;
	}
	
	public Block(int x, int y, int color)
	{
		coord = new int[]{x, y};
		this.color = color;
	}
	
	public void draw(Canvas canvas)
	{
		Paint paint = new Paint();
		float wspX = canvas.getWidth()/10;
		float wspY = canvas.getHeight()/20;
		paint.setColor(color);
		canvas.drawRect(coord[0]*wspX, coord[1]*wspY, (coord[0]+1)*wspX, (coord[1]+1)*wspY,paint);
		//canvas.drawRect(0,0,100,100,paint);
	}
	
	public void update()//bo se spada
	{
		coord[1] = coord[1]+1;
	}
	
	public void touchAction(int x, int y, int screenWidth, int screenHeight)
	{
		float wspX = screenWidth/10;
		float wspY = screenHeight/20;
		
		int xm = (int)(x/wspX);
		if((xm < coord[0]) && (coord[0] !=0))
			coord[0]--;
		else if((xm > coord[0]) && (coord[0] !=19))
			coord[0]++;
	}
	
	public Block clone()
	{
		return new Block(coord[0], coord[1], color);
	}
	
	public void move(int x, int y)
	{
		coord[0]+=x;
		coord[1]+=y;
	}
}
