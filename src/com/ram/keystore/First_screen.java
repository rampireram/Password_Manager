package com.ram.keystore;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class First_screen extends Activity implements OnClickListener {
Button bt,bt2,bt3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first);
		bt=(Button) findViewById(R.id.insert);
		bt.setOnClickListener(this);
		bt2=(Button) findViewById(R.id.ret);
		bt2.setOnClickListener(this);
		bt3=(Button) findViewById(R.id.view);
		bt3.setOnClickListener(this);
		
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
		case R.id.insert:
			Intent ins = new Intent(this, Insertion.class);
        startActivity(ins);
        break;
		case R.id.ret:
			Intent ret = new Intent(this, Retrieve.class);
	        startActivity(ret);
	        break;
		case R.id.view:
			Intent vi = new Intent(this, Viewer.class);
	        startActivity(vi);
	        break;
			
		}
	}
}