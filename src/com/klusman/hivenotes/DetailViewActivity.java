package com.klusman.hivenotes;

import java.util.List;

import com.klusman.hivenotes.db.NoteDataSource;
import com.klusman.hivenotes.db.NotesDbOpenHelper;
import com.klusman.hivenotes.db.UrgencyLevels;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailViewActivity extends Activity {
	
	private static final String TAG = "NOTES_DATABASE";
	
	Context _context;
	SQLiteOpenHelper dbHelper;
	SQLiteDatabase database;
	NoteDataSource datasourceNotes;
	UrgencyLevels datasourceLevels;
	long _ID;
	List<NoteObject> notes;
	
	TextView tvTitle;
	TextView tvPriority;
	TextView tvNotes;
	ImageView edit;
	ImageView delete;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			_ID = extras.getLong("ID");
			//Log.i(TAG, "ID Number: " + _ID);
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
		
		edit = (ImageView)findViewById(R.id.imageEdit);
		edit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent( DetailViewActivity.this, EditActivity.class);	
				intent.putExtra("TITLE", tvTitle.getText().toString());
				intent.putExtra("LEVEL", tvPriority.getText().toString());
				intent.putExtra("NOTE", tvNotes.getText().toString());
				intent.putExtra("ID", notes.get(0).getId());
				startActivity(intent);
			}
		});
		
		delete = (ImageView)findViewById(R.id.imageDelete);
		delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				deletePopUp();
			}
		});

	}
	
	
	public void deletePopUp(){
		AlertDialog.Builder pop2 = new AlertDialog.Builder(this);
		pop2.setTitle("Delete Note?");
		pop2.setMessage("Please Confirm Action.")
				.setCancelable(false)
				.setPositiveButton("Cancel",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						dialog.cancel();
					}
				  })
				.setNegativeButton("DELETE",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						NotesDbOpenHelper ndbos = new NotesDbOpenHelper(DetailViewActivity.this);
						Log.i(TAG, "Attempting to Delete Note " + _ID );
						try {
							ndbos.deleteRow(_ID);
							Intent intent = new Intent( DetailViewActivity.this, MainActivity.class);						
							startActivity(intent);
						} catch (Exception e) {
							Log.i(TAG, "Delete row failed.");
							e.printStackTrace();
						}
					}
				});
				AlertDialog alertDialog = pop2.create();
				alertDialog.show();
	}
}
