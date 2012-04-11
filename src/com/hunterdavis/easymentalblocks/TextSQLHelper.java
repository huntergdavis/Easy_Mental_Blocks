package com.hunterdavis.easymentalblocks;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;


public class TextSQLHelper extends android.database.sqlite.SQLiteOpenHelper {
	private static final String DATABASE_NAME = "easymentalblocks.db";
	private static final int DATABASE_VERSION = 2;

	// Table name
	public static final String TABLE = "pyramidsnotes";

	// Columns
	public static final String BLOCK = "block";
	public static final String DATA = "data";
	

	public TextSQLHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table " + TABLE + "( " + BaseColumns._ID
				+ " integer primary key autoincrement, " +
				BLOCK + " text not null,"+
				DATA + " text);";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (oldVersion >= newVersion)
			return;


	}

}
