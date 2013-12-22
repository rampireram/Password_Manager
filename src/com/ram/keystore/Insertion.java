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
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.hardware.Camera;
import android.hardware.SensorManager;
public class Insertion extends Activity implements OnClickListener {
TextView tv;
EditText pass,cred;
EditText pass_2;
EditText key1;
Button bt,bt2;
String st1,st2,st3;
SecretKey key;
private EventsData events;
public static final String TABLE = "keystore";
private static final String DATABASE_NAME = "passwords.db";
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
		setContentView(R.layout.activity_insert);
		pass=(EditText) findViewById(R.id.pass);
		cred=(EditText) findViewById(R.id.credential);
		pass_2=(EditText) findViewById(R.id.pass_2);
		key1=(EditText) findViewById(R.id.key);
		tv=(TextView) findViewById(R.id.tv);
		bt=(Button) findViewById(R.id.button1);
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
		case R.id.button1:
		String cr=cred.getText().toString();
		st1=pass.getText().toString();
		st1=st1.trim();
		st2=pass_2.getText().toString();
		st2=st2.trim();
		st3=key1.getText().toString();
		
		if(st3.isEmpty())
		{
			Toast.makeText(this,"Please enter a key",Toast.LENGTH_LONG).show();
			return;
		}
		if(st1.equals(st2))
		{
			         //String s=new CharacterEncoder().encode(outputBytes);
			try {
				/*
			 	byte[] in=encryptAES(st3,st1);	
				String ans=in.toString();
				tv.setText(ans);
				*/
				String in=encrypt(st1,st3);
				
				String o=in.toString();
				//tv.setText(out,0,out.length);
				tv.setText(o);
				cr=cr.trim();
				SQLiteDatabase db=events.getWritableDatabase();
				db.execSQL("INSERT INTO " + TABLE +" VALUES ('"+cr+"','"+o+"')");
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			events.close();
		}
		else
			Toast.makeText(this,"Passwords don't match",Toast.LENGTH_LONG).show();
		}
	
		
		
	}
	
	//starts
	
	private static final String CRYPTOGRAPHY_ALGO_DES = "DES";

	

	public static String encrypt(String inputString, String commonKey)
			throws InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException {

		String encryptedValue = "";
		SecretKey key = getSecretKey(commonKey);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] inputBytes = inputString.getBytes();
		byte[] outputBytes = cipher.doFinal(inputBytes);
		encryptedValue = Base64.encodeToString(outputBytes, 0);
                //String s=new CharacterEncoder().encode(outputBytes);
		return encryptedValue;
	}

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
	

	//ends
	
	@Override
	protected void onResume() {
		super.onResume();
		//      preview.camera = Camera.open();
		pass.setText(null);
		cred.setText(null);
		pass_2.setText(null);
		key1.setText(null);
	}
	
	@Override
	protected void onPause() {
		
		super.onPause();
		pass.setText(null);
		cred.setText(null);
		pass_2.setText(null);
		key1.setText(null);
		
	}
}
