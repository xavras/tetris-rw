package com.example.game;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Levels extends ListActivity {

	static final String[] levels = new String[] { "Newbie", "Beginner",
			"Novice", "Trained", "Skilled", "Talented", "Professional",
			"Expert", "Master" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setListAdapter(new ArrayAdapter<String>(this, R.layout.levels, levels));

		ListView lv = getListView();
		lv.setTextFilterEnabled(true);

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				switch (arg2) {
				case 0: {

					break;
				}
				case 1: {

					break;
				}
				case 2: {

					break;
				}
				case 3: {

					break;
				}
				case 4: {

					break;
				}
				case 5: {

					break;
				}
				case 6: {

					break;
				}
				case 7: {

					break;
				}
				case 8: {

					break;
				}
				}
			}

		});
	}
}
