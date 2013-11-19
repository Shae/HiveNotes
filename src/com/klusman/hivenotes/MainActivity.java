package com.klusman.hivenotes;

import java.util.List;


import com.klusman.hivenotes.db.NoteDataSource;
import com.klusman.hivenotes.db.UrgencyLevels;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends Activity {

	//private static final String TAG = "NOTES_DATABASE";
	
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
	
	
	public void listBuilder(){
		notes = datasourceNotes.findAllNoFilter();
		lv = (ListView)findViewById(R.id.listView1);
		notes = datasourceNotes.findAllNoFilter();
		ImageView image = (ImageView)findViewById(R.id.grnPlus);
		if(notes.size() > 0){
			lv.setAdapter( new NoteListCellAdapter(this, notes) );
			image.setVisibility(View.GONE);
		}else{
			image.setVisibility(View.VISIBLE);
			image.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
			        intent.setClass(MainActivity.this, AddNoteActivity.class);
			        startActivity(intent);
					
				}
			});
		}
		lv.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {			
				Intent intent = new Intent(MainActivity.this, DetailViewActivity.class);
				intent.putExtra("ID", notes.get(pos).getId());
				startActivity(intent);				
			}});
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
	    switch(item.getItemId()){
	    case R.id.action_add:
	        Intent intent = new Intent();
	        intent.setClass(MainActivity.this, AddNoteActivity.class);
	        startActivity(intent);
	        return true;            
	    }
	    return false;
	}

	
}
