package com.example.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class Menu extends Activity {
	
	public static final String PREFS_NAME = "MyPrefsFile";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		int medium = settings.getInt("mediumLevel", 2);
		
		// pierwszy zapis
		File file = getBaseContext().getFileStreamPath("scores.txt");
		if (!file.exists()) {
			FileOutputStream fos = null;
			try {
				fos = openFileOutput("scores.txt", Context.MODE_WORLD_READABLE
						| Context.MODE_WORLD_WRITEABLE);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			String buf = "player1 0\nplayer2 0\nplayer3 0\nplayer4 0\nplayer5 0";
			try {
				osw.write(buf);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				osw.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				osw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mymenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.Start:
			Intent i = new Intent("com.example.game.MAINACTIVITY");
			startActivity(i);
			break;
		case R.id.Levels:
			Intent k = new Intent("com.example.game.LEVELS");
			startActivity(k);
			break;
		case R.id.Scores:
			Intent j = new Intent("com.example.game.SCORES");
			startActivity(j);
			break;
		case R.id.Quit:
			finish();
		default:
			break;
		}
		return true;
	}
}
