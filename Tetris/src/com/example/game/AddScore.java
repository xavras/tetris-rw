package com.example.game;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddScore extends Activity{

	EditText et;
	Button b1, b2;
	String FILENAME = "scores.txt";
	Scores s;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addscore);
		et = (EditText)findViewById(R.id.editText1);
		b1 = (Button)findViewById(R.id.button1);
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					writeScore();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		b2 = (Button)findViewById(R.id.button2);
		b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
	public void writeScore() throws IOException{
		FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_WORLD_READABLE | Context.MODE_WORLD_WRITEABLE);
		OutputStreamWriter osw = new OutputStreamWriter(fos);
		Player inGame = new Player();
		inGame.name = et.getText().toString();
		SharedPreferences scoresSetting = getSharedPreferences(MainGamePanel.PREFS_NAME, 0);
		int data = scoresSetting.getInt("scoredata", 0);
		inGame.score = data;
		Player[] newPlayers = null;
		for(int i = 0; i < 5; i++)
			newPlayers[i] = s.players[i];
		newPlayers[5] = inGame;
		sortPlayers(newPlayers);
		String toWrite = newPlayers[0].name + " " + newPlayers[0].score + "\n" +
						 newPlayers[1].name + " " + newPlayers[1].score + "\n" +
						 newPlayers[2].name + " " + newPlayers[2].score + "\n" +
						 newPlayers[3].name + " " + newPlayers[3].score + "\n" +
						 newPlayers[4].name + " " + newPlayers[4].score + "\n";
		osw.write(toWrite);
		osw.flush();
		osw.close();
	}
	
	public void sortPlayers(Player[] p){
		int n = p.length;
		do{
			for(int i = 0; i < n-1; i++)
				if(p[i].score > p[i+1].score){
					Player tmp = p[i];
					p[i] = p[i+1];
					p[i+1] = tmp;
				}
			n = n-1;
		}while(n > 1);
	}
}
