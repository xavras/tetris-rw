package com.example.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class Menu extends Activity{

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
	}
	
	@Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
	
	@Override
	public void onPause(){
		super.onPause();
		finish();
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
	    case R.id.Quit:
	        	finish();
	    default:
	        break;
	    }
	    return true;
	}
}
