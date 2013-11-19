package com.klusman.hivenotes;

import java.util.ArrayList;
import java.util.List;

import com.klusman.hivenotes.db.NoteDataSource;
import com.klusman.hivenotes.db.UrgencyLevels;

import android.app.Activity;
import android.content.Intent;
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

public class EditActivity extends Activity {

	private static final String TAG = "NOTES_DATABASE";
	NoteDataSource datasourceNotes;
	UrgencyLevels datasourceLevels;
	
	String _title;
	String _note;
	String _lvl;
	long _id;
	
	EditText etTitle;
	Spinner spPriority;
	EditText etNotes;
	ImageView ok;
	ImageView delete;
	
	List<NoteUrgencyLevel> levels;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		datasourceNotes = new NoteDataSource(this);
		datasourceNotes.open();
		datasourceLevels = new UrgencyLevels(this);
		datasourceLevels.open();
	
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			_id = extras.getLong("ID");
			_title = extras.getString("TITLE");
			_note = extras.getString("NOTE");
			_lvl = extras.getString("LEVEL");
			Log.i(TAG, "EDIT: ID Number: " + _id);
			Log.i(TAG, "EDIT: TITLE Number: " + _title);
		}
		
		etTitle = (EditText)findViewById(R.id.etTitle);
		etTitle.setText(_title);
		
		spPriority = (Spinner)findViewById(R.id.spinner);
		
		etNotes = (EditText)findViewById(R.id.etNote);
		etNotes.setText(_note);
		
		levels = datasourceLevels.findAll();
		List<String> list = new ArrayList<String>();
		for(int i = 0; i < levels.size(); i++){
			list.add(levels.get(i).getName());
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spPriority.setAdapter(adapter);
		
		@SuppressWarnings("unchecked")
		ArrayAdapter<String> AA = (ArrayAdapter<String>) spPriority.getAdapter();
		int spinPos = AA.getPosition(_lvl);
		spPriority.setSelection(spinPos);
		
		
		ok = (ImageView)findViewById(R.id.imageOk);
		ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				boolean good = validateFields();
				if(good == true){
					datasourceNotes.open();

					String T = etTitle.getText().toString();
					String N = etNotes.getText().toString();
					String L = String.valueOf(spPriority.getSelectedItem());
					try {
						datasourceNotes.updateNote(_id, N, L, T);
						Log.i(TAG, "Update Complete");
						Intent intent = new Intent();
						intent.setClass(EditActivity.this, MainActivity.class);
				        startActivity(intent);
					} catch (Exception e) {
						Log.i(TAG, "Update Failed");
						e.printStackTrace();
					}

					datasourceNotes.close();

					
				}
			}
		});
		
	}
	
	private boolean validateFields(){
		Log.i(TAG, "etT length : " + etTitle.getText().length());
		Log.i(TAG, "etN length : " + etNotes.getText().length());
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
		Toast toast = Toast.makeText(EditActivity.this, textIN, duration);
		toast.setGravity(Gravity.BOTTOM, 0, 0);
		toast.show();
	};
	
	
	
	
}
