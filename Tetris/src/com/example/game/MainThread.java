package com.example.game;

import com.example.game.model.*;

import android.content.Intent;
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
	public long t = 0;

	public MainThread(SurfaceHolder surfaceHolder, MainGamePanel gamePanel) {
		super();
		this.surfaceHolder = surfaceHolder;
		this.gamePanel = gamePanel;
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
					
					//ustawanie czasu
					time_now = System.currentTimeMillis();
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
						boolean kontynuuj = gamePanel.update();
						if(kontynuuj == false)
						{
							if(gamePanel.isGameOver)//koniec gry, bo sie plansza zapchala
							{
								gamePanel.score = 0;
								speed = 500;
								gamePanel.level = 1;
								this.interrupt();
								setRunning(false);
								return;
							}
							else//nastepny poziom
							{
								speed -= speed*0.2; //przyspieszenie o 20%
							}
							gamePanel.resetGame();
						}
					}

					gamePanel.render(canvas);
					
				}
			} finally {
				if (canvas != null) {
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
		}
	}

	public void setRunning(boolean r) {
		running = r;
	}
}