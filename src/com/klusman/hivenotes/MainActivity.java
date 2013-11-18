package com.klusman.hivenotes;

import java.io.IOException;
import java.util.List;


import com.klusman.hivenotes.db.NoteDataSource;
import com.klusman.hivenotes.db.NotesDbOpenHelper;
import com.klusman.hivenotes.db.UrgencyLevels;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final String TAG = "NOTES_DATABASE";
	
	SQLiteOpenHelper dbHelper;
	SQLiteDatabase database;
	NoteDataSource datasourceNotes;
	UrgencyLevels datasourceLevels;
	ListView lv;
	List<NoteObject> notes;
	
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
		
		
		
		listBuilder();
		
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
	
	private void createTestNote() {
		NoteObject newNote = new NoteObject();
		newNote.setLevel("The Queen Bee Says, NOW! ");
		newNote.setNote("Test Test");
		newNote.setTitle("TestTubeBabies");
		newNote = datasourceNotes.create(newNote);
		
	}
	
	public void listBuilder(){
		notes = datasourceNotes.findAllNoFilter();
		lv = (ListView)findViewById(R.id.listView1);

		if(notes.size() == 0){
			createTestNote();
			notes = datasourceNotes.findAllNoFilter();
		}
		
		if(notes.size() > 0){
			lv.setAdapter( new NoteListCellAdapter(this, notes));
		}	
		
	}
	
//	public void deletePopUp(long id ){
//		AlertDialog.Builder pop2 = new AlertDialog.Builder(this);
//		
//		pop2.setTitle("Delete Note?");
//		pop2.setMessage("Please Confirm Action.")
//				.setCancelable(false)
//				.setPositiveButton("DELETE",new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog,int id) {
//						
//						NotesDbOpenHelper ndbos = new NotesDbOpenHelper(MainActivity.this);
//						Log.i(TAG, "Attempting to Delete Note " + id );
//						ndbos.deleteRow(id);
//						listBuilder();
//					}
//				  })
//				.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog,int id) {
//						dialog.cancel();
//					}
//				});
//				AlertDialog alertDialog = pop2.create();
//				alertDialog.show();
//	}
	
	
}
