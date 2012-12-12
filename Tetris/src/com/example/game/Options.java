package com.example.game;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class Options extends Activity{

	Button b;
	SeekBar s1, s2;
	TextView t1, t2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.options);
		b = (Button)findViewById(R.id.bdone);
		s1 = (SeekBar)findViewById(R.id.seekbar1);
		s2 = (SeekBar)findViewById(R.id.seekbar2);
		t1 = (TextView)findViewById(R.id.textViewH);
		t2 = (TextView)findViewById(R.id.textViewW);
		s1.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				t1.setText("Board height " + (progress*10+20));
				MainGamePanel.boardHeight = progress*10+20;
				t2.setText("Board width " + (progress*5+10));
				MainGamePanel.boardWidth = progress*5+10;
				s2.setProgress(progress*5+10);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				seekBar.setSecondaryProgress(seekBar.getProgress()); // set the shade of the previous value.
			}
			
		});
		s2.setEnabled(false);
		
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//wysokosc = e1.getText();
				//szerokosc = e2.getText();
				Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
				finish();
			}
		});
	}

}
