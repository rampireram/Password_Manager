package com.ram.keystore;

import java.io.IOException;
import java.security.InvalidKeyException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
public class Retrieve extends Activity implements OnClickListener {
TextView tv;
EditText pass;
EditText key1;
Button bt,bt2;
String st1,st2,st3;
SecretKey key;
private EventsData events;
public static final String TABLE = "keystore";
public static final String UNAME = "uname";
public static final String PASS = "pass";
private static Cipher cipher = null;
private static DESKeySpec keySpec = null;
private static SecretKeyFactory keyFactory = null;
public static final int DECRYPT_MODE=2;
public static final int ENCRYPT_MODE=1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ret);
		pass=(EditText) findViewById(R.id.cred);
		
		key1=(EditText) findViewById(R.id.key);
		tv=(TextView) findViewById(R.id.tv);
		bt=(Button) findViewById(R.id.decrypt);
		bt.setOnClickListener(this);
		
	    events = new EventsData(this);
	    events.getWritableDatabase();
		
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
		case R.id.decrypt:
		st1=pass.getText().toString();
		st1=st1.trim();
		st3=key1.getText().toString();
		
		if(st3.isEmpty())
		{
			Toast.makeText(this,"Please enter a key",Toast.LENGTH_LONG).show();
			return;
		}
			        
			try {
				EventsData events = new EventsData(this);
			    events.getWritableDatabase();
			    Cursor cursor = getEvents(st1);
			    String enc = null;
			    //System.out.println(cursor.getString(0));
			    if(cursor.moveToFirst())
			    {
			    	enc = cursor.getString(0);
			    	System.out.println(enc);
			    }
			    else
			    	Toast.makeText(this,"No such credential",Toast.LENGTH_LONG).show();
			    events.close();
				String in=decrypt(enc,st3);
				tv.setText(in);
				
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		}
		
		
		
	}
	
	//starts
	
	private static final String CRYPTOGRAPHY_ALGO_DES = "DES";


	public static String decrypt(String encryptedString, String commonKey)
			throws InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, IOException {
		String decryptedValue = "";
		encryptedString = encryptedString.replace(' ', '+');
		SecretKey key = getSecretKey(commonKey);
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] recoveredBytes = cipher.doFinal(Base64.decode(encryptedString,0));
		decryptedValue = new String(recoveredBytes);
		return decryptedValue;
	}

	private static SecretKey getSecretKey(String secretPassword) {
		SecretKey key = null;
		try {
			cipher = Cipher.getInstance(CRYPTOGRAPHY_ALGO_DES);
			keySpec = new DESKeySpec(secretPassword.getBytes("UTF8"));
			keyFactory = SecretKeyFactory.getInstance(CRYPTOGRAPHY_ALGO_DES);
			key = keyFactory.generateSecret(keySpec);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error in generating the secret Key");
		}
		return key;
	}
	
	private Cursor getEvents(String st1){
		// TODO Auto-generated method stub
		events = new EventsData(this);
	    events.getWritableDatabase();
		SQLiteDatabase db=events.getWritableDatabase();
			Cursor cursor=db.rawQuery("SELECT passTEXT FROM " + TABLE +" WHERE unameTEXT='"+st1+"'",null);
			startManagingCursor(cursor);
			
			return cursor;
		
	}


	//ends
	@Override
	protected void onResume() {
		super.onResume();

		pass.setText(null);
		key1.setText(null);
		tv.setText(null);
	}
	
	@Override
	protected void onPause() {
		
		super.onPause();
		pass.setText(null);
		key1.setText(null);
		tv.setText(null);
		
	}
}
