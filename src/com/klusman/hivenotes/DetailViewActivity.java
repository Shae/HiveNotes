package com.klusman.hivenotes;

import java.util.List;

import com.klusman.hivenotes.db.NoteDataSource;
import com.klusman.hivenotes.db.UrgencyLevels;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class DetailViewActivity extends Activity {
	
	private static final String TAG = "NOTES_DATABASE";
	
	SQLiteOpenHelper dbHelper;
	SQLiteDatabase database;
	NoteDataSource datasourceNotes;
	UrgencyLevels datasourceLevels;
	long _ID;
	List<NoteObject> notes;
	
	TextView tvTitle;
	TextView tvPriority;
	TextView tvNotes;
	Button edit;
	Button delete;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			_ID = extras.getLong("ID");
			Log.i(TAG, "ID Number: " + _ID);
		}
		
		datasourceNotes = new NoteDataSource(this);
		datasourceNotes.open();
		datasourceLevels = new UrgencyLevels(this);
		datasourceLevels.open();
		
		tvTitle = (TextView)findViewById(R.id.tvTitle);
		tvPriority = (TextView)findViewById(R.id.tvPriority);
		tvNotes = (TextView)findViewById(R.id.tvNote);
		
		notes = datasourceNotes.findSpecificNoteById(_ID);
		
		
		
		if(notes.size() > 0){
			
			tvTitle.setText(notes.get(0).getTitle());
			tvPriority.setText(notes.get(0).getLevel());
			tvNotes.setText(notes.get(0).getNote());
			
		}
		
		
	}
}
