package com.example.game;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class Levels extends Activity implements OnClickListener, OnCheckedChangeListener{

	RadioGroup rg;
	RadioButton easy, medium, hard;
	Button b;
	SharedPreferences.Editor editor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.levels);
		rg = (RadioGroup)findViewById(R.id.rgAnswers);
		easy = (RadioButton)findViewById(R.id.rEasy);
		medium = (RadioButton)findViewById(R.id.rMedium);
		hard = (RadioButton)findViewById(R.id.rHard);
		medium.setChecked(true);
		b = (Button)findViewById(R.id.back);
		rg.setOnCheckedChangeListener(this);
		b.setOnClickListener(this);
		
		
		SharedPreferences settings = getSharedPreferences(Menu.PREFS_NAME, 0);
	    editor = settings.edit();
	    

		switch(settings.getInt("level", 2)){
		case 1:
			easy.setChecked(true);
			break;
		case 2:
			medium.setChecked(true);
			break;
		case 3:
			hard.setChecked(true);
			break;
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		switch(checkedId){
		case R.id.rEasy:
			editor.putInt("level", 1);
			editor.commit();
			break;
		case R.id.rMedium:
			editor.putInt("level", 2);
			editor.commit();
			break;
		case R.id.rHard:
			editor.putInt("level", 3);
			editor.commit();
			break;
		}
	}

	@Override
	public void onClick(View arg0) {
		finish();
	}

	
}
