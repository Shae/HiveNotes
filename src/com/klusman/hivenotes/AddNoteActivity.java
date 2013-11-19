package com.klusman.hivenotes;

import java.util.ArrayList;
import java.util.List;

import com.klusman.hivenotes.db.NoteDataSource;
import com.klusman.hivenotes.db.UrgencyLevels;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class AddNoteActivity extends Activity {
	
	private static final String TAG = "NOTES_DATABASE";
	
	Context _context;
	SQLiteOpenHelper dbHelper;
	SQLiteDatabase database;
	NoteDataSource datasourceNotes;
	UrgencyLevels datasourceLevels;
	List<NoteUrgencyLevel> levels;
	
	EditText etTitle;
	Spinner spPriority;
	EditText etNotes;
	ImageView ok;
	ImageView delete;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);

		datasourceNotes = new NoteDataSource(this);
		datasourceNotes.open();
		datasourceLevels = new UrgencyLevels(this);
		datasourceLevels.open();
		
		etTitle = (EditText)findViewById(R.id.etTitle);
		spPriority = (Spinner)findViewById(R.id.spinner);
		etNotes = (EditText)findViewById(R.id.etNote);
		
		levels = datasourceLevels.findAll();
		
		delete = (ImageView)findViewById(R.id.imageDelete2);
		delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AddNoteActivity.this.finish();  // Delete Add by just closing activity
			}
		});
		
		ok = (ImageView)findViewById(R.id.imageOk);
		ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				boolean good = validateFields();
				if(good == true){
					Log.i(TAG, "SAVE HERE!");
					datasourceNotes.open();
					createNote(
							String.valueOf(spPriority.getSelectedItem()), 
							etTitle.getText().toString(), 
							etNotes.getText().toString());
					datasourceNotes.close();
					Intent intent = new Intent();
					intent.setClass(AddNoteActivity.this, MainActivity.class);
			        startActivity(intent);
				}
			}
		});
		
		
		List<String> list = new ArrayList<String>();
		for(int i = 0; i < levels.size(); i++){
			list.add(levels.get(i).getName());
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spPriority.setAdapter(adapter);
	}
	
	
	private boolean validateFields(){
		if((etTitle.getText().length() > 0 ) && (etNotes.getText().length() > 0)){
			//Log.i(TAG, "Validation good!");
			return true;
		}
			//Log.i(TAG, "Validation failed!");
			myToast("Please ensure all fields are filled out.");
		return false;
	}
	
	
	public void myToast(String text){  
		CharSequence textIN = text;
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(AddNoteActivity.this, textIN, duration);
		toast.setGravity(Gravity.BOTTOM, 0, 0);
		toast.show();
	};
	
	
	private void createNote(String lvl, String title, String note) {
		NoteObject newNote = new NoteObject();
		newNote.setLevel(lvl);
		newNote.setNote(note);
		newNote.setTitle(title);
		newNote = datasourceNotes.create(newNote);
	}
}
