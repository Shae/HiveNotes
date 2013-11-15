package com.klusman.hivenotes;

import java.util.List;

import com.klusman.hivenotes.db.NoteDataSource;
import com.klusman.hivenotes.db.UrgencyLevels;

import android.os.Bundle;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.Gravity;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final String TAG = "NOTES_DATABASE";
	
	SQLiteOpenHelper dbHelper;
	SQLiteDatabase database;
	NoteDataSource datasourceNotes;
	UrgencyLevels datasourceLevels;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		datasourceNotes = new NoteDataSource(this);
		datasourceNotes.open();
		datasourceLevels = new UrgencyLevels(this);
		datasourceLevels.open();
		
		List<NoteUrgencyLevel> lvls = datasourceLevels.findAll();
		if(lvls.size() == 0){
			createUrgencyLevels();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	@Override
	protected void onResume() {
		super.onResume();
		datasourceNotes.open();
		datasourceLevels.open();	
		//TODO build a page reset here
		
	}

	@Override
	protected void onPause() {
		super.onPause();
		datasourceNotes.close();
		datasourceLevels.close();
	}
	
	public void myToast(String text){  
		CharSequence textIN = text;
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(MainActivity.this, textIN, duration);
		toast.setGravity(Gravity.BOTTOM, 0, 0);
		toast.show();
	};// end myToast
	
	
	private void createUrgencyLevels(){
		NoteUrgencyLevel lvl = new NoteUrgencyLevel();
		lvl.setName("The Queen Bee Says, NOW! ");
		lvl = datasourceLevels.create(lvl);
	

		lvl = new NoteUrgencyLevel();
		lvl.setName("Before the Honey Melts.");
		lvl = datasourceLevels.create(lvl);
		

		lvl = new NoteUrgencyLevel();
		lvl.setName("During this Lifecycle.");
		lvl = datasourceLevels.create(lvl);
		
		
		lvl = new NoteUrgencyLevel();
		lvl.setName("After the Worker Bee Retires.");
		lvl = datasourceLevels.create(lvl);


		
	}
	
}
