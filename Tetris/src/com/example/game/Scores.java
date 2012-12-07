package com.example.game;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Scores extends Activity implements OnClickListener{

	TextView tv0, tv1, tv2, tv3, tv4, tv5;
	Button b1;
	String FILENAME = "scores.txt";
	String[] tab = new String[5];
	String[][] tabb = new String[5][2];
	Player[] players = new Player[5];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scores);
		b1 = (Button)findViewById(R.id.goback);
		tv0 = (TextView)findViewById(R.id.textView1);
		tv1 = (TextView)findViewById(R.id.textView2);
		tv2 = (TextView)findViewById(R.id.textView3);
		tv3 = (TextView)findViewById(R.id.textView4);
		tv4 = (TextView)findViewById(R.id.textView5);
		tv5 = (TextView)findViewById(R.id.textView6);
		for(int i = 0; i < players.length; i++)
			players[i] = new Player();
		try {
			tab = readScores();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i = 0; i < tab.length; i++){
			tabb[i] = tab[i].split(" ");
			players[i].name = tabb[i][0];
			players[i].score = Integer.parseInt(tabb[i][1]);
		}
		
		tv1.setText(players[0].name + " " + players[0].score);
		tv2.setText(players[1].name + " " + players[1].score);
		tv3.setText(players[2].name + " " + players[2].score);
		tv4.setText(players[3].name + " " + players[3].score);
		tv5.setText(players[4].name + " " + players[4].score);
		
		b1.setOnClickListener(this);
	}
	
	
	public String[] readScores() throws IOException{
		FileInputStream fis = openFileInput(FILENAME);
		InputStreamReader isr = new InputStreamReader(fis);
		int i;
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		while((i = isr.read()) != -1){
			bytes.write(i);
		}
		return bytes.toString().split("\n");
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		finish();
	}
	
}
