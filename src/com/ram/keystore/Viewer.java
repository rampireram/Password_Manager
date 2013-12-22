package com.ram.keystore;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Viewer extends Activity implements OnClickListener {
Button bt;
private EventsData events;
public static final String TABLE = "keystore";

public static final String UNAME = "uname";
public static final String PASS = "pass";
TextView text;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view);
		bt=(Button) findViewById(R.id.back);
		bt.setOnClickListener(this);
		text = (TextView) findViewById(R.id.output);
		EventsData events = new EventsData(this);
	    events.getWritableDatabase();
	    Cursor cursor = getEvents();
		showEvents(cursor);
		  
		
	}
	
	private void showEvents(Cursor cursor) {
		StringBuilder builder=new StringBuilder("----------------- Credentials  -----------------\n");
		while(cursor.moveToNext())
		{
			
			String name = cursor.getString(0);
			builder.append("------> ");
			builder.append(name);
			builder.append("\n");
			
			
		}
		events.close();
		text.setText(builder);
	}

	private Cursor getEvents(){
		// TODO Auto-generated method stub
		events = new EventsData(this);
	    events.getWritableDatabase();
		SQLiteDatabase db=events.getWritableDatabase();
			
			Cursor cursor=db.rawQuery("SELECT unameTEXT FROM "+ TABLE,null);
			
			startManagingCursor(cursor);
			
			return cursor;
		
	}

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.back:
			Intent ins = new Intent(this, First_screen.class);
        startActivity(ins);
        break;
		
			
		}
	}
}