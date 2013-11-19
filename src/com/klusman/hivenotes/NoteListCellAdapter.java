package com.klusman.hivenotes;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NoteListCellAdapter extends ArrayAdapter<NoteObject>{

	//private static final String TAG = "NOTES_DATABASE";
	Context _context;
	TextView tvTitle;
	int pos;
	
	long _ID = -1;
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

		_ID = -1;
		title = "";
		note = "";
		lvl = "";
		
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
			bee1.setVisibility(View.VISIBLE);
			bee2.setVisibility(View.VISIBLE);
			bee3.setVisibility(View.VISIBLE);
			bee4.setVisibility(View.VISIBLE);
		}
		
		if(lvl.equalsIgnoreCase(B)){
			bee1.setVisibility(View.VISIBLE);
			bee2.setVisibility(View.VISIBLE);
			bee3.setVisibility(View.VISIBLE);
			
		}
		
		if(lvl.equalsIgnoreCase(C)){
			bee1.setVisibility(View.VISIBLE);
			bee2.setVisibility(View.VISIBLE);
		}
		
		if(lvl.equalsIgnoreCase(D)){	
			bee1.setVisibility(View.VISIBLE);
		}
		
		return rowView;
	}
	


	
}
