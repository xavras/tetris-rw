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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddScore extends Activity{

	EditText et;
	Button b1;
	String FILENAME = "scores.txt";
	Scores s;
	Player inGame;
	Player[] newPlayers = new Player[6];
	
	public AddScore() throws IOException{
		s = new Scores();
		s.readScores();
		inGame = new Player();
		for(int i = 0; i < 6; i++)
			newPlayers[i] = new Player();
	}
	
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
				Context context = getApplicationContext();
				Toast t = Toast.makeText(context, "writing completed", Toast.LENGTH_SHORT);
				t.show();
				finish();
			}
		});
	}
	
	public void writeScore() throws IOException{
		FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_WORLD_READABLE | Context.MODE_WORLD_WRITEABLE);
		OutputStreamWriter osw = new OutputStreamWriter(fos);
		inGame.name = et.getText().toString();
		inGame.score = MainGamePanel.score;
		for(int i = 0; i < 5; i++){
			newPlayers[i].name = s.players[i].name;
			newPlayers[i].score = s.players[i].score;
		}
		newPlayers[5].name = inGame.name;
		newPlayers[5].score = inGame.score;
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
				if(p[i].score < p[i+1].score){
					Player tmp = new Player();
					tmp.name = p[i].name;
					tmp.score = p[i].score;
					p[i].name = p[i+1].name;
					p[i].score = p[i+1].score;
					p[i+1].name = tmp.name;
					p[i+1].score = tmp.score;
				}
			n = n-1;
		}while(n > 1);
	}
}
