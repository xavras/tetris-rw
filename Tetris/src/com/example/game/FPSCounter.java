package com.example.game;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class FPSCounter {
	private long time_now;
	private long time_last;
	private int counter;
	private int fps;

	public FPSCounter()
	{
		time_now = time_last = System.currentTimeMillis();
		counter = 0;
		fps = 0;
	}
	
	public void update(Canvas canvas)
	{
		time_now = System.currentTimeMillis();
		long t = 0;
		counter++;
		if(time_now < time_last)//przeskok MAX_VALUE
		{
			t = (Long.MAX_VALUE - time_last) + time_now;
		}
		else
		{
			t = time_now - time_last;
		}
		
		if(t > 1000)//narysuj po 1s fps
		{
			fps = counter;
			time_last = time_now;
			counter = 0;
		}
		

		Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setAntiAlias(true);
		
		canvas.drawText(""+fps, 5, 15, paint);
	}
}
