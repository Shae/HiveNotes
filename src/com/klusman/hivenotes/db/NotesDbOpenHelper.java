package com.klusman.hivenotes.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NotesDbOpenHelper extends SQLiteOpenHelper{
	private static final String TAG = "NOTES_DATABASE";
	
	
	public static final String DATABASE_NAME = "notes.db";
	private static final int DATABASE_VERSION = 1;
	
	public static final String TABLE_NOTES = "notesTable";
	
	public static final String COLUMN_ID = "noteID";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_LEVEL = "level";
	public static final String COLUMN_NOTE = "note";

	
	public static final String TABLE_LEVELS = "levels";
	public static final String COLUMN_LEVEL_ID = "levelID";
	public static final String COLUMN_LEVEL_NAME = "levelName";
	
	public NotesDbOpenHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	
	public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NOTES + " (" +
			COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			COLUMN_TITLE + " TEXT NOT NULL, " +
			COLUMN_LEVEL + " INTEGER NOT NULL, " +
			COLUMN_NOTE + " TEXT NOT NULL, " +
			")";
	
	private static final String TABLE_CREATE_LEVEL = "CREATE TABLE " + TABLE_LEVELS + " (" +
			COLUMN_LEVEL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			COLUMN_LEVEL_NAME + " TEXT NOT NULL " +
			")";
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE_LEVEL);
		Log.i(TAG, TABLE_LEVELS + ": Table has been created");
		
		db.execSQL(TABLE_CREATE);
		Log.i(TAG, DATABASE_NAME + ": Table has been created");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LEVELS);
		Log.i(TAG, " Dropping older DB Version " + oldVersion + " for new Version " + newVersion + ".");
		onCreate(db);
		
	}
	

}
