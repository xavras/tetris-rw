package com.example.game;

import com.example.game.model.*;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.os.*;

public class MainThread extends Thread {

	private SurfaceHolder surfaceHolder;
	private MainGamePanel gamePanel;
	private boolean running;
	private Block test;
	private long time_now, time_last;
	private long speed = 500;//co ile odswieza [ms]

	public MainThread(SurfaceHolder surfaceHolder, MainGamePanel gamePanel) {
		super();
		this.surfaceHolder = surfaceHolder;
		this.gamePanel = gamePanel;
		//test = new Block(0, 0);
		time_now = time_last = System.currentTimeMillis();
	}

	@Override
	public void run() {
		Canvas canvas;
		while (running) {
			canvas = null;
			try {
				canvas = this.surfaceHolder.lockCanvas();
				synchronized (surfaceHolder) {
					//gamePanel.update();
					//gamePanel.render(canvas);
					
					//ustawanie czasu
					time_now = System.currentTimeMillis();
					long t = 50;
					if(time_now < time_last)//przeskok MAX_VALUE
					{
						t = (Long.MAX_VALUE - time_last) + time_now;
					}
					else
					{
						t = time_now - time_last;
					}
					
					if(t > speed)
					{
						time_last = time_now;
						
						//TUTAJ BEDA UPDATE'Y, ZALEZNE OD CZASU
						gamePanel.update();
					}

					gamePanel.render(canvas);
					//Paint paint = new Paint();
					//paint.setColor(Color.GREEN);
					//canvas.drawRect(0, 0, 100, 100, paint);
					
				}
			} finally {
				if (canvas != null) {
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
		}
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
}