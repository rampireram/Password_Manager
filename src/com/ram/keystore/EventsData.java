package com.ram.keystore;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/** Helper to the database, manages versions and creation */
public class EventsData extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "passwords.db";
	private static final int DATABASE_VERSION = 3;

	
	public static final String TABLE = "keystore";

	public static final String UNAME = "uname";
	public static final String PASS = "pass";

	public EventsData(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE "+TABLE+"(" + UNAME +"TEXT, " + PASS +"TEXT);");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
					db.execSQL("DROP TABLE IF EXISTS " + TABLE);
					onCreate(db);
	}

}

	
		