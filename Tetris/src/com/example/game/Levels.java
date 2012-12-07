package com.example.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class Levels extends Activity implements OnClickListener, OnCheckedChangeListener{

	RadioGroup rg;
	Button b;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.levels);
		rg = (RadioGroup)findViewById(R.id.rgAnswers);
		b = (Button)findViewById(R.id.back);
		rg.setOnCheckedChangeListener(this);
		b.setOnClickListener(this);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		switch(checkedId){
		case R.id.rEasy:
			
			break;
		case R.id.rMedium:
			
			break;
			
		case R.id.rHard:
			
			break;
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		finish();
	}

	
}
