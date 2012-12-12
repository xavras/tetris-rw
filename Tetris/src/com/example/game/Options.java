package com.example.game;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Options extends Activity{

	Button b;
	EditText e1, e2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.options);
		b = (Button)findViewById(R.id.bdone);
		e1 = (EditText) findViewById(R.id.editTextH);
		e2 = (EditText) findViewById(R.id.editTextW);
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
