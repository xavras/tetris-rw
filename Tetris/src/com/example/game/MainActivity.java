package com.example.game;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

	MediaPlayer mp;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(new MainGamePanel(this));
		mp = MediaPlayer.create(this, R.raw.background);
		mp.start();
		mp.setLooping(true);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		MainThread.setRunning(false);
		super.onBackPressed();
		mp.stop();
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		MainThread.setRunning(false);
		super.onPause();
		mp.stop();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MainThread.setRunning(true);
	}
	
	
}
