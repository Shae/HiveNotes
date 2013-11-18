package com.klusman.hivenotes;

import java.util.List;

import com.klusman.hivenotes.db.NoteDataSource;
import com.klusman.hivenotes.db.NotesDbOpenHelper;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NoteListCellAdapter extends ArrayAdapter<NoteObject>{

	private static final String TAG = "NOTES_DATABASE";
	Context _context;
	TextView tvTitle;
	int pos;
	
	long _ID;
	String title = "";
	String note = "";
	String lvl = "";
	
	private List<NoteObject> _noteList ;
	ImageView bee1;
	ImageView bee2;
	ImageView bee3;
	ImageView bee4;
	
	//Current Urgency Options
	String A = "The Queen Bee Says, NOW! ";
	String B = "Before the Honey Melts.";
	String C = "During this Lifecycle.";
	String D = "After the Worker Bee Retires.";
	
	
	public NoteListCellAdapter(Context context, List<NoteObject> notes) {
		super(context, R.layout.custom_cell, notes);
		this._context = context;
		this._noteList = notes;

	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.custom_cell, parent, false);
		tvTitle = (TextView) rowView.findViewById(R.id.tvCellTitle);
		pos = position;
		
		bee1 = (ImageView)rowView.findViewById(R.id.bee1);
		bee1.setVisibility(View.INVISIBLE);
		
		bee2 = (ImageView)rowView.findViewById(R.id.bee2);
		bee2.setVisibility(View.INVISIBLE);
		
		bee3 = (ImageView)rowView.findViewById(R.id.bee3);
		bee3.setVisibility(View.INVISIBLE);
		
		bee4 = (ImageView)rowView.findViewById(R.id.bee4);
		bee4.setVisibility(View.INVISIBLE);
		

		title = _noteList.get(position).getTitle();
		tvTitle.setText(title);
		
		note = _noteList.get(position).getNote();
		lvl = _noteList.get(position).getLevel();
		_ID = _noteList.get(position).getId();

		if(lvl.equalsIgnoreCase(A)){
			Log.i(TAG, "String A true " + A);
			bee1.setVisibility(View.VISIBLE);
			bee2.setVisibility(View.VISIBLE);
			bee3.setVisibility(View.VISIBLE);
			bee4.setVisibility(View.VISIBLE);
		}
		
		if(lvl.equalsIgnoreCase(B)){
			Log.i(TAG, "String A true " + B);
			bee1.setVisibility(View.VISIBLE);
			bee2.setVisibility(View.VISIBLE);
			bee3.setVisibility(View.VISIBLE);
			
		}
		
		if(lvl.equalsIgnoreCase(C)){
			Log.i(TAG, "String A true " + C);
			bee1.setVisibility(View.VISIBLE);
			bee2.setVisibility(View.VISIBLE);
		}
		
		if(lvl.equalsIgnoreCase(D)){
			Log.i(TAG, "String A true " + D);
			bee1.setVisibility(View.VISIBLE);
		}
		
	
		
	rowView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//Toast.makeText(getContext(),name,Toast.LENGTH_SHORT).show();
				//popUp();
				Intent intent = new Intent(_context, DetailViewActivity.class);
				intent.putExtra("ID", _ID);
				_context.startActivity(intent);
			}
		});
		return rowView;
	}
	
	
//	private void popUp(){
//		AlertDialog.Builder pop = new AlertDialog.Builder(_context);
//		pop.setTitle(title);
//		pop.setMessage(lvl)
//				.setCancelable(false)
//				.setPositiveButton("Edit",new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog,int id) {
//						dialog.cancel();
//					}
//				  })
//				 .setPositiveButton("Delete",new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog,int id) {
//						MainActivity ma = new MainActivity();
//						ma.deletePopUp(_ID);
//					}
//				  })
//				.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog,int id) {
//						dialog.cancel();
//					}
//				});
//				AlertDialog alertDialog = pop.create();
//				alertDialog.show();
//	}
	
//	private void deletePopUp(){
//		AlertDialog.Builder pop2 = new AlertDialog.Builder(_context);
//		pop2.setTitle("Delete Note?");
//		pop2.setMessage("Please Confirm Action.")
//				.setCancelable(false)
//				.setPositiveButton("Cancel",new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog,int id) {
//						dialog.cancel();
//					}
//				  })
//				.setNegativeButton("DELETE",new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog,int id) {
//						NotesDbOpenHelper ndbos = new NotesDbOpenHelper(_context);
//						Log.i(TAG, "Attempting to Delete Note " + _ID );
//						ndbos.deleteRow(_ID);
//					}
//				});
//				AlertDialog alertDialog = pop2.create();
//				alertDialog.show();
//	}


	
}
