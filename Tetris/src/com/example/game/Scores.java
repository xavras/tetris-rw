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
import android.widget.Toast;

public class Scores extends Activity implements OnClickListener{

	TextView tv0, tv1, tv2, tv3, tv4, tv5;
	Button b1, res;
	String FILENAME = "scores.txt";
	String[] tab = new String[5];
	String[][] tabb = new String[5][2];
	Player[] players = new Player[5];
	
	public Scores(){
		for(int i = 0; i < players.length; i++)
			players[i] = new Player();
		for(int i = 0; i < 5; i++)
			tab[i] = new String();
		for(int i = 0; i < 5; i++){
			tabb[i] = new String[2];
			for(int j = 0; j < 2; j++)
				tabb[i][j] = new String();
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scores);
		b1 = (Button)findViewById(R.id.goback);
		res = (Button)findViewById(R.id.reset);
		tv0 = (TextView)findViewById(R.id.textView1);
		tv1 = (TextView)findViewById(R.id.textView2);
		tv2 = (TextView)findViewById(R.id.textView3);
		tv3 = (TextView)findViewById(R.id.textView4);
		tv4 = (TextView)findViewById(R.id.textView5);
		tv5 = (TextView)findViewById(R.id.textView6);
		
		try {
			readScores();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		tv1.setText(players[0].name + " " + players[0].score);
		tv2.setText(players[1].name + " " + players[1].score);
		tv3.setText(players[2].name + " " + players[2].score);
		tv4.setText(players[3].name + " " + players[3].score);
		tv5.setText(players[4].name + " " + players[4].score);
		
		b1.setOnClickListener(this);
		res.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tv1.setText("player 0");
				tv2.setText("player 0");
				tv3.setText("player 0");
				tv4.setText("player 0");
				tv5.setText("player 0");
				
				try {
					writeReset();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Context context = getApplicationContext();
				Toast t = Toast.makeText(context, "Reset completed", Toast.LENGTH_SHORT);
				t.show();
			}
		});
	}
	
	
	public void readScores() throws IOException{
		FileInputStream fis = openFileInput(FILENAME);
		InputStreamReader isr = new InputStreamReader(fis);
		int j;
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		while((j = isr.read()) != -1){
			bytes.write(j);
		}
		tab = bytes.toString().split("\n");
		
		for(int i = 0; i < tab.length; i++){
			tabb[i] = tab[i].split(" ");
			players[i].name = tabb[i][0];
			players[i].score = Integer.parseInt(tabb[i][1]);
		}
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		finish();
	}
	
	public void writeReset() throws IOException{
		//zapisanie równoczeie do pliku
		FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_WORLD_READABLE | Context.MODE_WORLD_WRITEABLE);
		OutputStreamWriter osw = new OutputStreamWriter(fos);
		String toWrite = "player 0\n" +
						"player 0\n" +
						"player 0\n" +
						"player 0\n" +
						"player 0\n";
		osw.write(toWrite);
		osw.flush();
		osw.close();
	}
}
