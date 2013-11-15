package com.klusman.hivenotes.db;


import java.util.ArrayList;
import java.util.List;

import com.klusman.hivenotes.NoteUrgencyLevel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UrgencyLevels {
	private static final String TAG = "NOTES_DATABASE";
	SQLiteOpenHelper dbHelper;
	SQLiteDatabase database;
	
	private static final String[] allColumns = {
		NotesDbOpenHelper.COLUMN_LEVEL_ID,
		NotesDbOpenHelper.COLUMN_LEVEL_NAME
	};
	
	private static final String[] nameColumn = {
		NotesDbOpenHelper.COLUMN_LEVEL_NAME
	};
	
	
	public UrgencyLevels(Context context){
        dbHelper = new NotesDbOpenHelper(context);
	}
	
	public void open(){
		database = dbHelper.getWritableDatabase();
		Log.i(TAG , "Database OPENED");
	}
	
	public void close(){
		Log.i(TAG , "Database CLOSED");
		dbHelper.close();
	}
	
	public NoteUrgencyLevel create(NoteUrgencyLevel lvl){
		ContentValues values = new ContentValues();
		values.put(NotesDbOpenHelper.COLUMN_LEVEL_NAME, lvl.getName());
		long insertid = database.insert(NotesDbOpenHelper.TABLE_LEVELS, null, values); // GET AUTO ID
		
		lvl.setId(insertid); 
		return lvl;
	};
	
	public List<String> buildLevelArray(){
		Cursor c = database.query(NotesDbOpenHelper.TABLE_LEVELS, nameColumn, null, null, null, null, null);
		Log.i(TAG, "lvlArray Returned " + c.getCount() + " rows");
		
        List<String> lvls = new ArrayList<String>();
        
        if(c.getCount() > 0){
        	while(c.moveToNext()){
        		lvls.add(c.getString(c.getColumnIndex(NotesDbOpenHelper.COLUMN_LEVEL_NAME)));
        	}
        }
        Log.i(TAG, "URGENCY LEVELS LIST SIZE: " + lvls.size());
		return lvls;
	}
	
	
	public List<NoteUrgencyLevel> findAll(){
		List<NoteUrgencyLevel> types = new ArrayList<NoteUrgencyLevel>();
		Cursor c = database.query(NotesDbOpenHelper.TABLE_LEVELS, allColumns, null, null, null, null, null);
		Log.i(TAG, "Urgency Levels List Returned " + c.getCount() + " rows");
		
		if(c.getCount() > 0){
			while(c.moveToNext()){
				NoteUrgencyLevel mlvl = new NoteUrgencyLevel();
				mlvl.setId(c.getLong(c.getColumnIndex(NotesDbOpenHelper.COLUMN_LEVEL_ID)));
				mlvl.setName(c.getString(c.getColumnIndex(NotesDbOpenHelper.COLUMN_LEVEL_NAME)));
				types.add(mlvl);
				Log.i(TAG, mlvl.getName() + ": Find All Level Info");
			}
		}
		return types;
	} 
	
}
