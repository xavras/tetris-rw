package com.example.game;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

	MediaPlayer mp;
	MainGamePanel gamePanel;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(gamePanel = new MainGamePanel(this));
		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		mp = MediaPlayer.create(this, R.raw.background);
		mp.start();
		mp.setLooping(true);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		mp.stop();
		gamePanel.thread.setRunning(false);
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mp.stop();
		gamePanel.thread.setRunning(false);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		gamePanel.thread.setRunning(true);
	}
	
	
}
