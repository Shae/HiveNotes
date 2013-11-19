package com.klusman.hivenotes.db;

import java.util.ArrayList;
import java.util.List;

import com.klusman.hivenotes.NoteObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class NoteDataSource {
	private static final String TAG = "NOTES_DATABASE";
	SQLiteOpenHelper dbHelper;
	SQLiteDatabase database;
	
	public static final String[] allColumns = {
		NotesDbOpenHelper.COLUMN_ID,
		NotesDbOpenHelper.COLUMN_TITLE,
		NotesDbOpenHelper.COLUMN_LEVEL,
		NotesDbOpenHelper.COLUMN_NOTE,
	};
	
	
	public NoteDataSource(Context context){
		dbHelper = new NotesDbOpenHelper(context);
	}
	
	public void open(){
		database = dbHelper.getWritableDatabase();
		Log.i(TAG , "Database OPENED");
	}
	
	public void close(){
		dbHelper.close();
		Log.i(TAG , "Database CLOSED");
	}
	
	public NoteObject create(NoteObject note){
		Log.i(TAG, "Create Note Function in NoteDataSource Code");
		
		ContentValues values = new ContentValues();

		values.put(NotesDbOpenHelper.COLUMN_TITLE, note.getTitle());
			//Log.i(TAG, "Create Title : " + note.getTitle());
		values.put(NotesDbOpenHelper.COLUMN_LEVEL, note.getLevel());
		 	//Log.i(TAG, "Create Level : " + note.getLevel());
		values.put(NotesDbOpenHelper.COLUMN_NOTE, note.getNote());
		 //	Log.i(TAG, "Create Note : " + String.valueOf(note.getNote()));

			
		long insertid = database.insert(NotesDbOpenHelper.TABLE_NOTES, null, values); // GET AUTO ID
		
		note.setId(insertid); // SET the ID of the new note with the Auto generated one
		
		return note;
	};
	
	
	public List<NoteObject> findAllNoFilter(){
		
		Log.i(TAG, "**START FIND ALL - no Filter");
		List<NoteObject> notes = new ArrayList<NoteObject>();
		
		Cursor c = database.query(NotesDbOpenHelper.TABLE_NOTES, allColumns, null, null, null, null, null);
		Log.i(TAG, "Notes List Returned " + c.getCount() + " rows");
		
		if(c.getCount() > 0){
			while(c.moveToNext()){
				NoteObject note = new NoteObject();
				note.setId(c.getLong(c.getColumnIndex(NotesDbOpenHelper.COLUMN_ID)));
				note.setTitle(c.getString(c.getColumnIndex(NotesDbOpenHelper.COLUMN_TITLE)));
				note.setLevel(c.getString(c.getColumnIndex(NotesDbOpenHelper.COLUMN_LEVEL)));
				note.setNote(c.getString(c.getColumnIndex(NotesDbOpenHelper.COLUMN_NOTE)));
			
				notes.add(note);
			}
		}
		return notes;
	} 
	
	public List<NoteObject> findSpecificNoteById(long idNum){
		
		Log.i(TAG, "**START, FIND SPECIFIC ID: " + idNum);
		List<NoteObject> notes = new ArrayList<NoteObject>();
		
		Cursor c = database.query(NotesDbOpenHelper.TABLE_NOTES, allColumns, NotesDbOpenHelper.COLUMN_ID + "=?", new String[] {String.valueOf(idNum)}, null, null, null);
		Log.i(TAG, "Notes List Returned " + c.getCount() + " rows");
		
		if(c.getCount() > 0){
			while(c.moveToNext()){
				NoteObject note = new NoteObject();
				note.setId(c.getLong(c.getColumnIndex(NotesDbOpenHelper.COLUMN_ID)));
				note.setTitle(c.getString(c.getColumnIndex(NotesDbOpenHelper.COLUMN_TITLE)));
				note.setLevel(c.getString(c.getColumnIndex(NotesDbOpenHelper.COLUMN_LEVEL)));
				note.setNote(c.getString(c.getColumnIndex(NotesDbOpenHelper.COLUMN_NOTE)));
			
				notes.add(note);
			}
		}
		return notes;
	} 
	
	public List<NoteObject> sortNotesByTitle(){
		Log.i(TAG, "**START sortNotesByTitle function");
		List<NoteObject> notes = new ArrayList<NoteObject>();
		
		Cursor c = database.query
				(
				NotesDbOpenHelper.TABLE_NOTES, 
				allColumns, 
				null, 
				null, 
				null, 
				null, 
				NotesDbOpenHelper.COLUMN_TITLE
				);

		
		if(c.getCount() > 0){
			while(c.moveToNext()){
				NoteObject note = new NoteObject();
				note.setId(c.getLong(c.getColumnIndex(NotesDbOpenHelper.COLUMN_ID)));
				note.setTitle(c.getString(c.getColumnIndex(NotesDbOpenHelper.COLUMN_TITLE)));
				note.setLevel(c.getString(c.getColumnIndex(NotesDbOpenHelper.COLUMN_LEVEL)));
				note.setNote(c.getString(c.getColumnIndex(NotesDbOpenHelper.COLUMN_NOTE)));
			
				notes.add(note);
				
			}
		}
		return notes;
	} 
	
	public List<NoteObject> sortNotesByUrgency(){
		Log.i(TAG, "**START sortNotesByUrgency function");
		List<NoteObject> notes = new ArrayList<NoteObject>();
		
		Cursor c = database.query
				(
				NotesDbOpenHelper.TABLE_NOTES, 
				allColumns, 
				null, 
				null, 
				null, 
				null, 
				NotesDbOpenHelper.COLUMN_LEVEL
				);

		
		if(c.getCount() > 0){
			while(c.moveToNext()){
				NoteObject note = new NoteObject();
				note.setId(c.getLong(c.getColumnIndex(NotesDbOpenHelper.COLUMN_ID)));
				note.setTitle(c.getString(c.getColumnIndex(NotesDbOpenHelper.COLUMN_TITLE)));
				note.setLevel(c.getString(c.getColumnIndex(NotesDbOpenHelper.COLUMN_LEVEL)));
				note.setNote(c.getString(c.getColumnIndex(NotesDbOpenHelper.COLUMN_NOTE)));
			
				notes.add(note);
				
			}
		}
		return notes;
	} 
	
	public void updateNote(long longID, String newString, int newlevel) {
		ContentValues cvUpdate = new ContentValues();
		cvUpdate.put(NotesDbOpenHelper.COLUMN_NOTE, newString);
		cvUpdate.put(NotesDbOpenHelper.COLUMN_LEVEL, newlevel);
		database.update(NotesDbOpenHelper.TABLE_NOTES, cvUpdate, NotesDbOpenHelper.COLUMN_ID + "=" + longID, null);
		
		Log.i(TAG, "Update Note: DONE");
	}
	
	
	public void SaveNewNote(long longID, String title, int level,
			String note) {
		ContentValues cvNew = new ContentValues();
		cvNew.put(NotesDbOpenHelper.COLUMN_TITLE, title);
		cvNew.put(NotesDbOpenHelper.COLUMN_NOTE, note);
		cvNew.put(NotesDbOpenHelper.COLUMN_LEVEL, level);
		database.insert(NotesDbOpenHelper.TABLE_NOTES, null, cvNew);
		
		Log.i(TAG, "Build New Note: COMPLETE");
		
	}
	
	public void deleteTableAndRebuild(){
		database.execSQL("DROP TABLE IF EXISTS " + NotesDbOpenHelper.TABLE_NOTES);
		Log.i(TAG, "DROP TABLE");
		database.execSQL(NotesDbOpenHelper.TABLE_CREATE);
		Log.i(TAG, "REBUILD TABLE");
	}
	
	
	
	
}
