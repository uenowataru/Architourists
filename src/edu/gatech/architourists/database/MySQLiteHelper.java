package edu.gatech.architourists.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

	public static final String TABLE_ARCHITECTURE = "architecture";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_SITE = "site";
	public static final String COLUMN_ARCHITECT = "architect";
	public static final String COLUMN_YEAR = "year";
	public static final String COLUMN_STYLE = "style";
	public static final String COLUMN_PROGRAM = "program";
	public static final String COLUMN_LOCATION = "location";
	public static final String COLUMN_LATITUDE = "latitude";
	public static final String COLUMN_LONGITUDE = "longitude";
	public static final String COLUMN_COST = "cost";
	public static final String COLUMN_OPENHOURS = "openhours";
	public static final String COLUMN_RID = "rid";
	public static final String COLUMN_RIDI = "ridi";
	public static final String COLUMN_TEXT = "text";
	private static final String DATABASE_NAME = "architectures.db";
	private static final int DATABASE_VERSION = 4;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_ARCHITECTURE + "( " + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_SITE
			+ " text not null, " + COLUMN_ARCHITECT + " text not null, "
			+ COLUMN_YEAR + " text not null, " + COLUMN_STYLE
			+ " text not null, " + COLUMN_PROGRAM + " text not null, "
			+ COLUMN_LOCATION + " text not null, " + COLUMN_LATITUDE
			+ " double not null, " + COLUMN_LONGITUDE + " double not null, "
			+ COLUMN_COST + " text not null, " + COLUMN_OPENHOURS
			+ " text not null, " + COLUMN_RID + " int not null, "
			+ COLUMN_RIDI + " int not null, " + COLUMN_TEXT + " text not null)";

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARCHITECTURE);
		onCreate(db);
	}

}
